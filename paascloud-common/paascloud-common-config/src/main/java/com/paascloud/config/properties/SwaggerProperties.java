package com.paascloud.config.properties;

import lombok.Data;

/**
 * The class Async task properties.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class SwaggerProperties {

	private String title;

	private String description;

	private String version = "1.0";

	private String license = "Apache License 2.0";

	private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";

	private String contactName = "无痕";

	private String contactUrl = "http://paascloud.net";

	private String contactEmail = "paascloud.net@gmail.com";
}
