package me.atticuszambrana.atticus.commands.impl.basic;

import java.awt.Color;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Display commands that you have access to", Rank.NONE);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(Color.CYAN);
		embed.setTitle("Command List");
		
		Rank myRank = Start.getPermManager().getRank(event.getMessageAuthor().asUser().get());
		
		for(Map.Entry<String, Command> cmd : Start.getCommandManager().getCommands().entrySet()) {
			if(myRank.getPower() >= cmd.getValue().getRankRequired().getPower()) {
				embed.addField(cmd.getKey(), cmd.getValue().getDescription());
			}
		}
		
		event.getChannel().sendMessage(embed);
		return;
	}

}
