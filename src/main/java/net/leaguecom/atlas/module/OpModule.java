package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.output.OutputUser;

public class OpModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent<Atlas> event) {
		Atlas bot = event.getBot();
		User user = bot.getUserChannelDao().getUser(txt);

		if(!bot.isAdmin(event.getUser())) {
			event.respond("Only Atlas admins can do that.");
			return;
		}
		
		char modifier = '-';
		switch (cmd) {
		case "op":
			modifier = '+';
			break;
		case "deop":
			modifier = '-';
			break;
		}

		OutputUser out = bot.getUserChannelDao().getUser("Chanserv").send();
		for(Channel c : bot.getUserBot().getChannelsOpIn()) {
			out.message(String.format("FLAGS %s %s %cOo", c.getName(), user.getNick(), modifier));
		}
	}

	public String help(String cmd) {
		switch (cmd) {
		case "op":
			return "Give +O to a user in all channels the bot is operator in.";
		case "deop":
			return "Remove +O from a user in all channels the bot is op in.";
		default:
			return String.format("Unknown command: %s", cmd);
		}
	}
}
