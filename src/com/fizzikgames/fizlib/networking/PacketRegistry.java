package com.fizzikgames.fizlib.networking;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;

/**
 * Registers it's current list of packet types with a supplied kryo instance.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class PacketRegistry {
	private static ArrayList<Class<?>> packetTypes = new ArrayList<Class<?>>();
	
	public PacketRegistry() {
		
	}
	
	/**
	 * Adds the packet type to the current packetTypes in the registry
	 */
	public static void addPacketType(Class<?> packetType) {
		packetTypes.add(packetType);
	}
	
	/**
	 * Removes this packet type from the current packetTypes in the registry.
	 * @param packetType the packetType to remove
	 */
	public static void removePacketType(Class<?> packetType) {
		packetTypes.remove(packetType);
	}
	
	/**
	 * Registers all of this registry's packets to the kryo instance.
	 * @param kryo the Kryo instance to register the packets to
	 */
	public static void registerPackets(Kryo kryo) {
		for (Class<?> c : packetTypes) {
			kryo.register(c);
		}
	}
}
