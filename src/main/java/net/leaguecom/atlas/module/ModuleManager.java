package net.leaguecom.atlas.module;

import java.util.HashMap;
import java.util.Map;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.hooks.types.GenericMessageEvent;

public class ModuleManager {
	private static ModuleManager self;
	private Map<String, Module> cmdMap = new HashMap<String, Module>();

	private ModuleManager() {
		// nothing to do here
	}
	
	public static ModuleManager getInstance() {
		if(self == null) {
			self = new ModuleManager();
		}
		return self;
	}
	
	public void registerCommand(String cmd, Module mod) {
		cmdMap.put(cmd, mod);
	}
	
	public void execute(String cmd, String txt, GenericMessageEvent<Atlas> event) {
		if(!cmdMap.containsKey(cmd)) {
			event.respond(String.format("Unknown command: %s", cmd));
		}
		
		cmdMap.get(cmd).execute(cmd, txt, event);
	}
	
	public Map<String, Module> getModuleMap() {
		return cmdMap;
	}
}
