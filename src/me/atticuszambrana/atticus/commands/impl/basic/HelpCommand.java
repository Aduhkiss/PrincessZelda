package me.atticuszambrana.atticus.commands.impl.basic;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;

import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Display commands that you have access to", Rank.NONE);
	}

	@Override
	public void execute(String[] args, MessageAuthor author, TextChannel channel) {
		
		for(Command cmd : )
		
	}

}
