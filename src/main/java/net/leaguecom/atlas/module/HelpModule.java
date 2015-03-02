package net.leaguecom.atlas.module;

import java.util.Map;
import java.util.Map.Entry;

import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.output.OutputUser;

public class HelpModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent event) {
		Map<String, Module> modMap = ModuleManager.getInstance().getModuleMap();
		OutputUser out = event.getUser().send();
		
		for(Entry<String, Module> e : modMap.entrySet()) {
			Module mod = e.getValue();
			cmd = e.getKey();
			out.notice(String.format("  !%-8s - %s", cmd, mod.help(cmd)));
		}
	}

	public String help(String cmd) {
		return "Print this help text.";
	}
}
