package com.paascloud.core.annotation;

import java.lang.annotation.*;

/**
 * 配合 SqlLogInterceptor 对指定方法 禁止打印SQL到控制台
 *
 * @author paascloud.net @gmail.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotDisplaySql {
}
