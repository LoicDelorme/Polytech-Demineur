package fr.polytech.demineur.controller;

import fr.polytech.demineur.model.Cell;

/**
 * This interface represents a Minesweeper observer.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public interface IMinesweeperObserver
{
	/**
	 * Notify the player is dead.
	 */
	public void playerIsDead();

	/**
	 * Notify the player has won.
	 */
	public void playerHasWon();

	/**
	 * Notify update a cell.
	 * 
	 * @param cell
	 *            The cell to update.
	 */
	public void updateCell(Cell cell);

	/**
	 * Set the number of mines remaining.
	 * 
	 * @param nbMinesRemaining
	 *            The number of mines remaining.
	 */
	public void setNbMines(int nbMinesRemaining);

	/**
	 * Set the score.
	 * 
	 * @param score
	 *            The score.
	 */
	public void setScore(int score);

	/**
	 * Set the time.
	 * 
	 * @param time
	 *            The elapsed time.
	 */
	public void setTime(long time);

	/**
	 * Reset the board game.
	 */
	public void resetBoardGame();
}