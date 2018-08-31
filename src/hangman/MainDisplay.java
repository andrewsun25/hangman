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
	private boolean[] visibleParts = new boolean[]{false, false, false, false, false, false};
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
		while(!gameManager.isGameOver()) {
			if(frame.hasNewInput) {
				String input = frame.getInput();
				if(input.length() == 1) {
					System.out.println("Secret word: " + gameManager.getGameState().secretWord);
					ArrayList<Integer> matchedIndices = gameManager.guessLetter(input.charAt(0));
					// If there is no match, no guessed incorrectly. 
					if(matchedIndices.size() == 0) {
						System.out.println("Incorrect Letter: " + input);
						frame.addBodyPart();
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
		setBounds(100, 100, 800, 465);
//		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

//		JButton btnSaveLoad = new JButton("Save / Load");
		btnSaveLoad.setBounds(648, 16, 115, 29);
		contentPane.add(btnSaveLoad);

		JLabel lblYourGuess = new JLabel("Your Guess");
		lblYourGuess.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblYourGuess.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourGuess.setBounds(536, 87, 128, 23);
		contentPane.add(lblYourGuess);

		// input field
//		inputField = new JTextField();
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 29));
		inputField.setHorizontalAlignment(SwingConstants.CENTER);
		inputField.setBounds(679, 75, 49, 39);
		contentPane.add(inputField);
		inputField.setColumns(10);

		// input button
		inputButton.setBounds(740, 75, 90, 39);
		inputButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
			    // display/center the jdialog when the button is pressed
				  hasNewInput = true;
			  }
			});
		contentPane.add(inputButton);

		// wrongWordsBank
		wrongWordsBank.setEditable(false);
		wrongWordsBank.setBounds(431, 158, 319, 187);
		contentPane.add(wrongWordsBank);
		wrongWordsBank.setColumns(10);

		JLabel lblWrongGuesses = new JLabel("WRONG GUESSES");
		lblWrongGuesses.setForeground(Color.RED);
		lblWrongGuesses.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblWrongGuesses.setBounds(460, 126, 284, 29);
		contentPane.add(lblWrongGuesses);

		JTextField textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(431, 361, 319, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

//		graphicsPanel = new MyPanel();
		graphicsPanel.setBounds(35, 16, 367, 377);
		contentPane.add(graphicsPanel);

	}
	
	public String getInput() {
		return inputField.getText();
	}
	
	
	
	public void addBodyPart() {
		visibleParts[currentPartIndex++] = true;
	}
	
	class MyPanel extends JPanel {
//		private boolean isHeadVisible = false;
//		private boolean isBodyVisible = false;
//		private boolean isLeftArmVisible = false;
//		private boolean isRightArmVisible = false;
//		private boolean isLeftLegVisible = false;
//		private boolean isRightLegVisible = false;
		
		
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.black);
			if(visibleParts[0]) {
				g.fillOval(150, 10, 50, 50);
			}
			if(visibleParts[1]) {
				g.fillRect(170, 20, 10, 200);
			}
		}

	}

	
}

