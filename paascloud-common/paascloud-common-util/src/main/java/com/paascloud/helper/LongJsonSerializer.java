package com.paascloud.helper;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Long 类型字段序列化时转为字符串，避免js丢失精度
 *
 * @author paascloud.net@gmail.com
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
	/**
	 * Serialize.
	 *
	 * @param value              the value
	 * @param jsonGenerator      the json generator
	 * @param serializerProvider the serializer provider
	 *
	 * @throws IOException the io exception
	 */
	@Override
	public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		String text = (value == null ? null : String.valueOf(value));
		if (text != null) {
			jsonGenerator.writeString(text);
		}
	}
}
