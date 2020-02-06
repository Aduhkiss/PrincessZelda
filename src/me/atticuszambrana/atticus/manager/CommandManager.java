package me.atticuszambrana.atticus.manager;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.atticus.Start;
import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.commands.impl.basic.HelpCommand;
import me.atticuszambrana.atticus.commands.impl.dev.RefreshPermsCommand;
import me.atticuszambrana.atticus.commands.impl.dev.TestCommand;
import me.atticuszambrana.atticus.commands.impl.dev.UpdateRankCommand;
import me.atticuszambrana.atticus.commands.impl.dev.UserInfoCommand;
import me.atticuszambrana.atticus.permissions.Rank;
import me.atticuszambrana.atticus.util.LogUtil;
import me.atticuszambrana.atticus.util.StringUtil;

public class CommandManager implements MessageCreateListener {
	
	private Map<String, Command> Commands = new HashMap<>();
	private final String PREFIX = "//";
	
	// Method run by the main class to get all commands registered, I will be sorting them into their individual groups with comments
	public void registerCommands() {
		// Developer Commands
		register(new TestCommand());
		register(new UserInfoCommand());
		register(new RefreshPermsCommand());
		//register(new UpdateRankCommand());
		
		// Basic Commands
		register(new HelpCommand());
	}
	

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(!event.getMessageContent().startsWith(PREFIX)) {
			return;
		}
		
		for(Map.Entry<String, Command> cmd : Commands.entrySet()) {
			if(event.getMessageContent().startsWith(PREFIX + cmd.getKey())) {
				
				// Permission check system
				Rank myRank = Start.getPermManager().getRank(event.getMessageAuthor().asUser().get());
				if(myRank.getPower() >= cmd.getValue().getRankRequired().getPower()) {
					cmd.getValue().execute(StringUtil.toArray(event.getMessageContent()), event.getMessageAuthor(), event.getChannel());
					// Then log it
					LogUtil.info("Command Manager", event.getMessage().getAuthor().getName() + " ran " + event.getMessageContent());
					return;
				} else {
					LogUtil.info("Command Manager", event.getMessageAuthor().getName() + " tried to run " + event.getMessageContent() + " however doesnt have permission.");
					
					EmbedBuilder embed = new EmbedBuilder();
					
					embed.setColor(Color.RED);
					embed.setTitle("You cannot do this!");
					embed.setDescription("This action requires Permission Rank [" + cmd.getValue().getRankRequired().getName().toUpperCase() + "].");
					
					event.getChannel().sendMessage(embed);
					
					return;
				}
			}
		}
	}
	
	private void register(Command cmd) {
		Commands.put(cmd.getName(), cmd);
	}
	
	public Command getCommand(String name) {
		return Commands.get(name);
	}
	
	public Map<String, Command> getCommands() {
		return Commands;
	}

}
