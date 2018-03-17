package com.paascloud.security.core.validate.code.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paascloud.security.core.SecurityResult;
import com.paascloud.security.core.validate.code.ValidateCodeGenerator;
import com.paascloud.security.core.validate.code.ValidateCodeRepository;
import com.paascloud.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 图片验证码处理器
 *
 * @author paascloud.net @gmail.com
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	@Resource
	private ObjectMapper objectMapper;

	/**
	 * Instantiates a new Abstract validate code processor.
	 *
	 * @param validateCodeGenerators the validate code generators
	 * @param validateCodeRepository the validate code repository
	 */
	public ImageCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
		super(validateCodeGenerators, validateCodeRepository);
	}

	/**
	 * 发送图形验证码，将其写到响应中
	 *
	 * @param request   the request
	 * @param imageCode the image code
	 *
	 * @throws Exception the exception
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(imageCode.getImage(), "JPEG", bos);

		SecurityResult result = SecurityResult.ok(bos.toByteArray());

		String json = objectMapper.writeValueAsString(result);
		HttpServletResponse response = request.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
