package me.atticuszambrana.atticus.commands.impl.dev;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class UserInfoCommand extends Command {
	
	public UserInfoCommand() {
		super("userinfo", "Display basic information about your account", Rank.NONE);
	}

	@Override
	public void execute(String[] args, MessageAuthor author, TextChannel channel) {
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(Color.BLUE);
		embed.setTitle("Your account info");
		embed.addField("Name", author.getName());
		embed.addField("Rank", Start.getPermManager().getRank(author.asUser().get()).getName());
		
		channel.sendMessage(embed);
		return;
	}

}
