package me.atticuszambrana.atticus.pvp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.javacord.api.entity.user.User;

import me.atticuszambrana.atticus.Plugin;
import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.database.Database;
import me.atticuszambrana.atticus.util.LogUtil;

public class PvPEngine extends Plugin {
	/*
	 * Atticus' PvP Engine
	 * Author: Atticus Zambrana
	 */
	public PvPEngine() {
		// When this starts up, go through all of the cached users (if they are not bots)
		// and create an entry in the database if they dont exist
		
		super("Combat System");
		
		for(User user : Start.getDiscord().getCachedUsers()) {
			if(!user.isBot()) {
				new Thread() {
					public void run() {
						try {
							int i = 0;
							ResultSet result = Database.get().getConnection().createStatement().executeQuery("SELECT * FROM `UserHealth` WHERE `USER_ID` = '" + user.getIdAsString() + "';");
							while(result.next()) {
								i++;
							}
							
							if(i == 0) {
								// Create them a new file
								Database.get().getConnection().createStatement().executeUpdate("INSERT INTO `UserHealth` (`USER_ID`, `HP`) VALUES ('" + user.getIdAsString() + "', 20);");
								LogUtil.info("Combat", "Created new entry for " + user.getName() + ".");
							}
						} catch(SQLException ex) {
							LogUtil.info("Database", "Error while starting combat system");
							ex.printStackTrace();
						}
					}
				}.start();
			}
		}
	}
}
