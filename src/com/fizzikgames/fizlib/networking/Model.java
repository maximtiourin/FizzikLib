package com.fizzikgames.fizlib.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.fizzikgames.fizlib.console.Console;
import com.fizzikgames.fizlib.console.ConsoleCommand;

/**
 * A Model contains some basic necessities for running either a fizzik client or server model.
 * @author Maxim Tiourin
 * @version 1.00
 */
public abstract class Model {
	/**
	 * Represents nothing where needed, such as a port initial value.
	 */
	protected static final int NONE = -1;
	protected int tcp;
	protected int udp;
	protected boolean isRunning;
	protected Console console;
	protected ArrayList<ChangeListener> changeListeners;
	
	public Model() {
		tcp = NONE;
		udp = NONE;
		setRunning(false);
		
		console = new Console();
		
		changeListeners = new ArrayList<ChangeListener>();
		
		registerDefaultCommands();
	}
	
	/**
	 * Registers all of the default commands for the console in this model.
	 * <br>Called automatically in the Model constructor.
	 */
	protected abstract void registerDefaultCommands();
	
	/**
	 * Opens the connection for the model
	 * @throws IOException 
	 */
	protected abstract void openConnection() throws IOException;
	
	/**
	 * Closes the model connection
	 */
	public abstract void close();

	/**
	 * Returns the endpoint for this model.
	 * @return EndPoint the endpoint for the model.
	 */
	protected abstract EndPoint getEndPoint();
	
	/**
	 * Calls the console command parser.
	 * @param command the command to parse and process.
	 */
	public void enterCommand(String command) {
		console.enterCommand(command);
		
		notifyChangeListeners();
	}
	
	/**
	 * Registers the given command with the console.
	 * @param command the command to register
	 */
	public void registerCommand(ConsoleCommand command) {
		console.registerCommand(command);
	}	
	
	/**
	 * Logs the entry into this model's console log to be displayed
	 * in the console viewport.
	 * @param entry the entry to the log
	 */
	public void log(String entry) {
		console.log(entry);
		
		notifyChangeListeners();
	}
	
	/**
	 * Logs the entry into this model's console log to be displayed
	 * in the console viewport, while adding a special timestamp
	 * @param entry the entry to the log
	 */
	public void logTime(String entry) {
		console.logTime(entry);
		
		notifyChangeListeners();
	}
	
	/**
	 * Returns the model's console log as a list of String entries.
	 * @return List<String> list of String entries logged by the model.
	 */
	public List<String> getLog() {
		return console.getEntryStringList();
	}
	
	/**
	 * Returns a list of registered commands for this model's console's command parser.
	 * @return List<ConsoleCommand> the list of registered commands.
	 */
	public List<ConsoleCommand> getCommandList() {
		return console.getCommandList();
	}
	
	/**
	 * Adds the change listener to the model's listeners.
	 * @param l change listener to add
	 */
	public void addChangeListener(ChangeListener l) {
		changeListeners.add(l);
	}
	
	/**
	 * Get a list of this model's change listeners
	 * @return List<ChangeListener> list of model change listeners
	 */
	protected List<ChangeListener> getChangeListeners() {
		return changeListeners;
	}
	
	/**
	 * Notifies the model's change listeners that data change has occured.
	 */
	protected void notifyChangeListeners() {
		for (ChangeListener l : changeListeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Removes the change listener from the model's listeners.
	 * @param l change listener to remove
	 */
	public void removeChangeListener(ChangeListener l) {
		changeListeners.remove(l);
	}
	
	/**
	 * Adds the listener to the model's endpoint.
	 * @param l the listener to add.
	 */
	public void addNetworkListener(Listener l) {
		getEndPoint().addListener(l);
		
		String name = l.getClass().getSimpleName();
		if (name.length() <= 0) name = "Anonymous";
		
		logTime("{ " + name + " } Network Listener added.\n");
	}
	
	/**
	 * Removes the listener from the model's endpoint.
	 * @param l the listener to remove.
	 */
	public void removeNetworkListener(Listener l) {
		getEndPoint().removeListener(l);
		
		logTime(l.getClass().getSimpleName() + "{} Network Listener removed.\n");
	}
	
	/**
	 * Returns whether or not the model is currently running.
	 * @return boolean whether or not the model is running
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * Sets whether or not the mode is currently running, does
	 * not actually perform any open/close connection operations.
	 * @param b whether or not the model should be currently running
	 */
	protected void setRunning(boolean b) {
		isRunning = b;
	}
	
	/**
	 * Returns the kryo instance this model is using,
	 * primarily to aid with the registration of packets.
	 * @return Kryo the kryo instance being used by the endpoint within this model.
	 */
	public Kryo getKryoInstance() {
		return getEndPoint().getKryo();
	}
}
