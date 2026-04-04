package com.yatmk.test.common.utility;


import java.util.Optional;
import lombok.experimental.UtilityClass;


@UtilityClass
public class FileUtility {

	public static final String DOT = ".";

	public static String getFileNameWithoutExtension(String fileName) {
		return Optional.ofNullable(fileName).map(e -> e.lastIndexOf(DOT)).filter(lastDotIndex -> lastDotIndex != -1 && lastDotIndex > 0).map(lastDotIndex -> fileName.substring(0, lastDotIndex)).orElseGet(() -> fileName);
	}

	public static String getFileExtension(String fileName) {
		return Optional.ofNullable(fileName).map(e -> e.lastIndexOf(DOT)).filter(lastDotIndex -> lastDotIndex != -1 && lastDotIndex < fileName.length() - 1).map(lastDotIndex -> fileName.substring(lastDotIndex + 1)).map(String::toLowerCase).orElseGet(String::new);
	}

}
