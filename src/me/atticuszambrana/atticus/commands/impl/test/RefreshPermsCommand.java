package me.atticuszambrana.atticus.commands.impl.test;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;
import me.atticuszambrana.atticus.util.LogUtil;

public class RefreshPermsCommand extends Command {
	
	public RefreshPermsCommand() {
		super("refreshperms", "Refresh Permissions Matrix from the database", Rank.DEVELOPER);
	}

	@Override
	public void execute(String[] args, MessageAuthor author, TextChannel channel) {
		// Ok
		LogUtil.info("Permissions", "Refreshing Permissions Matrix due to command from " + author.getName() + ".");
		Start.getPermManager().setupPermissions();
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(Color.GREEN);
		embed.setTitle("Success!");
		embed.setDescription("You have successfully refreshed the Permissions Matrix! This action has been logged.");
		
		channel.sendMessage(embed);
		
		return;
	}

}
