package me.atticuszambrana.atticus.commands.impl.punish;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.database.Database;
import me.atticuszambrana.atticus.permissions.Rank;
import me.atticuszambrana.atticus.time.AtticusTime;
import me.atticuszambrana.atticus.util.LogUtil;
import me.atticuszambrana.atticus.util.StringUtil;

public class BanCommand extends Command {
	
	public BanCommand() {
		super("ban", "Remove a user from the server for an extended period of time", Rank.MODERATOR);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		if(args.length == 1 || args.length == 2) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("Missing Arguments: <Target>, <Reason>");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		User target = event.getMessage().getMentionedUsers().get(0);
		String reason = StringUtil.combine(args, 2);
		
		AtticusTime exact = new AtticusTime();
		
		LogUtil.info("Punish", event.getMessageAuthor().getName() + " has banned " + target.getName() + " for: " + reason);
		

		//TODO: Lookup the channel that we want to post notifications to, then give a notification for the kick
		new Thread() {
			public void run() {
				String channel = null;
				try {
					ResultSet result = Database.get().getConnection().createStatement().executeQuery("SELECT * FROM `SystemConfig` WHERE `TOKEN` = 'Punishment_Alerts';");
					while(result.next()) {
						channel = result.getString("VALUE");
					}
				} catch(SQLException ex) {
					LogUtil.info("Database", "There was an error while getting data: " + ex.getMessage());
					ex.printStackTrace();
				}
				
				// Then send it
				EmbedBuilder notify = new EmbedBuilder();
				notify.setColor(Color.MAGENTA);
				notify.setTitle("Punishment Added");
				notify.addField("Moderator", event.getMessageAuthor().getName());
				notify.addField("Target", target.getName());
				notify.addField("Type", "Ban");
				notify.addField("Reason", reason);
				notify.addField("Timestamp", exact.getTime());
				
				Start.getDiscord().getChannelById(channel).get().asServerTextChannel().get().sendMessage(notify);
			}
		}.start();
		
		event.getServer().get().banUser(target, 100, reason + " [ZIP]");
		
		new Thread() {
			public void run() {
				try {
					ResultSet result = Database.get().getConnection().createStatement().executeQuery("SELECT * FROM `SystemConfig` WHERE `TOKEN` = 'RemovePunishCommands';");
					while(result.next()) {
						boolean remove = Boolean.valueOf(result.getString("VALUE"));
						
						if(remove) {
							event.getMessage().delete();
						}
					}
				}
				catch(SQLException ex) {
					LogUtil.info("Database", "There was an error while trying to access the database: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}.start();
		
		new Thread() {
			public void run() {
				try {
					Database.get().getConnection().createStatement().executeUpdate("INSERT INTO `Punishments_Bans` (`ID`, `MODERATOR`, `TARGET`, `REASON`, `TIMESTAMP`) VALUES (NULL, '" + event.getMessageAuthor().getIdAsString() + "', '" + target.getIdAsString() + "', '" + reason + "', '" + exact.getMilli() + "');");
				}
				catch(SQLException ex) {
					LogUtil.info("Database", "There was an error while inputting data into the database: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}.start();
		
		return;
	}

}
