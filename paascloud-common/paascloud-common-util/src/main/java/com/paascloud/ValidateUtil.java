package com.paascloud;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class Validate util.
 *
 * @author paascloud.net @gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtil {
	/**
	 * 校验手机号码是否合法.
	 *
	 * @param mobile the mobile
	 *
	 * @return the boolean
	 */
	public static boolean isMobileNumber(final String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		final String reg = "^((\\+?86)|(\\(\\+86\\)))?(13[0-9][0-9]{8}|14[0-9]{9}|15[0-9][0-9]{8}|17[0-9][0-9]{8}|18[0-9][0-9]{8})$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}


}
