package me.atticuszambrana.atticus.activity;

import org.javacord.api.entity.activity.ActivityType;

import me.atticuszambrana.atticus.Plugin;
import me.atticuszambrana.atticus.Start;

public class Activity extends Plugin {
	
	public Activity() {
		super("Activity");
		update();
	}
	
	private int activityTick = 0;
	
	private void update() {
		new Thread() {
			public void run() {
				if(activityTick == 0) {
					Start.getDiscord().updateActivity(ActivityType.PLAYING, "Hello World!");
					activityTick++;
				}
				if(activityTick == 1) {
					Start.getDiscord().updateActivity(ActivityType.PLAYING, "Hello World... 2!");
					activityTick = 0;
				}
				
				try {
					Thread.sleep((1000 * 60));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				update();
			}
		}.start();
	}
}
