package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Tools {
	
	public void log(String message) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime date = LocalDateTime.now();
	    String dateString = date.format(format);
		System.out.println("[" + dateString + "] " + message);
	}

}
