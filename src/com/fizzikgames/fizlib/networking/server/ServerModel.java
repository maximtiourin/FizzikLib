package com.fizzikgames.fizlib.networking.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import com.fizzikgames.fizlib.networking.Model;
import com.fizzikgames.fizlib.networking.server.commands.SvClose;
import com.fizzikgames.fizlib.networking.server.commands.SvOpen;
import com.fizzikgames.fizlib.networking.server.commands.SvTitle;

/**
 * The server model for a fizzik server.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class ServerModel extends Model {
	private static final String DEFAULT_SERVER_TITLE = "Fizzik Server";
	protected Server server;
	private String title;
	
	public ServerModel(String aTitle) {
		super();
		
		server = new Server();
		
		if (aTitle == null || aTitle.length() <= 0) setTitle(DEFAULT_SERVER_TITLE + " " + System.currentTimeMillis()); //Default title until one is supplied
		else setTitle(aTitle);
	}
	
	@Override
	protected void registerDefaultCommands() {
		registerCommand(new SvClose(this));
		registerCommand(new SvOpen(this));
		registerCommand(new SvTitle(this));
	}
	
	/**
	 * Open the server on the tcp and udp ports
	 * @param tcp the tcp port to open on
	 * @param udp the udp port to open on
	 * @throws IOException 
	 */
	public void open(int tcp, int udp) throws IOException {
		this.tcp = tcp;
		this.udp = udp;
		openConnection();
	}
	
	/**
	 * Open the server on the tcp port only
	 * @param tcp the tcp port to open on
	 * @throws IOException 
	 */
	public void open(int tcp) throws IOException {
		this.tcp = tcp;
		this.udp = NONE;
		openConnection();
	}
	
	@Override
	protected void openConnection() throws IOException {
		if (!isRunning()) {
			if (tcp == NONE) {
				//No ports to open on, abort!
				logTime("Unable to open server connection. No valid port specified (TCP: " + tcp + ").\n");
			}
			else {
				if (udp != NONE) {
					try {
						server.bind(tcp, udp); //Open on tcp and udp ports
						logTime("Server connection opened on ports (TCP: " + tcp + ", UDP: " + udp + ").\n");
						
						server.start();
						setRunning(true);
					}
					catch (IllegalArgumentException e) {
						logTime("Invalid Ports Specified. Out of Range. (TCP: " + tcp + " UDP: " + udp + ").\n");
					}
				}
				else {
					try {
						server.bind(tcp); //Open on tcp port only
						logTime("Server connection opened on port (TCP: " + tcp + ").\n");
						
						server.start();
						setRunning(true);
					}
					catch (IllegalArgumentException e) {
						logTime("Invalid Port Specified. Out of Range. (TCP: " + tcp + ").\n");
					}
				}
			}
		}
		else {
			logTime("Unable to open new server connection, please close the current one.\n");
		}
		
		notifyChangeListeners();
	}
	
	@Override
	public void close() {
		if (isRunning()) {
			logTime("Server connection closed.\n");
			
			server.stop();
			setRunning(false);
		}
		else {
			logTime("Unable to close server connection, no connection to close!\n");
		}
		
		notifyChangeListeners();
	}
	
	/**
	 * Sets the title of the server.
	 * @param title the title of the server.
	 */
	public void setTitle(String title) {
		this.title = title;
		
		logTime("Server title changed to '" + getTitle() + "'.\n");
	}
	
	/**
	 * Returns the title of the server.
	 * @return String the title of the server.
	 */
	public String getTitle() {
		return title;
	}

	@Override
	protected EndPoint getEndPoint() {
		return server;
	}
}
