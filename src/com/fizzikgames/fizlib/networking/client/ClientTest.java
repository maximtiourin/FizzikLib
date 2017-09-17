package com.fizzikgames.fizlib.networking.client;

import java.awt.Toolkit;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.fizzikgames.fizlib.networking.PacketRegistry;
import com.fizzikgames.fizlib.networking.Viewer;

public class ClientTest {
	public static void main(String[] args) {
		final ClientModel client = new ClientModel(3000);
		Viewer viewer = new Viewer(client); //Use hack so i can visualize the client, by use a server viewer!
		viewer.setSize(800, 600);
		viewer.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (viewer.getWidth() / 2)), 
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (viewer.getHeight() / 2)));
		
		PacketRegistry.addPacketType(Byte.class);
		PacketRegistry.addPacketType(String.class);
		PacketRegistry.registerPackets(client.getKryoInstance());
		
		client.addNetworkListener(new Listener() {
			public void connected(Connection connection) {
				System.out.println("Connected to Server.");
			}
			
			public void disconnected(Connection connection) {
				System.out.println("Disconnected from Server");
			}
			
			public void idle(Connection connection) {
				System.out.println("Server is idle");
			}
			
			public void received(Connection connection, Object object) {
				System.out.println("Received From Server");
				
				if (object instanceof String) {
					System.out.println("Received String From Server - '" + ((String) object) + "'");
				}
			}
		});
	}
}
