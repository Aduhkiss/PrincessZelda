package me.atticuszambrana.atticus;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import me.atticuszambrana.atticus.database.Database;
import me.atticuszambrana.atticus.manager.CommandManager;
import me.atticuszambrana.atticus.manager.PermissionsManager;
import me.atticuszambrana.atticus.manager.PluginManager;
import me.atticuszambrana.atticus.pvp.PvPEngine;
import me.atticuszambrana.atticus.util.LogUtil;
import me.atticuszambrana.atticus.util.UtilTime;
import me.atticuszambrana.atticus.util.UtilTime.TimeUnit;

public class Start {
	
	private static DiscordApi api;
	private static final boolean DEV_MODE = true;
	
	// Stuff
	private static PermissionsManager permissions;
	private static CommandManager commandManager;
	private static PvPEngine pvp;
	
	 public static void main(String[] args) {
		 LogUtil.info("System", "Welcome to the Atticus Discord Bot!");
		 
		 // Grab the time as of now
		 long epoch = System.currentTimeMillis();
		 
		 String TOKEN = null;
		 if(!DEV_MODE) {
			 if(args == null || args.length == 0) {
				 System.out.println("You are required to give Discord API Token as the only argument!");
				 System.exit(0);
			 }
			 
			 TOKEN = args[0];
		 } else {
			 // DELETE THIS BEFORE OPEN SOURCING LMAO
			 TOKEN = "Njc0NDkxMDM1NzY1NzAyNjg4.XjpW4w.kCKxjZpy0lVnqnVm2PrqoCpBk_k";
		 }
		 
		 LogUtil.info("System", "Logging into Discord...");
		 
		 // Get all of the discord api stuff ready for work
		api = new DiscordApiBuilder()
				.setToken(TOKEN)
				.login()
				.join();
		
		// Make the Activity display something about starting up
		Start.getDiscord().updateActivity(ActivityType.PLAYING, "Starting please wait...");
		
		// Setup the Database object
		Database db = Database.get();
		
		// Setup the Permissions Manager
		permissions = new PermissionsManager(db);
		
		// Start the Discord message listener so we can handle our commands, then hook it to the Command Manager
		LogUtil.info("Command Manager Hook", "Starting Command Manager...");
		commandManager = new CommandManager();
		commandManager.registerCommands();
		api.addMessageCreateListener(commandManager);
		
		// Start the plugins with the plugin manager
		LogUtil.info("Plugin Manager", "Starting Plugin Manager...");
		PluginManager.registerPlugins();
		
		// Start the new "pvp engine alpha test"
		LogUtil.info("PvP Engine", "Starting PvP Engine...");
		pvp = new PvPEngine();
		
		LogUtil.info("System", "All Done! Successfully started in " + UtilTime.convertString(System.currentTimeMillis() - epoch, 1, TimeUnit.FIT) + ".");
	 }
	 
	 public static DiscordApi getDiscord() {
		 return api;
	 }
	 
	 public static PermissionsManager getPermManager() {
		 return permissions;
	 }
	 public static CommandManager getCommandManager() {
		 return commandManager;
	 }
	 public static PvPEngine getPvPEngine() {
		 return pvp;
	 }
}
