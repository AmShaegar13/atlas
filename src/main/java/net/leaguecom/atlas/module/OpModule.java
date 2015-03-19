package net.leaguecom.atlas.module;

import net.leaguecom.atlas.Atlas;
import net.leaguecom.atlas.command.Command;
import net.leaguecom.atlas.command.OpCommand;
import net.leaguecom.atlas.command.RegisterCommand;

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
			String[] tmp = txt.split(" ", 2);
			String channel = tmp[0];
			String descr = tmp[1];
			
			command = new RegisterCommand(channel, descr, event);
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
		case "register":
			return "Register a new channel with a custom description.";
		default:
			return String.format("Unknown command: %s", cmd);
		}
	}
	
}
