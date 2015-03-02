package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

public class SimpleMessageModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent event) {
		Atlas bot = event.getBot();
		
		if(event instanceof MessageEvent) {
			OutputChannel out = ((MessageEvent) event).getChannel().send();
		} else {
			OutputUser out = event.getUser().send();
		}
	}

	public String help(String cmd) {
		return "Print this help text.";
	}
}
