package com.study.common.util;

import java.text.DecimalFormat;

public class StudyFileUtils {
	private static DecimalFormat df = new DecimalFormat("#,###.0");
	
	public static String fancySize(long size) {
		// 1k 미만
		if (size < 1024) {
			return size + "Bytes";
		// 1M 미만
		} else if (size < (1024 * 1024)) {
			return df.format(size / 1024.0) + "KB";
		// 1G 미만
		} else if (size < (1024 * 1024 * 1024)) {
			return df.format(size / (1024.0 * 1024.0)) + "MB";
		} else {
			return df.format(size / (1024.0 * 1024.0 * 1024.0)) + "GB";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(fancySize(453));
		System.out.println(fancySize(40054));
		System.out.println(fancySize(4551656465463L));
		System.out.println(fancySize(453156156156165L));
	}
}
