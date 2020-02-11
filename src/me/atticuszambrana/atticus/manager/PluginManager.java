package me.atticuszambrana.atticus.manager;

import java.util.HashMap;
import java.util.Map;

import me.atticuszambrana.atticus.Plugin;
import me.atticuszambrana.atticus.activity.Activity;
import me.atticuszambrana.atticus.pvp.PvPEngine;

public class PluginManager {
	
	// Static class to keep track of our plugins in the code
	
	private static Map<Integer, Plugin> Plugins = new HashMap<>();
	
	public static void registerPlugins() {
		// Void called by the main class to register all of the plugins
		Plugins.put(1, new Activity());
		Plugins.put(2, new PvPEngine());
	}
	
	public Plugin getPlugin(int id) {
		return Plugins.get(id);
	}
	
	public Map<Integer, Plugin> getPlugins() {	
		return Plugins;
	}
}
