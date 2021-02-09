package com.app.Doceria.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class DataContainer {
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDateTime dateTime;

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
