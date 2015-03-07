package net.leaguecom.atlas.command;

import net.leaguecom.atlas.Atlas;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class OpCommand implements Command {
	
	private boolean op;
	private String nick;
	private Atlas bot;

	public OpCommand(boolean op, String nick, Atlas bot) {
		this.op = op;
		this.nick = nick;
		this.bot = bot;
	}

	@Override
	public void execute() {
		User user = bot.getUserChannelDao().getUser(nick);

		String add_del = op ? "ADD" : "DEL";
		String op_deop = op ? "OP" : "DEOP";

		for(Channel c : bot.getUserBot().getChannelsOpIn()) {
			bot.sendIRC().message("ChanServ", String.format("ACCESS %s %s %s 10", c.getName(), add_del, user.getNick()));
			bot.sendIRC().message("ChanServ", String.format("%s %s %s", op_deop, c.getName(), user.getNick()));
		}
	}

}
