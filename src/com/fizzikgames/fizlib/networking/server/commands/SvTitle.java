package com.fizzikgames.fizlib.networking.server.commands;

import com.fizzikgames.fizlib.console.ConsoleCommand;
import com.fizzikgames.fizlib.networking.server.ServerModel;

/**
 * Server Command
 * <br>Sets the title of the server.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class SvTitle implements ConsoleCommand {
	private ServerModel model;
	
	public SvTitle(ServerModel model) {
		this.model = model;
	}
	
	@Override
	public String getCommandName() {
		return "sv_title";
	}

	@Override
	public String getCommandDescription() {
		return "Changes the title of the server.";
	}

	@Override
	public String getCommandUseInfo() {
		return "sv_title <\"title\">";
	}

	@Override
	public int getMaximumArguments() {
		return 1;
	}

	@Override
	public int getMinimumArguments() {
		return 1;
	}

	@Override
	public String executeCommand(String[] args) {
		if (args.length == 1) {
			model.setTitle(args[0]);
		}
		return "";
	}
}
