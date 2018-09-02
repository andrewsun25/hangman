package hangman;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;
import javax.swing.JEditorPane;

import hangman.GameManager;

public class MainDisplay extends JFrame {

	private JPanel contentPane = new JPanel();
	private JTextField inputField = new JTextField();
	private JButton inputButton = new JButton("Guess");
	private JTextField wrongWordsBank = new JTextField();
//	private JTextField textField_2 = new JTextField();
	private JPanel graphicsPanel = new MyPanel(); // MyPanel Object
	private JButton btnSaveLoad = new JButton("Save / Load");
	public boolean hasNewInput = false;

	private int currentPartIndex = 0;
	// visibleParts represent the 6 body parts which can be visible (in order of
	// head, body, left arm, right arm, left leg, right leg).
	private boolean[] visibleParts = new boolean[] { false, false, false, false, false, false };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainDisplay frame = new MainDisplay();
		;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		GameManager gameManager = new GameManager();
		while (!gameManager.isGameOver()) {
			// Whenever the user presses the input button, frame.hasNewInput becomes true.
			if (frame.hasNewInput) {
				String input = frame.getInput();
				if (input.length() == 1) {
					System.out.println("Secret word: " + gameManager.getGameState().getSecretWord());
					ArrayList<Integer> matchedIndices = gameManager.guessLetter(input.charAt(0));
					// If our guess is incorrect
					if (matchedIndices.size() == 0) {
						System.out.println("Incorrect Letter: " + input);
						frame.addWrongChar(input.charAt(0));
						frame.addBodyPart(); // makes a body part visible
						frame.graphicsPanel.repaint();
					}
				}
				frame.hasNewInput = false;
			}

		}
	}

	/**
	 * Create the frame.
	 */
	public MainDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 465);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnSaveLoad.setBounds(648, 16, 115, 29);
		contentPane.add(btnSaveLoad);

		JLabel lblYourGuess = new JLabel("Your Guess");
		lblYourGuess.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblYourGuess.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourGuess.setBounds(536, 87, 128, 23);
		contentPane.add(lblYourGuess);

		// input field
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 29));
		inputField.setHorizontalAlignment(SwingConstants.CENTER);
		inputField.setBounds(679, 75, 49, 39);
		contentPane.add(inputField);
		inputField.setColumns(10);

		// input button
		inputButton.setBounds(740, 75, 90, 39);
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hasNewInput = true;
			}
		});
		contentPane.add(inputButton);

		// wrongWordsBank
		wrongWordsBank.setEditable(false);
		wrongWordsBank.setBounds(431, 158, 319, 187);
		wrongWordsBank.setForeground(Color.RED);
		wrongWordsBank.setFont(new Font("Tahoma", Font.BOLD, 29));
		contentPane.add(wrongWordsBank);
		wrongWordsBank.setColumns(10);

		JLabel lblWrongGuesses = new JLabel("WRONG GUESSES");
		lblWrongGuesses.setForeground(Color.RED);
		lblWrongGuesses.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblWrongGuesses.setBounds(460, 126, 284, 29);
		contentPane.add(lblWrongGuesses);

		// secret word and info on the guess
		// ex: if choose a previously guessed letter then display "you have already
		// guessed this letter"
		JTextField textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(431, 361, 319, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		graphicsPanel.setBounds(35, 16, 367, 377);
		contentPane.add(graphicsPanel);

	}

	public String getInput() {
		return inputField.getText();
	}

	public void addWrongChar(char c) {
		wrongWordsBank.setText(wrongWordsBank.getText() + c + ", ");
	}

	public void addBodyPart() {
		visibleParts[currentPartIndex++] = true;
	}

	class MyPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			if (visibleParts[0]) {
				g2d.fillOval(150, 10, 50, 50);
			}
			if (visibleParts[1]) {
				g2d.fillRect(170, 20, 10, 200);
			}
			if (visibleParts[2]) {
				g2d.fillRect(70, 100, 100, 10);
			}
			if (visibleParts[3]) {
				g2d.fillRect(170, 100, 100, 10);
			}
			if (visibleParts[4]) {
				g2d.fillRect(70, 220, 100, 10);
			}
			if (visibleParts[5]) {

				g2d.fillRect(170, 220, 100, 10);

				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setFont(new Font("Tahoma", Font.BOLD, 29));
				g2d.setColor(Color.RED);
				g2d.drawString("YOU LOSE", this.getWidth() / 6, this.getHeight() / 6);

				g2d.setColor(Color.BLUE);
				g2d.drawString("YOU LOSE", this.getWidth() / 2, this.getHeight() / 2);

				g2d.setColor(Color.WHITE);
				g2d.drawString("YOU LOSE", this.getWidth() / 4, this.getHeight() / 4);

			}
		}

	}

}
