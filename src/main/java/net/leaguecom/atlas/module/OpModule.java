package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;
import net.leaguecom.atlas.command.Command;
import net.leaguecom.atlas.command.OpCommand;

import org.pircbotx.hooks.types.GenericMessageEvent;

public class OpModule implements Module {
	public void execute(String cmd, String txt, GenericMessageEvent event) {
		Atlas bot = event.getBot();
		if(!bot.isAdmin(event.getUser())) {
			event.respond("Only admins can do that.");
			return;
		}
		
		Command command = null;

		switch (cmd) {
		case "op":
		case "deop":
			command = new OpCommand(cmd.equals("op"), txt, bot);
			break;

		case "register":
			command = new RegisterCommand(txt);
			break;
		}
		
		command.execute();
	}

	public String help(String cmd) {
		switch (cmd) {
		case "op":
			return "Give op to a user in all channels teh bot controls.";
		case "deop":
			return "Remove op from a user in all channels the bot controls.";
		default:
			return String.format("Unknown command: %s", cmd);
		}
	}
}
