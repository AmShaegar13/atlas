package net.leaguecom.atlas.module;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import net.leaguecom.atlas.Atlas;
import net.leaguecom.atlas.command.Command;

import org.pircbotx.hooks.types.GenericMessageEvent;

public class RegisterCommand implements Command {

	private String channel;
	private String description;
	private GenericMessageEvent event;

	public RegisterCommand(String channel, String description, GenericMessageEvent event) {
		this.channel = channel;
		this.description = description;
		this.event = event;
	}
	
	@Override
	public void execute() {
		Atlas bot = event.getBot();
		
		bot.sendIRC().joinChannel(channel);
		if(!bot.getUserChannelDao().getChannel(channel).isOp(bot.getUserBot())) {
			bot.getUserChannelDao().getChannel(channel).send().part();
			event.respond("Channel cannot be registered. I am not Operator in it.");
			return;
		}
		
		Properties config = new Properties();
		try {
			config.load(new FileReader("config.ini"));
		} catch (IOException e) {
			event.respond("Error: Cannot load config file.");
			return;
		}

		bot.sendIRC().message("ChanServ", String.format("REGISTER %s %s %s", channel, config.getProperty("founder"), description));
		bot.sendIRC().message("ChanServ", String.format("SET %s SECURE off", channel));
		bot.sendIRC().message("ChanServ", String.format("SET %s SECUREFOUNDER off", channel));
		bot.sendIRC().message("ChanServ", String.format("SET %s TOPICLOCK on", channel));
		bot.sendIRC().message("ChanServ", String.format("SET %s MLOCK +cnt", channel));

		bot.sendIRC().message("ChanServ", String.format("LEVELS %s DIS HALFOP", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s DIS HALFOPME", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s DIS AUTOPROTECT", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s DIS FANTASIA", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s DIS PROTECTME", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s SET OPDEOP 10", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s SET SET 10", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s SET TOPIC 10", channel));
		bot.sendIRC().message("ChanServ", String.format("LEVELS %s SET INFO 10", channel));
	}

}
