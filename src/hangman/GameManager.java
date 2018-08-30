package hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the overall game state and game rules.
 * @author drake
 *
 */
public class GameManager {
	static int numAllowedGuesses = 6;
	String wordListPath = "word_list.txt";
	ArrayList<String> wordList;
	GameState gameState;
	
	/**
	 * Initializes a new GameManager object.
	 */
	public GameManager()
	{
		parseWordList(wordListPath);
		gameState = new GameState();
		startNewGame();
	}
	
	/**
	 * Initializes a new GameManager object with a provided word list.
	 * @param wordList Text file containing one word per line to be used for the game.
	 */
	public GameManager(String wordList)
	{
		parseWordList(wordList);
		gameState = new GameState();
		startNewGame();
	}
	
	/**
	 * Initializes a new GameManager object with the provided game state.
	 * @param state The game state to set as the current game.
	 */
	public GameManager(GameState state)
	{
		parseWordList(wordListPath);
		loadGame(gameState);
	}
	
	/**
	 * Parses a text file of words to use as options for the game.
	 * The word list must be in the format of one word per line.
	 * @param filePath The file path to the word list.
	 */
	void parseWordList(String filePath)
	{
		wordList = new ArrayList<String>();
		
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String str;
	
			while((str = in.readLine()) != null)
			{ 
			    wordList.add(str.trim());
			}
			
			in.close();
		}
		catch (Exception e)
		{
			System.out.println("Word list not found!");
		}
		
		// If the word list is of size 0, generate some default words
		if (wordList.size() == 0)
		{
			generateDefaultWordList();
		}
	}
	
	/**
	 * Generate a small default word list in case a word list
	 * text file is not provided or is invalid.
	 */
	void generateDefaultWordList()
	{
		wordList = new ArrayList<String>();
		wordList.add("engineering");
		wordList.add("computer");
		wordList.add("science");
		wordList.add("programming");
		wordList.add("bugs");
	}
	
	/**
	 * Chooses a new secret word for the game.
	 */
	public void startNewGame()
	{
		int index = 0;
		if (wordList.size() > 1) // size must be greater than 1 for rand to work
		{
		// Get a random number between 0 and wordList.size - 1
			Random rand = new Random();
			index = rand.nextInt(wordList.size() - 1);
		}
		// The random number is the index to pull the secret word from the word list
		gameState.setSecretWord(wordList.get(index));
	}
	
	/**
	 * Guess a letter and update the game state.
	 * @param guess The letter to guess
	 * @return An array list of indexes where the letter matched in the secret word.
	 * A size of 0 means no matches
	 * were found.
	 */
	public ArrayList<Integer> guessLetter(char guess)
	{
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		guess = Character.toLowerCase(guess); // Convert to lower case
		if (isGameOver() == false)
		{
			gameState.addGuessedLetter(guess);
			String secretWord = gameState.getSecretWord().toLowerCase(); // Convert to lower case
			int index = secretWord.indexOf(guess);
			
			// Wrong guess
			if (index < 0)
			{
				gameState.incrementNumWrongGuesses();
			}
			else // Right guess, see how many matches there are
			{		
				// Increase the correct guess count (only do this once)
				gameState.incrementNumCorrectGuesses();
				
				while (index >= 0) 
				{
					// Fill in the secret word array at the correct spots
					gameState.setLetter(index, guess);
					
					// Add this index to the list to return
					indexList.add(index);
					
					// Get the next occurrence of this guess (if any)
					index = secretWord.indexOf(guess, index + 1);
				}
			}
		}
		else
		{
			System.out.println("The game is over!");
		}
		
		return indexList;
	}
	
	/**
	 * Gets the current game state.
	 * @return The current game state.
	 */
	public GameState getGameState()
	{
		return gameState;
	}
	
	/**
	 * Gets whether the game is over or not.
	 * @return True if the game is over, false otherwise.
	 */
	public boolean isGameOver()
	{
		boolean gameOver = false;
		if (gameState.getNumWrongGuesses() >= numAllowedGuesses)
		{
			gameOver = true;
		}
		else if (didWin())
		{
			gameOver = true;
		}
		return gameOver;
	}
	
	/**
	 * Gets whether the current game state indicates the secret word has been guessed.
	 * @return True if the secret word has been guessed, false otherwise.
	 */
	public boolean didWin()
	{
		boolean won = false;
		String currentWord = new String(gameState.getWordState());
		if (currentWord.equalsIgnoreCase(gameState.getSecretWord()))
		{
			won = true;
		}
		return won;
	}
	
	/**
	 * Load a game state from a game state object.
	 * @param state The game state to load.
	 */
	public void loadGame(GameState state)
	{
		gameState = state;
	}
	
	/**
	 * Load a game state from a file path.
	 * @param filePath The full file path to a saved game state file.
	 */
	public void loadGame(String filePath)
	{
		// TODO: Load a game state from a file
	}
	
	/**
	 * Saves the current game to a file that can be loaded later.
	 * @param filePath The full file path to save to.
	 */
	public void saveAs(String filePath)
	{
		// TODO: Save a game state to a file
	}
}
