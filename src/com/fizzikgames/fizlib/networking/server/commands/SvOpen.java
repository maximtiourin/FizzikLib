package com.fizzikgames.fizlib.networking.server.commands;

import java.io.IOException;

import com.fizzikgames.fizlib.console.ConsoleCommand;
import com.fizzikgames.fizlib.networking.server.ServerModel;
import com.fizzikgames.fizlib.util.StringUtil;

/**
 * Server Command
 * <br>Opens a new server connection.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class SvOpen implements ConsoleCommand {
	private ServerModel model;
	
	public SvOpen(ServerModel model) {
		this.model = model;
	}
	
	@Override
	public String getCommandName() {
		return "sv_open";
	}

	@Override
	public String getCommandDescription() {
		return "Opens a connection of the given port(s) if the server has no current connection.";
	}

	@Override
	public String getCommandUseInfo() {
		return getCommandName() + " <tcpPort> [<udpPort>]";
	}

	@Override
	public int getMaximumArguments() {
		return 2;
	}

	@Override
	public int getMinimumArguments() {
		return 1;
	}

	@Override
	public String executeCommand(String[] args) {
		String output = "";
		
		if ((args.length >= getMinimumArguments()) && (args.length <= getMaximumArguments())) {
			boolean open = false;
			int tcp = -1, udp = -1;
			
			//TCP
			String port = args[0];
			
			if (port != null) {
				if (StringUtil.isNumeric(port, false)) {
					try {
						tcp = Integer.valueOf(port);
						open = true;
					}
					catch (NumberFormatException e) {
						output = "Invalid TCP Port entered (TCP: " + port + ").\n";
					}
					
					//UDP
					if ((args.length == 2) && (tcp != -1)) {
						port = args[1];
						udp = Integer.valueOf(port);
						
						if (port != null) {
							if (StringUtil.isNumeric(port, false)) {
								try {
									udp = Integer.valueOf(port);
								}
								catch (NumberFormatException e) {
									output = "Invalid UDP Port entered (UDP: " + port + ").\n";
								}
							}
							else {
								output = "Invalid UDP Port entered (UDP: " + port + ").\n";
								open = false;
							}
						}
					}		
				}
				else {
					output = "Invalid TCP Port entered (TCP: " + port + ").\n";
				}
			}
			
			//Attempt to open
			try {
				if (open) {
					if (args.length == 2) {
						model.open(tcp, udp);
					}
					else {
						model.open(tcp);
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				output = "Unable to open connection. IOException.\n";
			}
		}
		
		return output;
	}
}
