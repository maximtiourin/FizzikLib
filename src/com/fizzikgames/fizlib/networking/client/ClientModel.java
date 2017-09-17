package com.fizzikgames.fizlib.networking.client;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.fizzikgames.fizlib.networking.Model;
import com.fizzikgames.fizlib.networking.client.commands.ClConnect;

/**
 * The client model for a fizzik client
 * @author Maxim Tiourin
 * @version 1.00
 */
public class ClientModel extends Model {
	protected Client client;
	private InetAddress host;
	private int timeout;
	
	public ClientModel(int timeoutInMilliseconds) {
		super();
		
		client = new Client();
		
		timeout = timeoutInMilliseconds;
	}
	
	/**
	 * Sends the object packet over the client's connection
	 * if one is open using TCP.
	 * @param o the object packet to send.
	 */
	public void sendTCP(Object o) {
		if (isRunning()) {
			client.sendTCP(o);
		}
		else {
			logTime("Unable to send " + o.getClass().getSimpleName() + " TCP packet, no connection open!\n");
		}
		
		notifyChangeListeners();
	}
	
	/**
	 * Sends the object packet over the client's connection
	 * if one is open using UDP.
	 * @param o the object packet to send.
	 */
	public void sendUDP(Object o) {
		if (isRunning()) {
			client.sendUDP(o);
		}
		else {
			logTime("Unable to send " + o.getClass().getSimpleName() + " UDP packet, no connection open!\n");
		}
		
		notifyChangeListeners();
	}
	
	/**
	 * Returns the client's ping to host as a formatted String.
	 * <br> Will return a ping of up to 999 ms or - if
	 * no ping has been returned yet.
	 * @return String the ping in string format
	 */
	public String getPingString() {
		int ping = client.getReturnTripTime();
		
		if (ping == NONE) {
			return "-";
		}
		else {
			return ((int) Math.min(ping, 999)) + "";
		}
	}
	
	@Override
	protected void registerDefaultCommands() {
		registerCommand(new ClConnect(this));
	}
	
	/**
	 * Connects to the host using the tcp and udp ports
	 * @param host the host to connect to
	 * @param tcp the tcp port to use
	 * @param udp the udp port to use
	 * @throws IOException
	 */
	public void connect(InetAddress host, int tcp, int udp) throws IOException {
		this.host = host;
		this.tcp = tcp;
		this.udp = udp;
		openConnection();
	}
	
	/**
	 * Connects to the host using the tcp port
	 * @param host the host to connect to
	 * @param tcp the tcp port to use
	 * @throws IOException
	 */
	public void connect(InetAddress host, int tcp) throws IOException {
		this.host = host;
		this.tcp = tcp;
		this.udp = NONE;
		openConnection();
	}

	@Override
	protected void openConnection() throws IOException {
		if (!isRunning) {
			if (tcp == NONE) {
				//No ports to open on, abort!
				logTime("Unable to open connection to host (" + host.getHostAddress() + "). No valid port specified (TCP: " + tcp + ").\n");
			}
			else {
				if (udp != NONE) {
					try {
						client.connect(timeout, host, tcp, udp);
						logTime("Client connected to host (" + host.getHostAddress() + ") on ports (TCP: " + tcp + ", UDP: " + udp + ").\n");
						
						client.start();
						setRunning(true);
					}
					catch (IllegalArgumentException e) {
						logTime("Invalid Ports Specified. Out of Range. (TCP: " + tcp + " UDP: " + udp + ").\n");
					}
				}
				else {
					try {
						client.connect(timeout, host, tcp);
						logTime("Client connected to host (" + host.getHostAddress() + ") on port (TCP: " + tcp + ").\n");
						
						client.start();
						setRunning(true);
					}
					catch (IllegalArgumentException e) {
						logTime("Invalid Port Specified. Out of Range. (TCP: " + tcp + ").\n");
					}
				}
			}
		}
		else {
			logTime("Unable to open connection to new host (" + host.getHostAddress() + "), please close current one.\n");
		}
		
		notifyChangeListeners();
	}

	@Override
	public void close() {
		if (isRunning()) {
			logTime("Client connection to host (" + host.getHostAddress() + ") closed.\n");
			
			client.stop();
			setRunning(false);
		}
		else {
			logTime("Unable to close client connection, no connection to close!\n");
		}
		
		notifyChangeListeners();
	}

	@Override
	protected EndPoint getEndPoint() {
		return client;
	}
}
