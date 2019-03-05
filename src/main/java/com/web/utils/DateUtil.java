package com.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public static String getLocalDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime =  LocalDateTime.now().format(formatter);
        return formatDateTime;
	}
	
	public static String getLocalDate(String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formatDateTime =  LocalDateTime.now().format(formatter);
        return formatDateTime;
	}

}
