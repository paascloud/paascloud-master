package com.paascloud.provider.annotation;

import com.paascloud.provider.model.enums.DelayLevelEnum;
import com.paascloud.provider.model.enums.MqOrderTypeEnum;
import com.paascloud.provider.model.enums.MqSendTypeEnum;

import java.lang.annotation.*;


/**
 * 保存生产者消息.
 *
 * @author paascloud.net @gmail.com
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MqProducerStore {
	MqSendTypeEnum sendType() default MqSendTypeEnum.WAIT_CONFIRM;

	MqOrderTypeEnum orderType() default MqOrderTypeEnum.ORDER;

	DelayLevelEnum delayLevel() default DelayLevelEnum.ZERO;
}
