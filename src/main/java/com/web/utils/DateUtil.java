package com.web.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

import com.web.data.Message;

public class DateUtil {
	
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
