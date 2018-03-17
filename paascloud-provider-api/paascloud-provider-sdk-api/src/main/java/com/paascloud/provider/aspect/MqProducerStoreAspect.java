package com.paascloud.provider.aspect;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.annotation.MqProducerStore;
import com.paascloud.provider.exceptions.TpcBizException;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.enums.DelayLevelEnum;
import com.paascloud.provider.model.enums.MqSendTypeEnum;
import com.paascloud.provider.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * The class Mq producer store aspect.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Aspect
public class MqProducerStoreAspect {
	@Resource
	private MqMessageService mqMessageService;
	@Value("${paascloud.aliyun.rocketMq.producerGroup}")
	private String producerGroup;

	/**
	 * Add exe time annotation pointcut.
	 */
	@Pointcut("@annotation(com.paascloud.provider.annotation.MqProducerStore)")
	public void mqProducerStoreAnnotationPointcut() {

	}

	/**
	 * Add exe time method object.
	 *
	 * @param joinPoint the join point
	 *
	 * @return the object
	 */
	@Around(value = "mqProducerStoreAnnotationPointcut()")
	public Object processMqProducerStoreJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("processMqProducerStoreJoinPoint - 线程id={}", Thread.currentThread().getId());
		Object result;
		Object[] args = joinPoint.getArgs();
		MqProducerStore annotation = getAnnotation(joinPoint);
		MqSendTypeEnum type = annotation.sendType();
		int orderType = annotation.orderType().orderType();
		DelayLevelEnum delayLevelEnum = annotation.delayLevel();
		if (args.length == 0) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}
		MqMessageData domain = null;
		for (Object object : args) {
			if (object instanceof MqMessageData) {
				domain = (MqMessageData) object;
				break;
			}
		}

		if (domain == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}

		domain.setOrderType(orderType);
		domain.setProducerGroup(producerGroup);
		if (type == MqSendTypeEnum.WAIT_CONFIRM) {
			if (delayLevelEnum != DelayLevelEnum.ZERO) {
				domain.setDelayLevel(delayLevelEnum.delayLevel());
			}
			mqMessageService.saveWaitConfirmMessage(domain);
		}
		result = joinPoint.proceed();
		if (type == MqSendTypeEnum.SAVE_AND_SEND) {
			mqMessageService.saveAndSendMessage(domain);
		} else if (type == MqSendTypeEnum.DIRECT_SEND) {
			mqMessageService.directSendMessage(domain);
		} else {
			mqMessageService.confirmAndSendMessage(domain.getMessageKey());
		}
		return result;
	}

	private static MqProducerStore getAnnotation(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(MqProducerStore.class);
	}
}
