package me.atticuszambrana.atticus.commands.impl.punish;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.atticus.commands.Command;
import me.atticuszambrana.atticus.permissions.Rank;
import me.atticuszambrana.atticus.util.LogUtil;
import me.atticuszambrana.atticus.util.StringUtil;

public class KickCommand extends Command {
	
	public KickCommand() {
		super("kick", "Kick a user from the server", Rank.MODERATOR);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		if(args.length >= 1) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("Missing Arguments: <Target>, <Reason>");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		User target = getUser(event.getMessage().getMentionedUsers());
		String reason = StringUtil.combine(args, 2);
		
		LogUtil.info("Punish", event.getMessageAuthor().getName() + " has kicked " + target.getName() + " for: " + reason);
		
		//TODO: Lookup the channel that we want to post notifications to, then give a notification for the kick
		
		// The [ZIP] Tag marks the punishment, as Zelda Issued Punishment
		event.getServer().get().kickUser(target, reason + " [ZIP]");
		return;
	}
	
	private User getUser(List<User> users) {
		return users.get(0);
	}

}
