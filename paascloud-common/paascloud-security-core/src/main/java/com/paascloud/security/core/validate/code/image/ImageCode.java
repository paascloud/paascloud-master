package com.paascloud.security.core.validate.code.image;

import com.paascloud.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


/**
 * 图片验证码
 *
 * @author paascloud.net @gmail.com
 */
public class ImageCode extends ValidateCode {


	private static final long serialVersionUID = -6020470039852318468L;

	private BufferedImage image;

	/**
	 * Instantiates a new Image code.
	 *
	 * @param image    the image
	 * @param code     the code
	 * @param expireIn the expire in
	 */
	ImageCode(BufferedImage image, String code, int expireIn) {
		super(code, expireIn);
		this.image = image;
	}

	/**
	 * Instantiates a new Image code.
	 *
	 * @param image      the image
	 * @param code       the code
	 * @param expireTime the expire time
	 */
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}

	/**
	 * Gets image.
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Sets image.
	 *
	 * @param image the image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
