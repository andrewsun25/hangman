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
		assertEquals(50, gm.getWordList().size());
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
			gm.setWordList(file.getAbsolutePath());
			
			// Assert that the size is correct and all expected words are in the list
			assertEquals(5, gm.getWordList().size());
			assertEquals("word1", gm.getWordList().get(0));
			assertEquals("wordwordword2", gm.getWordList().get(1));
			assertEquals("thisIsATest", gm.getWordList().get(2));
			assertEquals("cOmPuTeR", gm.getWordList().get(3));
			assertEquals("veryveryveryveryLooooong", gm.getWordList().get(4));
			
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
		if (gm.getWordList().size() != 5)
		{
			isDefault = false;
		}
		if ("engineering".equalsIgnoreCase(gm.getWordList().get(0)) != true)
		{
			isDefault = false;
		}
		if ("computer".equalsIgnoreCase(gm.getWordList().get(1)) != true)
		{
			isDefault = false;
		}
		if ("science".equalsIgnoreCase(gm.getWordList().get(2)) != true)
		{
			isDefault = false;
		}
		if ("programming".equalsIgnoreCase(gm.getWordList().get(3)) != true)
		{
			isDefault = false;
		}
		if ("bugs".equalsIgnoreCase(gm.getWordList().get(4)) != true)
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
			gm.setWordList(file.getAbsolutePath());
			
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
		gm.setWordList("C:\\fake\\path\\word.txt");
						
		// Assert that the default word list was used instead
		assertTrue(isDefaultWordList(gm));
	}
	
	@Test
	void guessWrongLetterTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Manually set the secret word
		gm.getGameState().setSecretWord("engineering");
		
		// Guess an incorrect letter
		GameManager.Result result = gm.guessLetter('x');
		
		// Test that the ArrayLIst returned was empty
		assertEquals(GameManager.Result.WRONG, result);
		
		// Test that the incorrect number of wrong guesses was incremented
		assertEquals(1, gm.getGameState().getNumWrongGuesses());
		
		// Test that the number of correct guesses is still 0
		assertEquals(0, gm.getGameState().getNumCorrectGuesses());
		
		// Test that the guessed letters list contains 'x'
		assertTrue(gm.getGameState().getGuessedLetters().contains('x'));
	}
	
	@Test
	void guessCorrectLetterTest()
	{
		// Create a new default game manager
		GameManager gm = new GameManager();
		
		// Manually set the secret word
		gm.getGameState().setSecretWord("engineering");
		
		// Guess an correct letter
		GameManager.Result result = gm.guessLetter('e');
		
		// Test that the ArrayList returned 3 hits
		assertEquals(GameManager.Result.CORRECT, result);
		
		// Test that the incorrect number of wrong guesses is still 0
		assertEquals(0, gm.getGameState().getNumWrongGuesses());
		
		// Test that the number of correct guesses was incremented
		assertEquals(1, gm.getGameState().getNumCorrectGuesses());
		
		// Test that the guessed letters list contains 'e'
		assertTrue(gm.getGameState().getGuessedLetters().contains('e'));
		
		// Test that the word state is e _ _ _ _ e e _ _ _ _
		char[] wordState = gm.getGameState().getWordState();
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
		gm.getGameState().setSecretWord("engineering");
		
		// Guess an incorrect letter 6 times
		gm.guessLetter('x');
		gm.guessLetter('y');
		gm.guessLetter('z');
		gm.guessLetter('v');
		gm.guessLetter('p');
		gm.guessLetter('d');
		
		// Test that the incorrect number of wrong guesses is now 6
		assertEquals(6, gm.getGameState().getNumWrongGuesses());
				
		// Test that the number of correct guesses is 0
		assertEquals(0, gm.getGameState().getNumCorrectGuesses());
		
		// Test that the guessed letters list contains x, y, z, v, p, and d
		assertTrue(gm.getGameState().getGuessedLetters().contains('x'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('y'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('z'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('v'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('p'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('d'));
		
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
		gm.getGameState().setSecretWord("engineering");
				
	    // Guess the correct letters
		gm.guessLetter('e');
		gm.guessLetter('n');
		gm.guessLetter('g');
		gm.guessLetter('i');
		gm.guessLetter('r');
				
		// Test that the incorrect number of wrong guesses is 0
		assertEquals(0, gm.getGameState().getNumWrongGuesses());
						
		// Test that the number of correct guesses is 5
		assertEquals(5, gm.getGameState().getNumCorrectGuesses());
			
		// Test that the guessed letters list contains e, n, g, i ,r
		assertTrue(gm.getGameState().getGuessedLetters().contains('e'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('n'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('g'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('i'));
		assertTrue(gm.getGameState().getGuessedLetters().contains('r'));
				
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
		gm.getGameState().setSecretWord("engineering");
						
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
		assertEquals(2, gm.getGameState().getNumWrongGuesses());
								
		// Test that the number of correct guesses is 4
		assertEquals(4, gm.getGameState().getNumCorrectGuesses());
		
		// Get bored and decide to start a new game...
		gm.startNewGame();
		
		// Manually set the secret word again
		gm.getGameState().setSecretWord("bugs");
		
		// Test that number of guesses have been reset to 0
		assertEquals(0, gm.getGameState().getNumWrongGuesses());
		assertEquals(0, gm.getGameState().getNumCorrectGuesses());
		
		// Test that guessed letters list is empty
		assertEquals(0, gm.getGameState().getGuessedLetters().size());
		
		// Test that word state is _ _ _ _
		char[] wordState = gm.getGameState().getWordState();
		assertEquals(4, wordState.length);
		assertEquals(GameState.NULL_CHAR, wordState[0]);
		assertEquals(GameState.NULL_CHAR, wordState[1]);
		assertEquals(GameState.NULL_CHAR, wordState[2]);
		assertEquals(GameState.NULL_CHAR, wordState[3]);
	}
	
	@Test
	void loadAndSaveGameFromFileTest()
	{
		// Create a new default game manager
		GameManager gmOrig = new GameManager();
								
		// Manually set the secret word
		gmOrig.getGameState().setSecretWord("engineering");
						
		// Guess some correct letters
		gmOrig.guessLetter('e');
		gmOrig.guessLetter('n');
		gmOrig.guessLetter('g');
				
		// Guess some wrong letters
		gmOrig.guessLetter('x');
		gmOrig.guessLetter('y');
				
		// Guess another right letter
		gmOrig.guessLetter('r');
	
		File file = null;
		try
		{
			// Save the game to a temp file
			file = File.createTempFile("game", ".txt");	
			gmOrig.saveAs(file.getAbsolutePath());
			
			// Load the game to a new game manager
			GameManager gmNew = new GameManager();
			gmNew.loadGame(file.getAbsolutePath());
			
			// Verify all fields are the same
			GameState gsO = gmOrig.getGameState();
			GameState gsN = gmNew.getGameState();
			assertEquals(gsO, gsN);

		}
		catch (Exception e)
		{
			fail("Unable to create a temp file to test game save/load method.");
		}
		finally
		{
			if (file != null)
			{
				file.delete();
			}
		}
	}
}
