package com.paascloud.core.annotation;

import java.lang.annotation.*;

/**
 * The interface Validate annotation.
 *
 * @author paascloud.net@gmail.com
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateAnnotation {
	/**
	 * Is validate boolean.
	 *
	 * @return the boolean
	 */
	boolean isValidate() default true;
}