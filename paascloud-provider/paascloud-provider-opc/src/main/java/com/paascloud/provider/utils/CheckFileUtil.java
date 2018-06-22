/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CheckFileUtil.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The class Check file util.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckFileUtil {

	private static final Map<String, String> FILE_TYPE_MAP = Maps.newHashMap();

	/**
	 * Check file type.
	 *
	 * @param fileType            the file type
	 * @param inputStreamFileType the input stream file type
	 */
	public static void checkFileType(String fileType, String inputStreamFileType) {
		Preconditions.checkArgument(FileTypeEnum.getTypes().contains(fileType), "上传文件类型不正确");

		// 获取文件类型
		FILE_TYPE_MAP.clear();
		// 上传的是图片
		if (FileTypeEnum.PICTURE.getType().equals(fileType)) {
			getPicFileType();
			Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(inputStreamFileType), "图片格式非法");
		}
		// 上传的是音频
		if (FileTypeEnum.AUDIO.getType().equals(fileType)) {
			getAudioFileType();
			Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(inputStreamFileType), "音频格式非法");
		}
		// 上传的是视频
		if (FileTypeEnum.VIDEO.getType().equals(fileType)) {
			getVideoFileType();
			Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(inputStreamFileType), "视频格式非法");
		}
	}


	private static void getVideoFileType() {
		// Real Audio (ram)
		FILE_TYPE_MAP.put("ram", "2E7261FD");
		// Real Media (rm)
		FILE_TYPE_MAP.put("rm", "2E524D46");
		// Quicktime (mov)
		FILE_TYPE_MAP.put("mov", "00000014667479707174");
		// rmvb
		FILE_TYPE_MAP.put("rmvb", "2e524d46000000120001");
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("avi", "52494646b440c02b4156");
		FILE_TYPE_MAP.put("flv", "464C5601050000000900");
		FILE_TYPE_MAP.put("mp4", "00000020667479706d70");
		FILE_TYPE_MAP.put("wmv", "3026b2758e66CF11a6d9");
		FILE_TYPE_MAP.put("3gp", "00000014667479703367");
		FILE_TYPE_MAP.put("mkv", "1a45dfa3010000000000");
	}

	private static void getPicFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF");
		FILE_TYPE_MAP.put("png", "89504E47");
		FILE_TYPE_MAP.put("gif", "47494638");
		FILE_TYPE_MAP.put("bmp", "424D");
		FILE_TYPE_MAP.put("png", "89504E470D0a1a0a0000");
		// 16色位图(bmp)
		FILE_TYPE_MAP.put("bmp", "424d228c010000000000");
		// 24位位图(bmp)
		FILE_TYPE_MAP.put("bmp", "424d8240090000000000");
		// 256色位图(bmp
		FILE_TYPE_MAP.put("bmp", "424d8e1b030000000000");
	}

	private static void getAudioFileType() {
		// Wave (wav)
		FILE_TYPE_MAP.put("wav", "57415645");
		// MIDI (mid)
		FILE_TYPE_MAP.put("mid", "4D546864");
		FILE_TYPE_MAP.put("mp3", "49443303000000002176");
		FILE_TYPE_MAP.put("wav", "52494646e27807005741");
		FILE_TYPE_MAP.put("aac", "fff1508003fffcda004c");
		FILE_TYPE_MAP.put("wv", "7776706ba22100000704");
		FILE_TYPE_MAP.put("flac", "664c6143800000221200");
	}

	/**
	 * The enum File type enum.
	 *
	 * @author paascloud.net@gmail.com
	 */
	public enum FileTypeEnum {

		/**
		 * 音频
		 */
		AUDIO("audio", "音频"),
		/**
		 * 图片
		 */
		PICTURE("picture", "图片"),
		/**
		 * 视频
		 */
		VIDEO("video", "视频");

		/**
		 * The Type.
		 */
		String type;
		/**
		 * The Name.
		 */
		String name;

		FileTypeEnum(String type, String name) {
			this.type = type;
			this.name = name;
		}

		/**
		 * Gets type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Gets name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets name.
		 *
		 * @param type the type
		 *
		 * @return the name
		 */
		public static String getName(String type) {
			for (FileTypeEnum ele : FileTypeEnum.values()) {
				if (ele.getType().equals(type)) {
					return ele.getName();
				}
			}
			return null;
		}

		/**
		 * Gets types.
		 *
		 * @return the types
		 */
		public static List<String> getTypes() {
			List<String> list = new ArrayList<>();
			for (FileTypeEnum ele : FileTypeEnum.values()) {
				list.add(ele.getType());
			}
			return list;
		}

		/**
		 * 获取List集合
		 *
		 * @return List list
		 */
		public static List<FileTypeEnum> getList() {
			return Arrays.asList(FileTypeEnum.values());
		}
	}
}
