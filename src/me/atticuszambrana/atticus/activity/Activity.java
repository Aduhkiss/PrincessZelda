package me.atticuszambrana.atticus.activity;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import me.atticuszambrana.atticus.Plugin;
import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.util.LogUtil;

public class Activity extends Plugin {
	
	public Activity() {
		super("Activity");
		update();
	}
	
	private int activityTick = 0;
	
	private void update() {
		new Thread() {
			public void run() {
				String prefix = Start.getCommandManager().getPrefix();
				if(activityTick == 0) {
					Start.getDiscord().updateActivity(ActivityType.PLAYING, prefix + "help for commands!");
					//System.out.println("Slide 1");
					activityTick++;
				}
				else if(activityTick == 1) {
					// Lol advertise my own stream cause why not
					User atticus = null;
					try {
						atticus = Start.getDiscord().getUserById("249326432390610944").get();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						LogUtil.info("Atticus", "Error getting Atticus as a user. Displaying fallback message...");
						Start.getDiscord().updateActivity(ActivityType.PLAYING, "Error. GG-DUG01");
						e.printStackTrace();
					}
					
					if(atticus.getActivity().get().getType() == ActivityType.STREAMING) {
						Start.getDiscord().updateActivity("Atticus is Live!", "twitch.tv/atticuswasneverhere");
					} else {
						Start.getDiscord().updateActivity(ActivityType.PLAYING, "Welcome to Yeta!");
					}
					
					//System.out.println("Slide 2");
					activityTick++;
				}
				else if(activityTick == 2) {
					
					// Get the number of currently active servers that the bot is on
					int i = 0;
					for(Server server : Start.getDiscord().getServers()) {
						i++;
					}
					
					Start.getDiscord().updateActivity(ActivityType.PLAYING, "I am on " + i + " servers.");
					activityTick++;
				}
				else if(activityTick == 3) {
					// Get all the servers that we are on right now
					int i = 0;
					for(Server server : Start.getDiscord().getServers()) {
						for(User user : server.getMembers()) {
							i++;
						}
					}
					
					Start.getDiscord().updateActivity(ActivityType.PLAYING, "Watching " + i + " users.");
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
