package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.hooks.types.GenericMessageEvent;

public class SimpleMessageModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent<Atlas> event) {
		Atlas bot = event.getBot();
		
		if(!bot.isAdmin(event.getUser())) {
			event.respond("Only admins can do that.");
			return;
		}
		
		String[] tmp = txt.split(" ", 2);
		String target = tmp[0];
		String msg = tmp[1];

		bot.sendIRC().message(target, msg);
	}

	public String help(String cmd) {
		return "Send message to channel or user.";
	}
}
