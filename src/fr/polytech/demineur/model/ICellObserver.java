package fr.polytech.demineur.model;

/**
 * This interface represents a cell observer.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public interface ICellObserver
{
	/**
	 * Increment the score.
	 */
	public void incrementScore();

	/**
	 * Decrement the score.
	 */
	public void decrementScore();

	/**
	 * Increment the number of mines.
	 */
	public void incrementNbMines();

	/**
	 * Decrement the number of mines.
	 */
	public void decrementNbMines();

	/**
	 * Check the possible victory.
	 */
	public void checkVictory();

	/**
	 * Display all mines.
	 */
	public void displayAllMines();
}