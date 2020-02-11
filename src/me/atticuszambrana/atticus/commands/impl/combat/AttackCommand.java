package me.atticuszambrana.atticus.commands.impl.combat;

import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;

public class AttackCommand extends Command {
	
	public AttackCommand() {
		// The required rank will be developer until I have all the bugs and problems worked out
		super("atk", "Attack another user", Rank.DEVELOPER);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		
	}

}
