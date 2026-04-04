package com.yatmk.test.common.utility;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;


@UtilityClass
public class DateUtility {

	public static final String DATE_FORMAT_PATTERN_STRING = "yyyy-MM-dd_HH-mm-ss";

	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String ZONE_AFRICA_CASABLANCA = "Africa/Casablanca";

	public static SimpleDateFormat getSimpleDateFormat_YYYY_MM_DD() {
		return new SimpleDateFormat(FORMAT_DATE_YYYY_MM_DD);
	}

	public static DateTimeFormatter getDateTimeFormatter_YYYY_MM_DD() {
		return DateTimeFormatter.ofPattern(FORMAT_DATE_YYYY_MM_DD);
	}

	public static LocalDate nowDate() {
		return LocalDate.now(ZoneId.of(ZONE_AFRICA_CASABLANCA));
	}

	public static LocalTime nowTime() {
		return LocalTime.now(ZoneId.of(ZONE_AFRICA_CASABLANCA));
	}

	public static LocalDateTime nowDateTime() {
		return LocalDateTime.now(ZoneId.of(ZONE_AFRICA_CASABLANCA));
	}

	public static String nowDateTimeFormatted() {
		return nowDateTime().format(getDateTimeFormatter_YYYY_MM_DD());
	}

	public static String nameWithDate(String name) {
		return new StringBuilder().append(name).append("_").append(nowDateTimeFormatted()).toString();
	}

}
