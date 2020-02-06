package me.atticuszambrana.atticus.manager;

import java.util.HashMap;
import java.util.Map;

import me.atticuszambrana.atticus.Plugin;

public class PluginManager {
	
	// Static class to keep track of our plugins in the code
	
	private Map<Integer, Plugin> Plugins = new HashMap<>();
	
	public static void registerPlugins() {
		// Void called by the main class to register all of the plugins
	}
	
	public Plugin getPlugin(int id) {
		return Plugins.get(id);
	}
	
	public Map<Integer, Plugin> getPlugins() {	
		return Plugins;
	}
}
