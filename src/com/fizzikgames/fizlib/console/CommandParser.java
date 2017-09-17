package com.fizzikgames.fizlib.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fizzikgames.fizlib.util.StringUtil;

/**
 * The command parser stores, parses, and processes console commands.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class CommandParser {
	private HashMap<String, ConsoleCommand> commands;
	
	public CommandParser() {		
		commands = new HashMap<String, ConsoleCommand>();
	}
	
	/**
	 * Parses the String command and attempts to execute it if
	 * such a command exists with the proper arguments in the parser's
	 * hashmap.
	 * @param line command the string command to attempt to execute.
	 * @return String the log string resulting from the execution of the command
	 */
	public String executeCommand(String line) {
		String result = "";
		
		String commandName = parseCommandName(line);
		
		//Check to make sure we got a valid command
		if (commandName != null) {
			ConsoleCommand command = commands.get(commandName);
			
			//Get arguments if need, and check to make sure they are valid
			if (command.getMinimumArguments() > 0) {
				String[] args = parseArguments(line);
				
				if ((args.length >= command.getMinimumArguments()) && (args.length <= command.getMaximumArguments())) {
					//Execute command with arguments, without checking their validity
					command.executeCommand(args);
				}
				else {
					//Incorrect amount of arguments supplied, log error message
					String expectedCount;
					if (command.getMinimumArguments() == command.getMaximumArguments()) {
						expectedCount = "" + command.getMinimumArguments();
					}
					else {
						expectedCount = command.getMinimumArguments() + "-" + command.getMaximumArguments();
					}
					
					result = commandName + ": Incorrect amount of arguments. Supplied: " + args.length + ". Expected: " + expectedCount + ".\nUsage: " + command.getCommandUseInfo() + "\n";
				}
			}
			else {
				command.executeCommand(null);
			}
		}
		
		return result;
	}
	
	/**
	 * Registers the command, putting it into the hash map.
	 * Only one command can exist with the same name.
	 * @param command the console command the register.
	 */
	public void registerCommand(ConsoleCommand command) {
		commands.put(command.getCommandName(), command);
	}
	
	/**
	 * Returns a list of registered commands for this command parser.
	 * @return List<ConsoleCommand> the list of registered commands.
	 */
	public List<ConsoleCommand> getCommandList() {		
		ArrayList<ConsoleCommand> list = new ArrayList<ConsoleCommand>();
		
		for (String s : commands.keySet()) {
			list.add(commands.get(s));
		}
		
		return list;
	}
	
	/**
	 * Attempts to parse the command name from the String, if there is one.
	 */
	private String parseCommandName(String line) {
		if (line.length() <= 0) return null;
		
		final String splitRegex = " ";
		
		String[] tokens = line.split(splitRegex);
		
		//Check to make sure tokens is not empty
		if (tokens.length > 0) {
			//Check to make sure the first letter of the first token appears at index 0 in the line
			if (line.indexOf(tokens[0].charAt(0)) == 0) {
				//Attempt to find the command in the hash map
				ConsoleCommand command = commands.get(tokens[0]);
				
				if (command != null) {
					//Command exists, return the command name
					return command.getCommandName();
				}
			}
		}
		
		return null;
	}
	
	private String[] parseArguments(String line) {
		if (line.length() <= 0) return null;
		
		final char split = ' ';
		final char ignore = '"';
		
		String[] tokens = StringUtil.tokenize(line, split, ignore); //Split into tokens, ignoring any regex1 within regex2.
		
		//Check to make sure tokens is not empty
		if (tokens.length > 0) {			
			//Check to make sure the first letter of the first token appears at index 0 in the line
			String[] args = new String[tokens.length - 1];
			
			for (int i = 0; i < args.length; i++) {
				args[i] = tokens[i + 1];
			}
			
			return args;
		}
		
		return null;
	}
}
