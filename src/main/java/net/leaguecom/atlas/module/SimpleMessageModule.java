package net.leaguecom.atlas.module;

import org.pircbotx.Channel;
import org.pircbotx.hooks.types.GenericChannelEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.output.GenericChannelUserOutput;

public class SimpleMessageModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent event) {
		GenericChannelUserOutput out = null;
		if(event instanceof GenericChannelEvent) {
			Channel channel = ((GenericChannelEvent) event).getChannel();
			if(channel != null) {
				out = channel.send();
			}
		}
		if(out == null) {
			out = event.getUser().send();
		}
		
		out.action("performs a simple action!");
		out.message("This is a simple message!");
		out.notice("This is a simple notice!");
	}

	public String help(String cmd) {
		return "Print this help text.";
	}
}
