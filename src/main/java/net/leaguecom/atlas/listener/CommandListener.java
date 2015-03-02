package net.leaguecom.atlas.listener;

import net.leaguecom.atlas.module.ModuleManager;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CommandListener extends ListenerAdapter {
	@Override
	public void onGenericMessage(GenericMessageEvent event)
			throws Exception {
		String msg = event.getMessage();

		if (msg.startsWith("!")) {
			String[] split = msg.substring(1).split(" ", 2);
			ModuleManager.getInstance().execute(split[0],
					split.length == 2 ? split[1] : null, event);
		}
	}
}
