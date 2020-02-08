package me.atticuszambrana.atticus.time;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AtticusTime {
	
	// Will give you an object for the time of its creation
	// Author: Atticus Zambrana
	
	private LocalTime time;
	private long milli;
	
	public AtticusTime() {
		// Because the server is being hosted in this region
		time = LocalTime.now(ZoneId.of("America/Los_Angeles"));
		milli = System.currentTimeMillis();
	}
	
	public String getTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return time.format(formatter);
	}
	
	public long getMilli() {
		return milli;
	}
}
