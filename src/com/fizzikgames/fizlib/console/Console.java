package com.fizzikgames.fizlib.console;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The console contains all of the data needed to apply a user console
 * to any viewer and extended data type model. For example, a server console that supplies
 * console operations to a server model / viewer / controller pairing.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Console {
	private ArrayList<String> entries;
	private CommandParser cmd;
	
	public Console() {
		entries = new ArrayList<String>(100);
		cmd = new CommandParser();
	}
	
	/**
	 * Registers the command to this console's command parser
	 * @param command command to register
	 */
	public void registerCommand(ConsoleCommand command) {
		cmd.registerCommand(command);
	}
	
	/**
	 * Attempts to parse and process the String command.
	 * @param command the command to parse and process.
	 */
	public void enterCommand(String command) {
		log("> " + command + "\n");
		
		String result = cmd.executeCommand(command);
		if (result.length() > 0) {
			logTime(result);
		}
	}
	
	/**
	 * Adds the log entry to the list of entries in this console
	 * @param entry the log entry to add
	 */
	public void log(String entry) {
		entries.add(entry);
	}
	
	/**
	 * Adds the log entry to the list of entries in this console, with a timestamp
	 * @param entry the log entry to add
	 */
	public void logTime(String entry) {
		GregorianCalendar cal = new GregorianCalendar();
		
		String time = "[";
		
		String hour = cal.get(GregorianCalendar.HOUR_OF_DAY) + "";
		String minute = cal.get(GregorianCalendar.MINUTE) + "";
		String second = cal.get(GregorianCalendar.SECOND) + "";
		
		if (hour.length() == 1) hour = "0" + hour;
		if (minute.length() == 1) minute = "0" + minute;
		if (second.length() == 1) second = "0" + second;
		
		time += hour + ":" + minute + ":" + second + "] ";
		
		log(time + entry);
	}
	
	/**
	 * Returns a list of strings of all of this console's stored entry Strings.
	 */
	public List<String> getEntryStringList() {		
		return entries;
	}
	
	/**
	 * Returns a list of registered commands for this console's command parser.
	 * @return List<ConsoleCommand> the list of registered commands.
	 */
	public List<ConsoleCommand> getCommandList() {
		return cmd.getCommandList();
	}
}
