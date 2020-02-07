package me.atticuszambrana.atticus.commands.impl.dev;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class RestartCommand extends Command {
	
	public RestartCommand() {
		super("restart", "Restart the bot's current instance", Rank.DEVELOPER);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
	}

}
