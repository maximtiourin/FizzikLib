package com.fizzikgames.fizlib.console;

/**
 * A console command has all of the information needed to parse and process a user command for the console.
 * @author Maxim Tiourin
 * @version 1.00
 */
public interface ConsoleCommand {
	/**
	 * Returns the string name reference for the command.
	 * <br>Example: commandName
	 * @return String the name of the command, used for calling itself.
	 */
	public String getCommandName();
	/**
	 * Returns a description of the command.
	 * <br>Example: Does A, B, and C.
	 * @return String description of the command.
	 */
	public String getCommandDescription();
	/**
	 * Returns information about how to use the command.
	 * <br>Example: commandName \<Argument1\> [\<Argument2\> ... \<ArgumentN\>]
	 * @return String information about how to use the command.
	 */
	public String getCommandUseInfo();
	/**
	 * Returns the maximum amount of arguments this command takes.
	 * @return int maximum amount of arguments to pass to this command
	 */
	public int getMaximumArguments();
	/**
	 * Returns the minimum amount of arguments this command takes.
	 * @return int minimum amount of arguments to pass to this command
	 */
	public int getMinimumArguments();
	/**
	 * Attempts to execute the command with the supplied args.
	 * The resulting log string is returned.
	 * @param args The list of arguments
	 * @return String the resulting log string of the execution.
	 */
	public String executeCommand(String[] args);
}
