package com.paascloud.provider;

import com.paascloud.provider.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;


/**
 * The class Md 5 test.
 * @author paascloud.net@gmail.com
 */
@Slf4j
public class MD5Test {
	/**
	 * Md 5.
	 */
	private static void md5() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
		md5.setEncodeHashAsBase64(false);
		String pwd = md5.encodePassword("admin,123", null);
		log.info("MD5: " + pwd + " len=" + pwd.length());
	}

	/**
	 * Sha 256.
	 *
	 */
	private static void sha_256() {
		ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
		sha.setEncodeHashAsBase64(true);
		String pwd = sha.encodePassword("admin,123", null);
		log.info("哈希算法 256: " + pwd + " len=" + pwd.length());
	}


	/**
	 * Sha sha 256.
	 */
	private static void sha_SHA_256() {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String pwd = sha.encodePassword("admin,123", null);
		log.info("哈希算法 SHA-256: " + pwd + " len=" + pwd.length());
	}


	/**
	 * Md 5 system wide salt source.
	 */
	private static void md5_SystemWideSaltSource() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		// 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可     
		String pwd = md5.encodePassword("admin,123", "acegisalt");
		log.info("MD5 SystemWideSaltSource: " + pwd + " len=" + pwd.length());
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 *
	 */
	public static void main(String[] args) {
		md5(); // 使用简单的MD5加密方式     

		sha_256(); // 使用256的哈希算法(SHA)加密     

		sha_SHA_256(); // 使用SHA-256的哈希算法(SHA)加密     

		md5_SystemWideSaltSource(); // 使用MD5再加全局加密盐加密的方式加密
		String salt = KeyGenerators.string().generateKey();
		log.info(salt);
		log.info("salt.length={}", salt.length());
		String encrypt = Md5Util.encrypt("admin,123");
		log.info(encrypt);
	}

}  