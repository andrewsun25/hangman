package hangman;

import java.util.ArrayList;

/**
 * Class object to hold all data related to a game of hangman.
 * @author drake
 *
 */
public class GameState implements java.io.Serializable {
	/**
	 * Default value in an empty char[] array
	 */
	static transient char NULL_CHAR = '\u0000';
	public String secretWord;
	public char[] secretWordArray; // To be filled by correct letter guesses
	public ArrayList<Character> guessedLetters;
	public int numWrongGuesses;
	public int numCorrectGuesses;
	
	/**
	 * Gets the current secret word.
	 * @return The secret word.
	 */
	public String getSecretWord()
	{
		return secretWord;
	}
	
	/**
	 * Sets a new secret word.
	 * @param word The value to set the new secret word to. 
	 */
	public void setSecretWord(String word)
	{
		secretWord = word;
		secretWordArray = new char[secretWord.length()];
		guessedLetters = new ArrayList<Character>();
		numWrongGuesses = 0;
		numCorrectGuesses = 0;
	}
	
	/**
	 * Gets the number of incorrect guesses in this game.
	 * @return The number of guesses that were incorrect.
	 */
	public int getNumWrongGuesses()
	{
		return numWrongGuesses;
	}
	
	/**
	 * Increases the number of wrong guesses by 1.
	 */
	public void incrementNumWrongGuesses()
	{
		numWrongGuesses++;
	}
	
	/**
	 * Gets the number of correct guesses in this game.
	 * @return The number of guesses that were correct.
	 */
	public int getNumCorrectGuesses()
	{
		return numCorrectGuesses;
	}
	
	/**
	 * Increases the number of correct guesses by 1.
	 */
	public void incrementNumCorrectGuesses()
	{
		numCorrectGuesses++;
	}
	
	/**
	 * Returns a list of characters that have been guessed in this game.
	 * @return A list of characters that have been guessed so far.
	 */
	public ArrayList<Character> getGuessedLetters()
	{
		return guessedLetters;
	}
	
	/**
	 * Adds a new letter as a guess.
	 * @param letter The letter that has been guessed.
	 */
	public void addGuessedLetter(char letter)
	{
		guessedLetters.add(letter);
	}
	
	/**
	 * Returns a char[] array the size of the secret word where each index is either empty
	 * or a letter that was guessed previously and has been filled in.
	 * @return A char[] array that represents the current state of the secret word.
	 */
	public char[] getWordState()
	{
		return secretWordArray;
	}
	
	/**
	 * Sets the provided letter a the provided index position in the secret word array.
	 * @param index Position to put the letter.
	 * @param letter Letter to put the at the provided position.
	 */
	void setLetter(int index, char letter)
	{
		if (index >= 0 && index < secretWordArray.length)
		{
			secretWordArray[index] = letter;
		}
	}
}
