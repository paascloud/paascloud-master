package com.paascloud.base.constant;

import com.google.common.collect.Lists;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * The class Aliyun sms constants.
 *
 * @author paascloud.net@gmail.com
 */
public class AliyunSmsConstants {

	/**
	 * 短信模板枚举
	 */
	public enum SmsTempletEnum {

		/**
		 * 通用模板(短信内容:验证码${code}, 您正在注册成为paasCloud用户, 感谢您的支持！)
		 */
		UAC_PC_GLOBAL_TEMPLATE("UAC_PC_GLOBAL_TEMPLATE", "SMS_105115057", "code"),;

		private String busType;

		private String templetCode;

		private String smsParamName;

		public static SmsTempletEnum getEnum(String templateCode) {
			SmsTempletEnum smsTempletEnum = null;
			for (SmsTempletEnum ele : SmsTempletEnum.values()) {
				if (templateCode.equals(ele.getTempletCode())) {
					smsTempletEnum = ele;
					break;
				}
			}
			return smsTempletEnum;
		}

		public static boolean isSmsTemplate(String smsTemplateCode) {

			if (StringUtils.isEmpty(smsTemplateCode)) {
				throw new BusinessException(ErrorCodeEnum.UAC10011020);
			}
			List<String> templetCodeList = getTemplateCodeList();

			return templetCodeList.contains(smsTemplateCode);
		}

		public static List<SmsTempletEnum> getList() {
			return Arrays.asList(SmsTempletEnum.values());
		}

		public static List<String> getTemplateCodeList() {
			List<String> templetCodeList = Lists.newArrayList();
			List<SmsTempletEnum> list = getList();
			for (SmsTempletEnum templetEnum : list) {
				if (StringUtils.isEmpty(templetEnum.getTempletCode())) {
					continue;
				}
				templetCodeList.add(templetEnum.getTempletCode());
			}
			return templetCodeList;
		}

		SmsTempletEnum(String busType, String templetCode, String smsParamName) {
			this.busType = busType;
			this.templetCode = templetCode;
			this.smsParamName = smsParamName;
		}

		/**
		 * Gets bus type.
		 *
		 * @return the bus type
		 */
		public String getBusType() {
			return busType;
		}

		/**
		 * Gets templet code.
		 *
		 * @return the templet code
		 */
		public String getTempletCode() {
			return templetCode;
		}

		/**
		 * Gets sms param name.
		 *
		 * @return the sms param name
		 */
		public String getSmsParamName() {
			return smsParamName;
		}
	}


}
