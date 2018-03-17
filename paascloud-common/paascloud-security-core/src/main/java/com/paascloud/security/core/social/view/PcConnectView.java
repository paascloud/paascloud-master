package com.paascloud.security.core.social.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 绑定结果视图
 *
 * @author paascloud.net @gmail.com
 */
public class PcConnectView extends AbstractView {

	private static final String CONNECTIONS = "connections";

	/**
	 * Render merged output model.
	 *
	 * @param model    the model
	 * @param request  the request
	 * @param response the response
	 *
	 * @throws Exception the exception
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
	                                       HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		if (model.get(CONNECTIONS) == null) {
			response.getWriter().write("<h3>解绑成功</h3>");
		} else {
			response.getWriter().write("<h3>绑定成功</h3>");
		}

	}

}
