package com.paascloud.provider.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Perm.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class Perm implements Serializable {

	private static final long serialVersionUID = 6578617729619284277L;
	private String resource;
	private String perm;

}
