package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class GameManagerTest {

	@Test
	void parseIncludedWordListFileTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Test that there are 43 words in the word list
		assertEquals(50, gm.wordList.size());
	}
	
	@Test
	void parseValidWordListFileTest()
	{	 
		try
		{
			// Create a temp file
			File file = File.createTempFile("word_list", ".txt");
			
			// Write 5 words to the file, one line per word
			FileWriter writer = new FileWriter(file);
			writer.write("word1\n");
			writer.write("wordwordword2\n");
			writer.write("thisIsATest\n");
			writer.write("cOmPuTeR\n");
			writer.write("veryveryveryveryLooooong\n");
			writer.close();
			
			// Parse the words
			GameManager gm = new GameManager();
			gm.parseWordList(file.getAbsolutePath());
			
			// Assert that the size is correct and all expected words are in the list
			assertEquals(5, gm.wordList.size());
			assertEquals("word1", gm.wordList.get(0));
			assertEquals("wordwordword2", gm.wordList.get(1));
			assertEquals("thisIsATest", gm.wordList.get(2));
			assertEquals("cOmPuTeR", gm.wordList.get(3));
			assertEquals("veryveryveryveryLooooong", gm.wordList.get(4));
			
			// Clean up
			file.delete();
		}
		catch (Exception e)
		{
			fail("Unable to write to a temp file for valid word list parsing test.");
		}
	}
	
	/**
	 * Helper function to determine if the game manager is using the default word list or not.
	 * @param gm The GameManager object to test.
	 * @return True if the default word list is being used, false otherwise.
	 */
	boolean isDefaultWordList(GameManager gm)
	{
		boolean isDefault = true;
		if (gm.wordList.size() != 5)
		{
			isDefault = false;
		}
		if ("engineering".equalsIgnoreCase(gm.wordList.get(0)) != true)
		{
			isDefault = false;
		}
		if ("computer".equalsIgnoreCase(gm.wordList.get(1)) != true)
		{
			isDefault = false;
		}
		if ("science".equalsIgnoreCase(gm.wordList.get(2)) != true)
		{
			isDefault = false;
		}
		if ("programming".equalsIgnoreCase(gm.wordList.get(3)) != true)
		{
			isDefault = false;
		}
		if ("bugs".equalsIgnoreCase(gm.wordList.get(4)) != true)
		{
			isDefault = false;
		}
		return isDefault;
	}
	
	@Test
	void parseEmptyWordListTest()
	{
		try
		{
			// Create an empty temp file
			File file = File.createTempFile("word_list", ".txt");
			
			// Parse the words
			GameManager gm = new GameManager();
			gm.parseWordList(file.getAbsolutePath());
			
			// Assert that the default word list was used instead
			assertTrue(isDefaultWordList(gm));
			
			// Clean up
			file.delete();
		}
		catch (Exception e)
		{
			fail("Unable to write to a temp file for valid word list parsing test.");
		}
	}
	
	@Test
	void parseInvalidWordListTest()
	{
		// Parse the words from a file that doesn't exit
		GameManager gm = new GameManager();
		gm.parseWordList("C:\\fake\\path\\word.txt");
						
		// Assert that the default word list was used instead
		assertTrue(isDefaultWordList(gm));
	}
	
	@Test
	void generateDefaultWordListTest()
	{
		GameManager gm = new GameManager();
		gm.generateDefaultWordList();
		assertTrue(isDefaultWordList(gm));		
	}
	
	@Test
	void guessWrongLetterTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Manually set the secret word
		gm.gameState.setSecretWord("engineering");
		
		// Guess an incorrect letter
		ArrayList<Integer> temp = gm.guessLetter('x');
		
		// Test that the ArrayLIst returned was empty
		assertEquals(0, temp.size());
		
		// Test that the incorrect number of wrong guesses was incremented
		assertEquals(1, gm.gameState.getNumWrongGuesses());
		
		// Test that the number of correct guesses is still 0
		assertEquals(0, gm.gameState.getNumCorrectGuesses());
		
		// Test that the guessed letters list contains 'x'
		assertTrue(gm.gameState.getGuessedLetters().contains('x'));
	}
	
	@Test
	void guessCorrectLetterTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Manually set the secret word
		gm.gameState.setSecretWord("engineering");
		
		// Guess an correct letter
		ArrayList<Integer> temp = gm.guessLetter('e');
		
		// Test that the ArrayList returned 3 hits
		assertEquals(3, temp.size());
		
		// Test that the ArrayList contains indexes 0, 5, and 6
		assertTrue(temp.contains(0));
		assertTrue(temp.contains(5));
		assertTrue(temp.contains(6));
		
		// Test that the incorrect number of wrong guesses is still 0
		assertEquals(0, gm.gameState.getNumWrongGuesses());
		
		// Test that the number of correct guesses was incremented
		assertEquals(1, gm.gameState.getNumCorrectGuesses());
		
		// Test that the guessed letters list contains 'e'
		assertTrue(gm.gameState.getGuessedLetters().contains('e'));
		
		// Test that the word state is e _ _ _ _ e e _ _ _ _
		char[] wordState = gm.gameState.getWordState();
		assertEquals('e', wordState[0]);
		assertEquals(GameState.NULL_CHAR, wordState[1]);
		assertEquals(GameState.NULL_CHAR, wordState[2]);
		assertEquals(GameState.NULL_CHAR, wordState[3]);
		assertEquals(GameState.NULL_CHAR, wordState[4]);
		assertEquals('e', wordState[5]);
		assertEquals('e', wordState[6]);
		assertEquals(GameState.NULL_CHAR, wordState[7]);
		assertEquals(GameState.NULL_CHAR, wordState[8]);
		assertEquals(GameState.NULL_CHAR, wordState[9]);
		assertEquals(GameState.NULL_CHAR, wordState[10]);
	}
	
	@Test
	void runOutOfGuessesTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Manually set the secret word
		gm.gameState.setSecretWord("engineering");
		
		// Guess an incorrect letter 6 times
		gm.guessLetter('x');
		gm.guessLetter('y');
		gm.guessLetter('z');
		gm.guessLetter('v');
		gm.guessLetter('p');
		gm.guessLetter('d');
		
		// Test that the incorrect number of wrong guesses is now 6
		assertEquals(6, gm.gameState.getNumWrongGuesses());
				
		// Test that the number of correct guesses is 0
		assertEquals(0, gm.gameState.getNumCorrectGuesses());
		
		// Test that the guessed letters list contains x, y, z, v, p, and d
		assertTrue(gm.gameState.getGuessedLetters().contains('x'));
		assertTrue(gm.gameState.getGuessedLetters().contains('y'));
		assertTrue(gm.gameState.getGuessedLetters().contains('z'));
		assertTrue(gm.gameState.getGuessedLetters().contains('v'));
		assertTrue(gm.gameState.getGuessedLetters().contains('p'));
		assertTrue(gm.gameState.getGuessedLetters().contains('d'));
		
		// Test that the game is over
		assertTrue(gm.isGameOver());
		
		// Test that we lost
		assertFalse(gm.didWin());
	}
	
	@Test
	void winTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
				
		// Manually set the secret word
		gm.gameState.setSecretWord("engineering");
				
	    // Guess the correct letters
		gm.guessLetter('e');
		gm.guessLetter('n');
		gm.guessLetter('g');
		gm.guessLetter('i');
		gm.guessLetter('r');
				
		// Test that the incorrect number of wrong guesses is 0
		assertEquals(0, gm.gameState.getNumWrongGuesses());
						
		// Test that the number of correct guesses is 5
		assertEquals(5, gm.gameState.getNumCorrectGuesses());
			
		// Test that the guessed letters list contains e, n, g, i ,r
		assertTrue(gm.gameState.getGuessedLetters().contains('e'));
		assertTrue(gm.gameState.getGuessedLetters().contains('n'));
		assertTrue(gm.gameState.getGuessedLetters().contains('g'));
		assertTrue(gm.gameState.getGuessedLetters().contains('i'));
		assertTrue(gm.gameState.getGuessedLetters().contains('r'));
				
		// Test that the game is over
		assertTrue(gm.isGameOver());
				
		// Test that we won
		assertTrue(gm.didWin());
	}

	
	@Test
	void startNewGameTest() 
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
						
		// Manually set the secret word
		gm.gameState.setSecretWord("engineering");
						
		// Guess some correct letters
		gm.guessLetter('e');
		gm.guessLetter('n');
		gm.guessLetter('g');
		
		// Guess some wrong letters
		gm.guessLetter('x');
		gm.guessLetter('y');
		
		// Guess another right letter
		gm.guessLetter('r');
		
		// Test that the incorrect number of wrong guesses is 2
		assertEquals(2, gm.gameState.getNumWrongGuesses());
								
		// Test that the number of correct guesses is 4
		assertEquals(4, gm.gameState.getNumCorrectGuesses());
		
		// Get bored and decide to start a new game...
		gm.startNewGame();
		
		// Manually set the secret word again
		gm.gameState.setSecretWord("bugs");
		
		// Test that number of guesses have been reset to 0
		assertEquals(0, gm.gameState.getNumWrongGuesses());
		assertEquals(0, gm.gameState.getNumCorrectGuesses());
		
		// Test that guessed letters list is empty
		assertEquals(0, gm.gameState.getGuessedLetters().size());
		
		// Test that word state is _ _ _ _
		char[] wordState = gm.gameState.getWordState();
		assertEquals(4, wordState.length);
		assertEquals(GameState.NULL_CHAR, wordState[0]);
		assertEquals(GameState.NULL_CHAR, wordState[1]);
		assertEquals(GameState.NULL_CHAR, wordState[2]);
		assertEquals(GameState.NULL_CHAR, wordState[3]);
	}
	
	@Test
	void loadGameFromObjTest()
	{
		// TODO
	}
	
	@Test
	void loadGameFromFileTest()
	{
		// TODO
	}
	
	@Test
	void saveGameTest()
	{
		// TODO
	}

}
