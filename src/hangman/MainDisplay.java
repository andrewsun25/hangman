package hangman;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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

public class MainDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPanel panel;

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
		textField_1.setBounds(431, 158, 319, 187);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblWrongGuesses = new JLabel("WRONG GUESSES");
		lblWrongGuesses.setForeground(Color.RED);
		lblWrongGuesses.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblWrongGuesses.setBounds(460, 126, 284, 29);
		contentPane.add(lblWrongGuesses);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(431, 361, 319, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
	
		panel = new MyPanel();
		panel.setBounds(35, 16, 367, 377);
		contentPane.add(panel);
		
		
	}
	
}

		class MyPanel extends JPanel {
			public void paint (Graphics g) {
				g.setColor(Color.black);
				g.fillOval(150, 10, 50, 50);
				g.fillRect(170, 20, 10, 200);
			}
			
			
		}

