package com.paascloud.provider.model.enums;

/**
 * Rocketmq默认延时级别.
 *
 * @author paascloud.net @gmail.com
 */
public enum DelayLevelEnum {
	/**
	 * Zero delay level enum.
	 */
	ZERO(0, "不延时"),
	/**
	 * One delay level enum.
	 */
	ONE(1, "1秒"),
	/**
	 * Two delay level enum.
	 */
	TWO(2, "5秒"),
	/**
	 * Three delay level enum.
	 */
	THREE(3, "10秒"),
	/**
	 * Four delay level enum.
	 */
	FOUR(4, "30秒"),
	/**
	 * Five delay level enum.
	 */
	FIVE(5, "1分钟"),
	/**
	 * Six delay level enum.
	 */
	SIX(6, "2分钟"),
	/**
	 * Seven delay level enum.
	 */
	SEVEN(7, "3分钟"),
	/**
	 * Eight delay level enum.
	 */
	EIGHT(8, "4分钟"),
	/**
	 * Nine delay level enum.
	 */
	NINE(9, "5分钟"),
	/**
	 * Ten delay level enum.
	 */
	TEN(10, "6分钟"),
	/**
	 * Eleven delay level enum.
	 */
	ELEVEN(11, "7分钟"),
	/**
	 * Twelve delay level enum.
	 */
	TWELVE(12, "8分钟"),
	/**
	 * Thirteen delay level enum.
	 */
	THIRTEEN(13, "9分钟"),
	/**
	 * Fourteen delay level enum.
	 */
	FOURTEEN(14, "10分钟"),
	/**
	 * Fifteen delay level enum.
	 */
	FIFTEEN(15, "20分钟"),
	/**
	 * Sixteen delay level enum.
	 */
	SIXTEEN(16, "30分钟"),
	/**
	 * Seventeen delay level enum.
	 */
	SEVENTEEN(17, "1小时"),
	/**
	 * Eighteen delay level enum.
	 */
	EIGHTEEN(18, "2小时");

	DelayLevelEnum(int delayLevel, String value) {
		this.delayLevel = delayLevel;
		this.value = value;
	}

	private int delayLevel;
	private String value;

	/**
	 * Delay level int.
	 *
	 * @return the int
	 */
	public int delayLevel() {
		return delayLevel;
	}

	/**
	 * Value string.
	 *
	 * @return the string
	 */
	public String value() {
		return value;
	}
}
