package net.leaguecom.atlas;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import net.leaguecom.atlas.listener.CommandListener;
import net.leaguecom.atlas.module.HelpModule;
import net.leaguecom.atlas.module.Module;
import net.leaguecom.atlas.module.ModuleManager;
import net.leaguecom.atlas.module.OpModule;
import net.leaguecom.atlas.module.SimpleMessageModule;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.exception.IrcException;

public class Atlas extends PircBotX {
	private String master;
	
	public Atlas(Configuration configuration) {
		super(configuration);
	}
	
	public void setMaster(String channel) {
		master = channel;
	}
	
	public boolean isAdmin(User user) {
		return getUserChannelDao().getChannel(master).isOp(user);
	}

	public static void main(String[] args) throws IOException, IrcException {
		Properties config = new Properties();
		config.load(new FileReader("config.ini"));
		
		Builder builder = new Configuration.Builder()
				.setName(config.getProperty("nick"))
				.setNickservPassword(config.getProperty("ident"))
				.addServer(config.getProperty("host"), Integer.parseInt(config.getProperty("port")))
				.addListener(new CommandListener());
		
		String[] channels = config.getProperty("channels").split(",");
		String master = config.getProperty("master");
		builder.addAutoJoinChannel(master);
		
		if(!Boolean.parseBoolean(config.getProperty("debug"))) {
			for(String channel : channels) {
				builder.addAutoJoinChannel(channel);
			}
		}
		
		boolean useSSL = Boolean.parseBoolean(config.getProperty("ssl"));
		
		if(useSSL) {
			builder.setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates());
		}
		
		initCommands();

		Atlas bot = new Atlas(builder.buildConfiguration());
		bot.setMaster(master);
		bot.startBot();
	}

	private static void initCommands() {
		ModuleManager cmdMan = ModuleManager.getInstance();

		cmdMan.registerCommand("help", new HelpModule());
		Module opMod = new OpModule();
		cmdMan.registerCommand("op", opMod);
		cmdMan.registerCommand("deop", opMod);
		cmdMan.registerCommand("register", opMod);
		Module msgMod = new SimpleMessageModule();
		cmdMan.registerCommand("msg", msgMod);
	}
}
