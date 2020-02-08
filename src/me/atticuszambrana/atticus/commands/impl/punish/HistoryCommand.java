package me.atticuszambrana.atticus.commands.impl.punish;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.commands.impl.punish.Punishment.PunishmentType;
import me.atticuszambrana.atticus.database.Database;
import me.atticuszambrana.atticus.permissions.Rank;
import me.atticuszambrana.atticus.util.LogUtil;

public class HistoryCommand extends Command {
	
	public HistoryCommand() {
		super("history", "View a users punishment history", Rank.MODERATOR);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		if(args.length == 1) {
			EmbedBuilder err = new EmbedBuilder();
			err.setColor(Color.RED);
			err.setTitle("Not Enough Arguments");
			err.setDescription("You forgot to provide who to lookup!");
			return;
		}
		
		User target = event.getMessage().getMentionedUsers().get(0);
		
		List<Punishment> Punishments = new ArrayList<>();
		
		// Make a for statement for each punishment type, since they have their own tables on the database
		new Thread() {
			public void run() {
				try {
					Connection conn = Database.get().getConnection();
					
					// Kicks
					ResultSet kickResults = conn.createStatement().executeQuery("SELECT * FROM `Punishments_Kicks` WHERE `TARGET` = '" + target.getId() + "';");
					while(kickResults.next()) {
						Punishment p = new Punishment(kickResults.getString("MODERATOR"), kickResults.getString("TARGET"), kickResults.getString("REASON"), Long.valueOf(kickResults.getString("TIMESTAMP")), PunishmentType.KICK);
						Punishments.add(p);
					}
					
					// Warnings
					ResultSet warnResults = conn.createStatement().executeQuery("SELECT * FROM `Punishments_Warns` WHERE `TARGET` = '" + target.getId() + "';");
					while(warnResults.next()) {
						Punishment p = new Punishment(kickResults.getString("MODERATOR"), kickResults.getString("TARGET"), kickResults.getString("REASON"), Long.valueOf(kickResults.getString("TIMESTAMP")), PunishmentType.WARNING);
						Punishments.add(p);
					}
					
				} catch(SQLException ex) {
					LogUtil.info("Database", "Error: " + ex.getMessage());
					ex.printStackTrace();
					return;
				}
				
				for(Punishment punish : Punishments) {
					EmbedBuilder p = new EmbedBuilder();
					p.setColor(punish.getType().getColor());
					p.setTitle(punish.getType().getName());
					p.addField("Punishment Type", punish.getType().getName());
					try {
						p.addField("Moderator", Start.getDiscord().getUserById(punish.getModId()).get().getName());
						p.addField("Target", Start.getDiscord().getUserById(punish.getTargetId()).get().getName());
					} catch (InterruptedException | ExecutionException e) {
						LogUtil.info("Punish", "Something bad happened when building the punishment history of someone! Have a stack trace!");
						e.printStackTrace();
						EmbedBuilder err = new EmbedBuilder();
						err.setColor(Color.RED);
						err.setTitle("Internal Server Error");
						err.setDescription("An Internal server error has occured! Please let Atticus#6362 know ASAP!");
						event.getChannel().sendMessage(err);
						return;
					}
					p.addField("Reason", punish.getReason());
					//TODO: Fix this one later, so we can see the actual time that the punishment was added
					//p.addField("Time", convertToTime(punish.getTimestamp()));
					
					event.getChannel().sendMessage(p);
					return;
				}
			}
		}.start();
	}

}
