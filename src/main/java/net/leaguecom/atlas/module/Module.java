package net.leaguecom.atlas.module;

import org.pircbotx.hooks.types.GenericMessageEvent;

public interface Module {
	public void execute(String cmd, String txt, GenericMessageEvent event);
	public String help(String cmd);
}
