/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcAlipayServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.paascloud.BigDecimalUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.generator.UniqueIdGenerator;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.PtcPayInfoMapper;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.constant.PtcApiConstant;
import com.paascloud.provider.model.domain.OmcOrder;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.model.domain.PtcPayInfo;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.attachment.OptUploadFileByteInfoReqDto;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import com.paascloud.provider.service.OmcOrderDetailService;
import com.paascloud.provider.service.OmcOrderService;
import com.paascloud.provider.service.OpcOssService;
import com.paascloud.provider.service.PtcAlipayService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The class Ptc alipay service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class PtcAlipayServiceImpl implements PtcAlipayService {

	private static AlipayTradeService tradeService;

	static {

		Configs.init("zfbinfo.properties");

		tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
	}

	@Resource
	private OmcOrderService omcOrderService;
	@Resource
	private OmcOrderDetailService omcOrderDetailService;
	@Resource
	private PtcPayInfoMapper ptcPayInfoMapper;
	@Resource
	private OpcOssService opcOssService;

	@Value("${paascloud.alipay.callback.url}")
	private String alipayCallbackUrl;
	@Value("${paascloud.alipay.qrCode.pcPath}")
	private String qrCodePcPath;
	@Value("${paascloud.alipay.qrCode.qiniuPath}")
	private String qrCodeQiniuPath;

	/**
	 * Pay wrapper.
	 *
	 * @param orderNo      the order no
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper pay(String orderNo, LoginAuthDto loginAuthDto) {
		Long userId = loginAuthDto.getUserId();
		OrderDto order = omcOrderService.queryOrderDtoByUserIdAndOrderNo(userId, orderNo);
		if (order == null) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031003);
		}

		// (必填) 商户网站订单系统中唯一订单号, 64个字符以内, 只能包含字母、数字、下划线,
		// 需保证商户系统端不能重复, 建议通过数据库sequence生成,
		String outTradeNo = order.getOrderNo();


		// (必填) 订单标题, 粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
		String subject = "PCMall扫码支付,订单号:" + outTradeNo;


		// (必填) 订单总金额, 单位为元, 不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = order.getPayment().toString();


		// (可选) 订单不可打折金额, 可以配合商家平台配置折扣活动, 如果酒水不参与打折, 则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
		String undiscountableAmount = "0";


		// 卖家支付宝账号ID, 用于支持一个签约账号下支持打款到不同的收款账号, (打款到sellerId对应的支付宝账号)
		// 如果该字段为空, 则默认为与支付宝签约的商户的PID, 也就是appid对应的PID
		String sellerId = "";

		// 订单描述, 可以对交易或商品进行一个详细地描述, 比如填写"购买商品2件共15.00元"
		String body = "订单" + outTradeNo + "购买商品共" + totalAmount + "元";


		// 商户操作员编号, 添加此参数可以为商户操作员做销售统计
		String operatorId = "test_operator_id";

		// (必填) 商户门店编号, 通过门店号和商家后台可以配置精准到门店的折扣信息, 详询支付宝技术支持
		String storeId = "test_store_id";

		// 业务扩展参数, 目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法), 详情请咨询支付宝技术支持
		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId("2088100200300400500");


		// 支付超时, 定义为120分钟
		String timeoutExpress = "120m";

		// 商品明细列表, 需填写购买商品详细信息,
		List<GoodsDetail> goodsDetailList = Lists.newArrayList();

		List<OmcOrderDetail> orderItemList = omcOrderDetailService.getListByOrderNoUserId(orderNo, userId);
		for (OmcOrderDetail orderItem : orderItemList) {
			GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
					BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), 100D).longValue(),
					orderItem.getQuantity());
			goodsDetailList.add(goods);
		}

		// 创建扫码支付请求builder, 设置请求参数
		AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
				.setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
				.setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
				.setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
				.setTimeoutExpress(timeoutExpress)
				//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
				.setNotifyUrl(alipayCallbackUrl)
				.setGoodsDetailList(goodsDetailList);


		AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
		switch (result.getTradeStatus()) {
			case SUCCESS:
				log.info("支付宝预下单成功: )");

				AlipayTradePrecreateResponse response = result.getResponse();
				dumpResponse(response);

				// 需要修改为运行机器上的路径
				File folder = new File(qrCodePcPath);
				if (!folder.exists()) {
					folder.setWritable(true);
					folder.mkdirs();
				}
				//细节细节细节
				String qrPath = String.format(qrCodePcPath + "/qr-%s.png", response.getOutTradeNo());
				String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());

				ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
				File qrCodeImage = new File(qrPath);
				OptUploadFileReqDto optUploadFileReqDto = new OptUploadFileReqDto();

				optUploadFileReqDto.setBucketName("paascloud-oss-bucket");
				optUploadFileReqDto.setFilePath(qrCodeQiniuPath);
				optUploadFileReqDto.setFileType("png");
				optUploadFileReqDto.setUserId(loginAuthDto.getUserId());
				optUploadFileReqDto.setUserName(loginAuthDto.getUserName());
				OptUploadFileByteInfoReqDto optUploadFileByteInfoReqDto = new OptUploadFileByteInfoReqDto();
				optUploadFileByteInfoReqDto.setFileName(qrFileName);
				byte[] bytes = FileUtil.readBytes(qrCodeImage);
				optUploadFileByteInfoReqDto.setFileByteArray(bytes);
				optUploadFileReqDto.setUploadFileByteInfoReqDto(optUploadFileByteInfoReqDto);
				OptUploadFileRespDto optUploadFileRespDto = null;
				try {
					optUploadFileRespDto = opcOssService.uploadFile(optUploadFileReqDto);
					optUploadFileRespDto.setRefNo(orderNo);
					// 获取二维码
					final OptGetUrlRequest request = new OptGetUrlRequest();
					request.setAttachmentId(optUploadFileRespDto.getAttachmentId());
					request.setEncrypt(true);
					String fileUrl = opcOssService.getFileUrl(request);
					optUploadFileRespDto.setAttachmentUrl(fileUrl);
				} catch (Exception e) {
					log.error("上传二维码异常", e);
				}
				return WrapMapper.ok(optUploadFileRespDto);
			case FAILED:
				log.error("支付宝预下单失败!!!");
				return WrapMapper.error("支付宝预下单失败!!!");

			case UNKNOWN:
				log.error("系统异常, 预下单状态未知!!!");
				return WrapMapper.error("系统异常, 预下单状态未知!!!");

			default:
				log.error("不支持的交易状态, 交易返回异常!!!");
				return WrapMapper.error("不支持的交易状态, 交易返回异常!!!");
		}
	}

	/**
	 * 简单打印应答
	 */
	private void dumpResponse(AlipayResponse response) {
		if (response != null) {
			log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
			if (StringUtils.isNotEmpty(response.getSubCode())) {
				log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
						response.getSubMsg()));
			}
			log.info("body:" + response.getBody());
		}
	}

	@Override
	public Wrapper aliPayCallback(Map<String, String> params) {
		log.info("支付宝回调. - aliPayCallback. params={}", params);
		String orderNo = params.get("out_trade_no");
		String tradeNo = params.get("trade_no");
		String tradeStatus = params.get("trade_status");
		OrderDto order = omcOrderService.queryOrderDtoByOrderNo(orderNo);
		if (order == null) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031010);
		}
		if (order.getStatus() >= OmcApiConstant.OrderStatusEnum.PAID.getCode()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031011);
		}
		if (PtcApiConstant.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
			order.setPaymentTime(DateUtil.parseDate(params.get("gmt_payment")));
			order.setStatus(OmcApiConstant.OrderStatusEnum.PAID.getCode());
			ModelMapper modelMapper = new ModelMapper();
			OmcOrder omcOrder = modelMapper.map(order, OmcOrder.class);
			omcOrderService.update(omcOrder);
		}

		PtcPayInfo payInfo = new PtcPayInfo();
		payInfo.setUserId(order.getUserId());
		payInfo.setOrderNo(order.getOrderNo());
		payInfo.setPayPlatform(PtcApiConstant.PayPlatformEnum.ALIPAY.getCode());
		payInfo.setPlatformNumber(tradeNo);
		payInfo.setPlatformStatus(tradeStatus);
		payInfo.setUpdateTime(new Date());
		payInfo.setCreatedTime(new Date());
		payInfo.setCreator(order.getCreator());
		payInfo.setCreatorId(order.getUserId());
		payInfo.setLastOperator(order.getLastOperator());
		payInfo.setLastOperatorId(order.getLastOperatorId());
		payInfo.setId(UniqueIdGenerator.generateId());

		ptcPayInfoMapper.insertSelective(payInfo);

		return WrapMapper.ok();
	}
}
