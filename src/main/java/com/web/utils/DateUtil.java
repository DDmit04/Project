package com.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

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
	
	public static boolean isLater(LocalDateTime firstDate, LocalDateTime secondDate) {
		return firstDate.isAfter(secondDate);
	}
	
	public static Long calculateNewMessages(List<Message> messages, LocalDateTime lastViewDate) {
		Long res = (long) 0;
		ListIterator<Message> iterator = messages.listIterator(messages.size()); 
		while(iterator.hasPrevious() 
				&& iterator.previous().getMessageDate().isAfter(lastViewDate)) {
			res++;
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
}
