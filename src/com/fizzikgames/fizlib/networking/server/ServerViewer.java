package com.fizzikgames.fizlib.networking.server;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

import com.fizzikgames.fizlib.networking.Viewer;
import com.fizzikgames.fizlib.util.StringUtil;

import javax.swing.ImageIcon;
/**
 * Extends the simple viewer window to visualize a server model.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class ServerViewer extends Viewer implements ChangeListener {
	private static final long serialVersionUID = 7458095648712731531L;
	protected JMenu menuOpenConnection;
	protected JMenuItem menuItemCloseConnection;
	
	public ServerViewer(ServerModel m) {
		super(m);		
		
		JMenu mnConnection = new JMenu("Connection");
		mnConnection.setIcon(new ImageIcon(ServerViewer.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		menuBar.add(mnConnection);
		
		menuOpenConnection = new JMenu("Open Connection");
		menuOpenConnection.setIcon(new ImageIcon(ServerViewer.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")));
		mnConnection.add(menuOpenConnection);
		
		JMenuItem mntmTcpudp = new JMenuItem("TCP/UDP");
		mntmTcpudp.setBorder(new EmptyBorder(5, 5, 5, 5));
		mntmTcpudp.setIcon(new ImageIcon(ServerViewer.class.getResource("/javax/swing/plaf/metal/icons/ocean/iconify-pressed.gif")));
		mntmTcpudp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPortDialog(true);
			}
		});
		menuOpenConnection.add(mntmTcpudp);
		
		JMenuItem mntmTcpOnly = new JMenuItem("TCP Only");
		mntmTcpOnly.setBorder(new EmptyBorder(5, 5, 5, 5));
		mntmTcpOnly.setIcon(new ImageIcon(ServerViewer.class.getResource("/javax/swing/plaf/metal/icons/ocean/iconify.gif")));
		mntmTcpOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPortDialog(false);
			}
		});
		menuOpenConnection.add(mntmTcpOnly);
		
		menuItemCloseConnection = new JMenuItem("Close Connection");
		menuItemCloseConnection.setIcon(new ImageIcon(ServerViewer.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		menuItemCloseConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.close();
			}
		});
		mnConnection.add(menuItemCloseConnection);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		super.stateChanged(e);
		
		this.setTitle(((ServerModel)model).getTitle()); //Set title of the frame
		
		//Set Menu States
		if (model.isRunning()) {
			menuOpenConnection.setEnabled(false);
			menuItemCloseConnection.setEnabled(true);
		}
		else {
			menuOpenConnection.setEnabled(true);
			menuItemCloseConnection.setEnabled(false);
		}
	}
	
	/**
	 * Shows the dialog for getting the port information, if askudp is true will ask for udp port as well.
	 */
	private void showPortDialog(boolean askudp) {
		boolean open = false;
		int tcp = -1, udp = -1;
		
		//TCP
		String input = (String) JOptionPane.showInputDialog(this, "TCP Port:", "Connection Port Information", JOptionPane.QUESTION_MESSAGE);
		
		if (input != null) {
			if (StringUtil.isNumeric(input, false)) {
				try {
					tcp = Integer.valueOf(input);
					open = true;
				}
				catch (NumberFormatException e) {
					model.logTime("Invalid TCP Port entered (TCP: " + input + ").\n");
				}
				
				//UDP
				if ((askudp) && (tcp != -1)) {
					input = (String) JOptionPane.showInputDialog(this, "UDP Port:", "Connection Port Information", JOptionPane.QUESTION_MESSAGE);
					
					if (input != null) {
						if (StringUtil.isNumeric(input, false)) {
							try {
								udp = Integer.valueOf(input);
							}
							catch (NumberFormatException e) {
								model.logTime("Invalid UDP Port entered (UDP: " + input + ").\n");
							}
						}
						else {
							model.logTime("Invalid UDP Port entered (UDP: " + input + ").\n");
							open = false;
						}
					}
				}		
			}
			else {
				model.logTime("Invalid TCP Port entered (TCP: " + input + ").\n");
			}
		}
		
		//Attempt to open
		try {
			if (open) {
				if (askudp) {
					((ServerModel)model).open(tcp, udp);
				}
				else {
					((ServerModel)model).open(tcp);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			model.logTime("Unable to open connection. IOException(ServerViewer:showPortDialog::ServerModel:open).\n");
		}
	}
}
