package me.atticuszambrana.atticus.commands.impl.basic;

import java.awt.Color;
import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Display commands that you have access to", Rank.NONE);
	}

	@Override
	public void execute(String[] args, MessageAuthor author, TextChannel channel) {
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(Color.CYAN);
		embed.setTitle("Command List");
		
		Rank myRank = Start.getPermManager().getRank(author.asUser().get());
		
		for(Map.Entry<String, Command> cmd : Start.getCommandManager().getCommands().entrySet()) {
			if(myRank.getPower() >= cmd.getValue().getRankRequired().getPower()) {
				embed.addField(cmd.getKey(), cmd.getValue().getDescription());
			}
		}
		
		channel.sendMessage(embed);
		return;
	}

}
