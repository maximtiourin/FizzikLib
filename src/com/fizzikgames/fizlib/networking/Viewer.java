package com.fizzikgames.fizlib.networking;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.fizzikgames.fizlib.console.ConsoleCommand;

/**
 * Supplies a simple viewer window with a basic gui to help visualize a model.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Viewer extends JFrame implements ChangeListener {
	private static final long serialVersionUID = 8051629259270800359L;
	protected JFrame frame;
	protected Model model;
	protected JTextArea consoleTextArea;
	protected CommandSensitiveJScrollPane consoleScrollPane;
	protected JTextField consoleCommandLine;
	protected JButton consoleCommandSubmit;
	protected JMenuBar menuBar;
	
	public Viewer(Model m) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Viewer.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		model = m;
		model.addChangeListener(this);
		frame = this;
		
		setMinimumSize(new Dimension(600, 400));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{217, 217, 0};
		gridBagLayout.rowHeights = new int[]{262, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel console = new JPanel();
		console.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_console = new GridBagConstraints();
		gbc_console.fill = GridBagConstraints.BOTH;
		gbc_console.insets = new Insets(0, 0, 0, 5);
		gbc_console.gridx = 0;
		gbc_console.gridy = 0;
		getContentPane().add(console, gbc_console);
		GridBagLayout gbl_console = new GridBagLayout();
		gbl_console.columnWidths = new int[]{106, 0};
		gbl_console.rowHeights = new int[]{262, 0, 0};
		gbl_console.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_console.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		console.setLayout(gbl_console);
		
		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		consoleTextArea.setForeground(new Color(220, 220, 220));
		consoleTextArea.setBackground(Color.BLACK);
		consoleTextArea.setWrapStyleWord(true);
		consoleTextArea.setLineWrap(true);
		
		consoleScrollPane = new CommandSensitiveJScrollPane(consoleTextArea);
		consoleScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				consoleScrollPane.repaint();
			}			
		});
		consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_consoleScrollPane = new GridBagConstraints();
		gbc_consoleScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_consoleScrollPane.fill = GridBagConstraints.BOTH;
		gbc_consoleScrollPane.gridx = 0;
		gbc_consoleScrollPane.gridy = 0;
		console.add(consoleScrollPane, gbc_consoleScrollPane);
		
		JPanel consoleCommandLinePanel = new JPanel();
		GridBagConstraints gbc_consoleCommandLinePanel = new GridBagConstraints();
		gbc_consoleCommandLinePanel.anchor = GridBagConstraints.SOUTH;
		gbc_consoleCommandLinePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_consoleCommandLinePanel.gridx = 0;
		gbc_consoleCommandLinePanel.gridy = 1;
		console.add(consoleCommandLinePanel, gbc_consoleCommandLinePanel);
		GridBagLayout gbl_consoleCommandLinePanel = new GridBagLayout();
		gbl_consoleCommandLinePanel.columnWidths = new int[]{0, 0, 0};
		gbl_consoleCommandLinePanel.rowHeights = new int[]{0, 0};
		gbl_consoleCommandLinePanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_consoleCommandLinePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		consoleCommandLinePanel.setLayout(gbl_consoleCommandLinePanel);
		
		consoleCommandLine = new JTextField();
		consoleCommandLine.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				frame.repaint();
			}
		});
		consoleCommandLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterCommandLine();
			}
		});
		GridBagConstraints gbc_consoleCommandLine = new GridBagConstraints();
		gbc_consoleCommandLine.fill = GridBagConstraints.HORIZONTAL;
		gbc_consoleCommandLine.insets = new Insets(0, 0, 0, 5);
		gbc_consoleCommandLine.gridx = 0;
		gbc_consoleCommandLine.gridy = 0;
		consoleCommandLinePanel.add(consoleCommandLine, gbc_consoleCommandLine);
		consoleCommandLine.setColumns(10);
		
		consoleCommandSubmit = new JButton("Submit");
		consoleCommandSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterCommandLine();
			}
		});
		consoleCommandSubmit.setMaximumSize(new Dimension(80, 20));
		consoleCommandSubmit.setMinimumSize(new Dimension(80, 20));
		consoleCommandSubmit.setPreferredSize(new Dimension(80, 20));
		GridBagConstraints gbc_consoleCommandSubmit = new GridBagConstraints();
		gbc_consoleCommandSubmit.fill = GridBagConstraints.HORIZONTAL;
		gbc_consoleCommandSubmit.gridx = 1;
		gbc_consoleCommandSubmit.gridy = 0;
		consoleCommandLinePanel.add(consoleCommandSubmit, gbc_consoleCommandSubmit);
		
		JPanel display = new JPanel();
		display.setSize(new Dimension(200, 0));
		display.setPreferredSize(new Dimension(200, 10));
		display.setMinimumSize(new Dimension(200, 10));
		display.setMaximumSize(new Dimension(200, 32767));
		display.setBorder(new EmptyBorder(5, 0, 5, 5));
		GridBagConstraints gbc_display = new GridBagConstraints();
		gbc_display.fill = GridBagConstraints.BOTH;
		gbc_display.gridx = 1;
		gbc_display.gridy = 0;
		getContentPane().add(display, gbc_display);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/* Closing Operations */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.close();
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		consoleCommandLine.requestFocus();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {		
		//Set Console Text Area content
		String content = "";
		for (String s : model.getLog()) {
			content += s;
		}
		consoleTextArea.setText(content);
	}
	
	/**
	 * Attempts to enter the text in the command line as a command.
	 */
	private void enterCommandLine() {
		model.enterCommand(consoleCommandLine.getText());
		consoleCommandLine.setText("");
	}
	
	/**
	 * Extension of the JTextArea that also draws a transparent overlay displaying
	 * relevant commands while the user is entering text into the console command line.
	 * @author Maxim Tiourin
	 * @version 1.00
	 */
	@SuppressWarnings("serial")
	private class CommandSensitiveJScrollPane extends JScrollPane {
		private final Color OVERLAY_BG_COLOR = new Color(50, 50, 50, 200);
		private final Color OVERLAY_TEXT_COLOR = new Color(255, 255, 255, 255);
		
		public CommandSensitiveJScrollPane(Component c) {
			super(c);
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			Graphics2D g2 = (Graphics2D) g;
			
			//Draw overlay
			if (consoleCommandLine.isFocusOwner() && consoleCommandLine.getText().length() > 0) {
				Color oldColor = g2.getColor();
				Font oldFont = g2.getFont();
				
				ArrayList<ConsoleCommand> cmdlist = (ArrayList<ConsoleCommand>) model.getCommandList();
				ArrayList<ConsoleCommand> filter = new ArrayList<ConsoleCommand>();
				
				//Find matching commands
				String[] tokens = consoleCommandLine.getText().split(" ");	
				
				if (tokens != null && tokens.length > 0) {
					String compare = tokens[0];
					
					for (ConsoleCommand c : cmdlist) {
						String name = c.getCommandName();
						
						if (name.indexOf(compare) == 0) {
							filter.add(c);
				
						}
					}
				}
				
				Font cmdFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
				FontMetrics metrics = frame.getFontMetrics(cmdFont);
				
				int fontHeight = metrics.getHeight();
				
				int overlayHeight = Math.min(getHeight(), fontHeight * filter.size());
				int amountToShow = overlayHeight / (fontHeight);
				
				g2.setColor(OVERLAY_BG_COLOR);
				g2.fillRect(0, getHeight() - overlayHeight, getWidth() - this.verticalScrollBar.getWidth(), overlayHeight);
				
				//Draw command strings
				g2.setColor(OVERLAY_TEXT_COLOR);
				g2.setFont(cmdFont);
				int hoffset1 = 10; //Offset command name
				int hoffset2 = 15; //Offset command usage
				int hoffset3 = 15; //Offset command desc
				int x1 = 0, x2 = 0; //Maximum offsets to display strings nicely
				
				//Determine maximum offsets
				for (int i = 0; i < amountToShow; i++) {			
					int nameWidth = (int) metrics.getStringBounds(filter.get(i).getCommandName(), g2).getWidth();
					x1 = Math.max(nameWidth, x1);
					int usageWidth = (int) metrics.getStringBounds(filter.get(i).getCommandUseInfo(), g2).getWidth();
					x2 = Math.max(usageWidth, x2);
				}
				
				//Draw strings with maximum offsets
				for (int i = 0; i < amountToShow; i++) {										
					g2.drawString(filter.get(i).getCommandName(), hoffset1, (getHeight() - overlayHeight) + (i * fontHeight) + (fontHeight - (fontHeight / 4)));
					g2.drawString(filter.get(i).getCommandUseInfo(), hoffset1 + x1 + hoffset2, (getHeight() - overlayHeight) + (i * fontHeight) + (fontHeight - (fontHeight / 4)));
					g2.drawString(filter.get(i).getCommandDescription(), hoffset1 + x1 + hoffset2 + x2 + hoffset3, (getHeight() - overlayHeight) + (i * fontHeight) + (fontHeight - (fontHeight / 4)));
				}
				
				g2.setFont(oldFont);
				g2.setColor(oldColor);
			}
		}
	}
}

