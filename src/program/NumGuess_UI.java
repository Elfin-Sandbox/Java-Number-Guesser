package program;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class NumGuess_UI {

	private JFrame homeWindow;
	private JTextPane answerInput;
	private NumGuess_Logic logic;
	private char flag;
	private boolean isEntered;
	private int turns;

	public NumGuess_UI(NumGuess_Logic logic) {
		this.logic = logic;
	}

	public void createDisplayGUI() {
		setupFrame();
		newFrame();
		setCalcButton();
		addRightPanel();
		addCredits();

		packFrame();
	}

	public void setupFrame() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.err
					.println("UI Warning: UI Theme cannot be loaded. \n" + "Proceeding with the default interface...");
		}
	}

	public void newFrame() {
		homeWindow = new JFrame("Number Guesser v.1");
		homeWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		homeWindow.setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("background.png"))));
	}

	public JButton addCalcButton(String value, int x, int y) {
		JButton button = new JButton(value);
		button.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 32));

		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBounds(x, y, 95, 80);

		if (value.equals("0")) {
			button.setBounds(x, y, 315, 80);
		}


		homeWindow.add(button);

		return button;
	}

	public void setCalcButton() {

		String[] buttonLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		int[][] buttonCoordinates = {
        {25, 300},
        {25, 210}, {135, 210}, {245, 210},
        {25, 120}, {135, 120}, {245, 120},
        {25, 30}, {135, 30}, {245, 30}
		};

		JButton[] calcButton = new JButton[buttonLabels.length];

		for (int i = 0; i < buttonLabels.length; i++) {
			calcButton[i] = addCalcButton(buttonLabels[i], buttonCoordinates[i][0], buttonCoordinates[i][1]);
			calcButton[i].setActionCommand(buttonLabels[i]);
		}
		calcButton[0].setText("");
		calcButton[0].setIcon(new ImageIcon(getClass().getClassLoader().getResource("zero.png")));

		/* NOTE: To be able to run as a .jar even the image is deleted in the resources:
					1.) Right click the project
					2.) Add the resources folder on "Java Build Path -> Source"
					3.) code: new ImageIcon(getClass().getClassLoader().getResource("nameOfImageOnly.png")

					*/

		ActionListener buttonAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JButton b = (JButton) evt.getSource();
				String digit = b.getActionCommand();
				try {
					int caretPosition = answerInput.getCaretPosition();
					answerInput.getDocument().insertString(caretPosition, digit, null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};

		for (int i = 0; i < buttonLabels.length; i++) {
			calcButton[i].addActionListener(buttonAction);
		}

	}

	public void addRightPanel() {

		JTextPane instruxPane = setTextPane("Enter your number here: ", 24);
		instruxPane.setBounds(370, 40, 300, 50);

		answerInput = new JTextPane();
		answerInput.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
		answerInput.setBounds(370, 85, 280, 50);
		answerInput.setMargin(new Insets(4, 5, 0, 0));

		JButton enterButton = new JButton("↲");
		enterButton.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 24));
		enterButton.setForeground(Color.DARK_GRAY);
		enterButton.setBackground(Color.CYAN);
		enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		enterButton.setBounds(670, 85, 70, 50);

		JTextPane judgPane = setTextPane("Result: ", 24);
		judgPane.setBounds(370, 165, 300, 50);

		JTextPane resultOutput = new JTextPane();
		resultOutput.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
		resultOutput.setBounds(370, 210, 370, 50);
		resultOutput.setEditable(false);
		resultOutput.setFocusable(false);
		resultOutput.setBackground(Color.decode("#e1e1e1"));
		centerAligner(resultOutput);

		JButton rerollButton = new JButton("Reroll");
		rerollButton.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 24));
		rerollButton.setForeground(Color.BLUE);
		rerollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rerollButton.setBounds(590, 310, 150, 65);

		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 24));
		quitButton.setForeground(Color.RED);
		quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		quitButton.setBounds(370, 310, 150, 65);

		JTextPane historyPane = setTextPane("HISTORY:", 30);
		historyPane.setBounds(790, 10, 200, 50);

		JTextPane historyOutput = setTextPane("", 21);
		historyOutput.setBounds(790, 65, 150, 250);
		historyOutput.setOpaque(true);

		JScrollPane historyScroll = setScroll(historyOutput);
		historyScroll.setBounds(790, 65, 150, 250);
		centerAligner(historyOutput);

		JTextPane turnsPane = setTextPane("Turns: ", 25);
		turnsPane.setBounds(790, 330, 150, 30);
		centerAligner(turnsPane);


		homeWindow.add(instruxPane);
		homeWindow.add(answerInput);
		homeWindow.add(enterButton);
		homeWindow.add(judgPane);
		homeWindow.add(resultOutput);
		homeWindow.add(quitButton);
		homeWindow.add(rerollButton);

		homeWindow.add(historyPane);
		homeWindow.add(historyScroll);
		homeWindow.add(turnsPane);

		answerInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char digit = e.getKeyChar();

				if (!Character.isDigit(digit) && digit != KeyEvent.VK_BACK_SPACE) {
					e.consume();
					return;
				}

				if (digit == KeyEvent.VK_BACK_SPACE) {
					digit = '0';
				}
			}
		});

		answerInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					enterButton.doClick();

					if (isEntered) {
						answerInput.setText("");
						isEntered = !isEntered;
					}
				}

			}

		});

		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String oldEntries = historyOutput.getText();
				String newEntry = answerInput.getText();

				try {
					flag = logic.numberEqualsTarget(Integer.parseInt(answerInput.getText()));
				}
				catch (Exception e1) {
					System.err.print("Please try again.\n");
				}
				isEntered = !isEntered;
				turns++;

				switch (flag) {
					case '=': 	resultOutput.setText("Number guessed: " + answerInput.getText() + "!");
								resultOutput.setBackground(Color.BLUE);
								resultOutput.setForeground(Color.WHITE);
								resultOutput.setEditable(false);
					break;

					case 'U':	resultOutput.setText("Target number is lower!");
								resultOutput.setBackground(Color.ORANGE);
								resultOutput.setForeground(Color.BLACK);
					break;

					case 'L':	resultOutput.setText("Target number is higher!");
								resultOutput.setBackground(Color.PINK);
								resultOutput.setForeground(Color.BLACK);
					break;
				}

				if (answerInput.getText().equals("143")) {
					resultOutput.setText("I love you, my Sana! ^ ^");
					resultOutput.setBackground(Color.PINK);
					resultOutput.setForeground(Color.decode("#d80000"));
				}

				if (oldEntries.isEmpty()) {
					historyOutput.setText(newEntry);
				}

				else {
					historyOutput.setText(oldEntries + "\n" + newEntry);
				}

				turnsPane.setText("Turns: " + turns);

			}
		});

		rerollButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logic.generateNumber();

				answerInput.setText("");
				resultOutput.setText("");
				resultOutput.setBackground(Color.decode("#e1e1e1"));
				historyOutput.setText("");

				turns = 0;
				turnsPane.setText("Turns: " + turns);

				answerInput.requestFocusInWindow();
			}
		});

		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homeWindow.dispose();
				System.exit(0);

			}
		});
	}

	public void addCredits () {

		JTextPane creditsPane = setTextPane("Developed by: Coronel, ALP.", 1);
		creditsPane.setBounds(25, 394, 350, 100);
		creditsPane.setFont(new Font("Consolas", Font.BOLD, 14));
		creditsPane.setForeground(Color.GRAY);

		homeWindow.add(creditsPane);

		JTextPane versionPane = setTextPane("All rights reserved. 2023.", 1);
		versionPane.setBounds(735, 394, 350, 100);
		versionPane.setFont(new Font("Consolas", Font.BOLD, 14));
		versionPane.setForeground(Color.GRAY);

		homeWindow.add(versionPane);

	}

	public JTextPane setTextPane(String label, int fontSize) {
		JTextPane textPane = new JTextPane();
		textPane.setText(label);
		textPane.setEditable(false);
		textPane.setFocusable(false);
        textPane.setOpaque(false);
		textPane.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, fontSize));

		return textPane;
	}

	public void centerAligner(JTextPane textPane) {
		StyledDocument style = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		StyleConstants.setLineSpacing(center, 0.015f);
		style.setParagraphAttributes(0, style.getLength(), center, false);
	}

	public static JScrollPane setScroll (JTextPane textpane) {

		JScrollPane scrollPane = new JScrollPane(textpane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(true);

		return scrollPane;
	}

	public void packFrame() {
		homeWindow.setLayout(null);
		homeWindow.setResizable(false);
		homeWindow.pack();
		homeWindow.setSize(1000, 475);
		homeWindow.setLocationRelativeTo(null);
		homeWindow.setVisible(true);

		answerInput.requestFocusInWindow();
	}


}
