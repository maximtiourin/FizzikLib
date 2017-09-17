package com.fizzikgames.fizlib.networking.client.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fizzikgames.fizlib.console.ConsoleCommand;
import com.fizzikgames.fizlib.networking.client.ClientModel;
import com.fizzikgames.fizlib.util.StringUtil;

public class ClConnect implements ConsoleCommand {
	private ClientModel model;
	
	public ClConnect(ClientModel model) {
		this.model = model;
	}

	@Override
	public String getCommandName() {
		return "cl_connect";
	}

	@Override
	public String getCommandDescription() {
		return "Attempts to connect to the ip address on the given port(s).";
	}

	@Override
	public String getCommandUseInfo() {
		return getCommandName() + " <hostName> <tcpPort> [<udpPort>].";
	}

	@Override
	public int getMaximumArguments() {
		return 3;
	}

	@Override
	public int getMinimumArguments() {
		return 2;
	}

	@Override
	public String executeCommand(String[] args) {
		String output = "";
		
		if ((args.length >= getMinimumArguments()) && (args.length <= getMaximumArguments())) {
			boolean open = false;
			int tcp = -1, udp = -1;
			
			//IP
			String ip = args[0];
			InetAddress ina = null;
			
			try {
				ina = InetAddress.getByName(ip);
			
				if (ina.isReachable(300)) {				
					//TCP
					String port = args[1];
					
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
							if ((args.length == 3) && (tcp != -1)) {
								port = args[2];
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
				}
				else {
					output = "Invalid host name entered (Host: " + ip + ").\n";
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Attempt to open
			try {
				if (open) {
					if (args.length == 3) {
						model.connect(ina, tcp, udp);
					}
					else {
						model.connect(ina, tcp, udp);
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
