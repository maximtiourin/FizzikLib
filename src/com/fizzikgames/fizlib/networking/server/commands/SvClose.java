package com.fizzikgames.fizlib.networking.server.commands;

import com.fizzikgames.fizlib.console.ConsoleCommand;
import com.fizzikgames.fizlib.networking.server.ServerModel;

/**
 * Server Command
 * <br>Closes the current server connection.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class SvClose implements ConsoleCommand {
	private ServerModel model;
	
	public SvClose(ServerModel model) {
		this.model = model;
	}
	
	@Override
	public String getCommandName() {
		return "sv_close";
	}

	@Override
	public String getCommandDescription() {
		return "Closes the server's current open connection, if there is one.";
	}

	@Override
	public String getCommandUseInfo() {
		return getCommandName();
	}

	@Override
	public int getMaximumArguments() {
		return 0;
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public String executeCommand(String[] args) {
		model.close();
		
		return "";
	}			
}
