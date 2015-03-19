package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class JoinModule implements Module {
	
	public void execute(String cmd, String txt, GenericMessageEvent event) {
		Atlas bot = event.getBot();
		if(!bot.isAdmin(event.getUser())) {
			event.respond("Only admins can do that.");
			return;
		}

		String channel = null, key = null;
		
		if(txt != null) {
			String[] tmp = txt.split(" ", 2);
			channel = tmp[0];
			key = tmp.length == 2 ? tmp[1] : null;
		}
		
		switch (cmd) {
		case "part":
			if(channel == null) {
				if(event instanceof MessageEvent) {
					((MessageEvent) event).getChannel().send().part();
				} else {
					event.respond("No channel specified");
					return;
				}
			} else {
				bot.getUserChannelDao().getChannel(channel).send().part(channel);
			}
			break;

		case "join":
			if(channel == null) {
				event.respond("No channel specified.");
				return;
			}
			
			if(key != null) {
				bot.sendIRC().joinChannel(channel, key);
			} else {
				bot.sendIRC().joinChannel(channel);
			}
			break;
		}
	}

	public String help(String cmd) {
		switch (cmd) {
		case "join":
			return "Join a channel.";
		case "par":
			return "Part from a channel.";
		default:
			return String.format("Unknown command: %s", cmd);
		}
	}
	
}
