package net.leaguecom.atlas.module;

import net.leaguecom.atlas.command.Command;

public class RegisterCommand implements Command {

	private String channel;

	public RegisterCommand(String channel) {
		this.channel = channel;
	}
	
	@Override
	public void execute() {
		
	}

}
