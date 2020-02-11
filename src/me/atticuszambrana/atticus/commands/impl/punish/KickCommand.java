package me.atticuszambrana.atticus.commands.impl.punish;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

public class KickCommand extends Command {
	
	public KickCommand() {
		super("kick", "Kick a user from the server", Rank.MODERATOR);
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
		
		User target = getUser(event.getMessage().getMentionedUsers());
		String reason = StringUtil.combine(args, 2);
		
		// Fix this bug I found that fcks up the database for some reason
//		if(reason == null || reason.length() == 0) {
//			return;
//		}
		
		AtticusTime exact = new AtticusTime();
		
		LogUtil.info("Punish", event.getMessageAuthor().getName() + " has kicked " + target.getName() + " for: " + reason);
		
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
				notify.addField("Type", "Kick");
				notify.addField("Reason", reason);
				notify.addField("Timestamp", exact.getTime());
				
				Start.getDiscord().getChannelById(channel).get().asServerTextChannel().get().sendMessage(notify);
			}
		}.start();
		
		// Add a check to all the punishments, to make sure that we are able to do so
		if(!event.getServer().get().canYouKickUser(target) || !event.getServer().get().canYouKickUsers()) {
			EmbedBuilder err = new EmbedBuilder();
			err.setColor(Color.RED);
			err.setTitle("I do not have permission!");
			err.setDescription("I do not have the required permissions to complete this action! Make sure I have the `Kick Users` permission node, and my role is higher then the user you are trying to punish!");
			event.getChannel().sendMessage(err);
			return;
		}
		
		// The [ZIP] Tag marks the punishment, as Zelda Issued Punishment
		event.getServer().get().kickUser(target, reason + " [ZIP]");
		
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
					Database.get().getConnection().createStatement().executeUpdate("INSERT INTO `Punishments_Kicks` (`ID`, `MODERATOR`, `TARGET`, `REASON`, `TIMESTAMP`) VALUES (NULL, '" + event.getMessageAuthor().getIdAsString() + "', '" + target.getIdAsString() + "', '" + reason + "', '" + exact.getMilli() + "');");
				}
				catch(SQLException ex) {
					LogUtil.info("Database", "There was an error while inputting data into the database: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}.start();
		
		return;
	}
	
	private User getUser(List<User> users) {
		return users.get(0);
	}

}
