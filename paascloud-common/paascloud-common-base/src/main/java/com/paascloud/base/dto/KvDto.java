package com.paascloud.base.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * The class Kv dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class KvDto<K, V> implements Serializable {

	private static final long serialVersionUID = -7712636075929650779L;

	/**
	 * Instantiates a new Kv dto.
	 */
	public KvDto() {
	}

	/**
	 * Instantiates a new Kv dto.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public KvDto(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * key
	 */
	private K key;
	/**
	 * value
	 */
	private V value;

}
