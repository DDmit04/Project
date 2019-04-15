package com.web.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import com.web.data.Message;

public class DateUtil {
	
	public static String getLoalDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);
        return formatDateTime;
	}
	
	public static String getLoalDate(String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formatDateTime = LocalDateTime.now().format(formatter);
        return formatDateTime;
	}
	
	public static String formatDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = date.format(formatter);
        return formatDateTime;
	}
	
	public static boolean isLater(LocalDateTime date, LocalDateTime date1) {
		return date.isAfter(date1);
	}
	
	public static Long calculateNewMessages(Iterable<Message> messages, LocalDateTime date) {
		Long res = (long) 0;
		for(Message message : messages ) {
			if(message.getMessageDate().isAfter(date)) {
				res++;
			}
		}
		return res;
	}
	
	public static boolean dateInRange(LocalDateTime currentDate, LocalDateTime date, LocalDateTime date1) {
		if(currentDate.isAfter(date) && currentDate.isBefore(date1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isSameDate(LocalDateTime date, LocalDateTime date1) {
		if(date.equals(date1)) {
			return true;
		} else {
			return false;
		}
	}
}
