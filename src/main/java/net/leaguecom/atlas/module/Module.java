package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.hooks.types.GenericMessageEvent;

public interface Module {
	public void execute(String cmd, String txt, GenericMessageEvent<Atlas> event);
	public String help(String cmd);
}
