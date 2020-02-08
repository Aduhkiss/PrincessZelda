package me.atticuszambrana.atticus.util;

import me.atticuszambrana.atticus.time.AtticusTime;

public class LogUtil {
	public static void info(String caller, String message) {
		AtticusTime time = new AtticusTime();
		System.out.println("[" + time.getTime() + "] " + caller + "> " + message);
	}
}
