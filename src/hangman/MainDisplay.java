package hangman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class MainDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDisplay frame = new MainDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		StdDraw.setPenRadius(0.07);
		StdDraw.setPenColor(new Color(234,245,249));
		StdDraw.filledSquare(0.5, 0.5, 2);
		Color yellow = new Color(186,158,6);
		StdDraw.setPenColor(yellow);
		double headXPos = 0.5;
		double headYPos = 0.75;
		StdDraw.filledCircle(headXPos, headYPos, 0.1);
		// body
		StdDraw.line(headXPos, headYPos, headXPos, headYPos - 0.5);
		double bodyYPos = 0.5;
		// right arm
		StdDraw.line(headXPos, bodyYPos, headXPos + 0.2, bodyYPos);
		// left arm
		StdDraw.line(headXPos, bodyYPos, headXPos - 0.2, bodyYPos);
		double crotchYPos = 0.25;
		StdDraw.line(headXPos, crotchYPos, headXPos + 0.2, crotchYPos - 0.2);
		StdDraw.line(headXPos, crotchYPos, headXPos - 0.2, crotchYPos - 0.2);
		StdDraw.show(10);
		
		JButton btnSaveLoad = new JButton("Save / Load");
		btnSaveLoad.setBounds(648, 16, 115, 29);
		contentPane.add(btnSaveLoad);
		
		JLabel lblYourGuess = new JLabel("Your Guess");
		lblYourGuess.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblYourGuess.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourGuess.setBounds(536, 87, 128, 23);
		contentPane.add(lblYourGuess);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 29));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(679, 75, 49, 39);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(433, 206, 319, 187);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblWrongGuesses = new JLabel("WRONG GUESSES");
		lblWrongGuesses.setForeground(Color.RED);
		lblWrongGuesses.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblWrongGuesses.setBounds(461, 170, 284, 20);
		contentPane.add(lblWrongGuesses);
	}
}
