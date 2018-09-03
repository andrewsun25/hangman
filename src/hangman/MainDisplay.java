package hangman;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.SwingUtilities;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import hangman.GameManager;
import hangman.GameState;

public class MainDisplay extends JFrame {

	private JPanel contentPane = new JPanel();
	private JTextField inputField = new JTextField();
	private JButton inputButton = new JButton("Guess");
	private JTextField wrongWordsBank = new JTextField();
	private JTextField wordStateBox = new JTextField();
//	private JTextField textField_2 = new JTextField();
	private JPanel graphicsPanel = new MyPanel(); // MyPanel Object
	private JButton btnSave = new JButton("Save");
	private JButton btnLoad = new JButton("Load");
	public String frameInput = "";
	public boolean hasNewInput = false;
	public boolean loadGame = false;
	public boolean saveGame = false;
	public boolean didWin = false;
	private int currentPartIndex = 0;
	private int numFreeLetters = 1;
	// visibleParts represent the 6 body parts which can be visible (in order of
	// head, body, left arm, right arm, left leg, right leg).
	private boolean[] visibleParts = new boolean[] { false, false, false, false, false, false };
	
	private static GameManager gameManager = new GameManager();

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

		frame.updateWordState(gameManager.getGameState().getWordState());
		while (!gameManager.isGameOver()) 
		{
			if (frame.loadGame)
			{
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hangman Game File", "hangman");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) 
		        {
		        	gameManager.loadGame(chooser.getSelectedFile().getAbsolutePath());
		        	String secretWord = gameManager.getGameState().getSecretWord();
		        	
		        	// Update wrong guesses box and body parts
		        	Iterator<Character> it = gameManager.getGameState().getGuessedLetters().iterator();
		            while(it.hasNext())
		            {
		               char guess = it.next();
		               if (secretWord.indexOf(guess) < 0)
		               {
		            	   frame.addWrongChar(guess);
		            	   frame.addBodyPart(); // makes a body part visible
		               }
		            }
		        	
		        	// Update word state box
		        	frame.updateWordState(gameManager.getGameState().getWordState());
		        }
		        
		        frame.loadGame = false;
		        // Repaint the GUI for any updates
				frame.graphicsPanel.repaint();
			}
			if (frame.saveGame)
			{
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hangman Game File", "hangman");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showSaveDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) 
		        {
		        	String filePath = chooser.getSelectedFile().getAbsolutePath();
		        	gameManager.saveAs(filePath);
		        }
		        frame.saveGame = false;
			}
			// Whenever the user presses the input button, frame.hasNewInput becomes true.
			if (frame.hasNewInput) 
			{
				String input = frame.getInput();
				if (input.length() == 1) 
				{
					System.out.println("Secret word: " + gameManager.getGameState().getSecretWord());
					GameManager.Result result = gameManager.guessLetter(input.charAt(0));
					if (gameManager.didWin()) {
						frame.didWin = true;
					}
					// If our guess is incorrect

					if (result == GameManager.Result.WRONG) 
					{
						System.out.println("Incorrect Letter: " + input);
						frame.addWrongChar(input.charAt(0));
						frame.addBodyPart(); // makes a body part visible
					}
					else if (result == GameManager.Result.CORRECT)
					{
						frame.updateWordState(gameManager.getGameState().getWordState());
					}
					else if (result == GameManager.Result.DUPLICATE)
					{
						// TODO: Something?
					}
					
					// Repaint the GUI for any updates
					frame.graphicsPanel.repaint();
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

		btnLoad.setBounds(530, 16, 115, 29);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadGame = true;
			}
		});
		contentPane.add(btnLoad);
		
		btnSave.setBounds(648, 16, 115, 29);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGame = true;
			}
		});
		contentPane.add(btnSave);

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
				frameInput = inputField.getText();
				inputField.setText("");
				hasNewInput = true; // Because of the while loop in main this must be set AFTER we set the frameInput variable
				
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
		// ex: if choose a previously guessed letter then display "you have already guessed this letter"
		// guessed this letter"
		wordStateBox.setEditable(false);
		wordStateBox.setFont(new Font("Tahoma", Font.BOLD, 29));
		wordStateBox.setBounds(431, 361, 600, 50);
		contentPane.add(wordStateBox);
		wordStateBox.setColumns(10);

		graphicsPanel.setBounds(35, 16, 367, 377);
		contentPane.add(graphicsPanel); 
		
		JButton btnNewButton = new JButton("");
		try {
			Image img = ImageIO.read(getClass().getResource("joker_pic.png"));
			btnNewButton.setIcon(new ImageIcon(img));
		} catch (Exception p) {
			System.out.println(p);
		}
		
		btnNewButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				if (numFreeLetters > 0) {
				numFreeLetters--;
			  String SecretWord	= gameManager.getGameState().getSecretWord();
			  Random random = new Random();
			  int secretValue = random.nextInt(SecretWord.length()-1);
			  gameManager.guessLetter(SecretWord.charAt(secretValue));
			  updateWordState(gameManager.getGameState().getWordState());
				}
			}
		});
		btnNewButton.setBounds(750, 186, 128, 131);
		contentPane.add(btnNewButton);

	}

	public String getInput() {
		return frameInput;
	}

	public void addWrongChar(char c) {
		
		wrongWordsBank.setText(wrongWordsBank.getText() + c + ", ");
	}

	public void addBodyPart() {
		visibleParts[currentPartIndex++] = true;
	}
	
	public void updateWordState(char[] wordState)
	{
		wordStateBox.setText("");
		for (int i=0; i<wordState.length; i++)
		{
			if (wordState[i] == GameState.NULL_CHAR) // if blank
			{
				wordStateBox.setText(wordStateBox.getText() + "_" + " ");
			}
			else // there is a letter
			{
				wordStateBox.setText(wordStateBox.getText() + wordState[i] + " ");
			}
		}
	}

	class MyPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			if(didWin) {
				g2d.fillRect(170, 220, 100, 10);

				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setFont(new Font("Tahoma", Font.BOLD, 29));
				g2d.setColor(Color.RED);
				g2d.drawString("YOU WIN", this.getWidth() / 6, this.getHeight() / 6);

				g2d.setColor(Color.BLUE);
				g2d.drawString("YOU WIN", this.getWidth() / 2, this.getHeight() / 2);

				g2d.setColor(Color.WHITE);
				g2d.drawString("YOU WIN", this.getWidth() / 4, this.getHeight() / 4);
				return;
			}
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

