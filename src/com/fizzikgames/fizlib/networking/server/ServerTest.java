package com.fizzikgames.fizlib.networking.server;

import java.awt.Toolkit;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.fizzikgames.fizlib.networking.PacketRegistry;

public class ServerTest {
	public static void main(String[] args) {		
		final ServerModel model = new ServerModel("FizzikLib Test Server");
		ServerViewer viewer = new ServerViewer(model);
		viewer.setSize(800, 600);
		viewer.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (viewer.getWidth() / 2)), 
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (viewer.getHeight() / 2)));
		
		PacketRegistry.addPacketType(Byte.class);
		PacketRegistry.addPacketType(String.class);
		PacketRegistry.registerPackets(model.getKryoInstance());
		
		model.addNetworkListener(new Listener() {
			public void connected(Connection connection) {
				connection.setName(connection.getID() + "");
				model.logTime("Client " + connection.toString() + " @ " + connection.getRemoteAddressTCP().getHostString() + " connected.\n");
			}
			
			public void disconnected(Connection connection) {
				model.logTime("Client " + connection.toString() + " @ " + connection.getRemoteAddressTCP().getHostString() + " disconnected.\n");
			}
			
			public void idle(Connection connection) {
				
			}
			
			public void received(Connection connection, Object object) {				
				if (object instanceof String) {
					model.logTime("Client " + connection.toString() + ": " + ((String) object) + "\n");
				}
			}
		});	
	}
}
