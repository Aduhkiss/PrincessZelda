package me.atticuszambrana.atticus.commands.impl.dev;

import java.awt.Color;
import java.sql.SQLException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.database.Database;
import me.atticuszambrana.atticus.permissions.Rank;

public class UpdateRankCommand extends Command {
	
	public UpdateRankCommand() {
		super("updaterank", "Update the rank of the given user in the bots database", Rank.DEVELOPER);
	}

	@Override
	public void execute(String[] args, MessageAuthor author, TextChannel channel) {
		if(args.length != 3) {
			EmbedBuilder err = new EmbedBuilder();
			err.setColor(Color.RED);
			err.setTitle("Error");
			err.setDescription("You provided too many, or not enough arguments for this command.");
			channel.sendMessage(err);
			return;
		}
		
		// First, lets get the arguments that the user passed us, then save them to their own variables
		String target = args[1];
		String rank = args[2];
		
		// Then make sure this rank exists
		Rank testFor;
		try {
			testFor = Rank.valueOf(args[2]);
		} catch(IllegalArgumentException ex) {
			EmbedBuilder err = new EmbedBuilder();
			err.setColor(Color.RED);
			err.setTitle("Error");
			err.setDescription("You gave an invalid rank name. Please check with a bot developer for rank names.");
			channel.sendMessage(err);
			return;
		}
		
		// Then lets try pushing this to the database and sending a success message
//		try {
//			
//			Database db = Database.get();
//			
//			db.getConnection().createStatement().executeUpdate("")
//			
//		} catch(SQLException ex) {
//			EmbedBuilder err = new EmbedBuilder();
//			err.setColor(Color.RED);
//			err.setTitle("Error");
//			err.setDescription("A database error occured when we tried to update this rank. Please notify a bot developer immediately.");
//			channel.sendMessage(err);
//		}
		
		return;
	}
	
	

}
