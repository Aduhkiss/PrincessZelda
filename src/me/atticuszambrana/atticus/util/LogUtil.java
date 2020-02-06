package me.atticuszambrana.atticus.util;

public class LogUtil {
	public static void info(String caller, String message) {
		System.out.println(caller + "> " + message);
	}
}
