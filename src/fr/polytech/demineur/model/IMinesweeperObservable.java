package fr.polytech.demineur.model;

/**
 * This interface represents a Minesweeper observable.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public interface IMinesweeperObservable
{
	/**
	 * The on left mouse clicked.
	 * 
	 * @param coordX
	 *            The X coordinate.
	 * @param coordY
	 *            The Y coordinate.
	 */
	public void onLeftMouseClicked(int coordX, int coordY);

	/**
	 * The on right mouse clicked.
	 * 
	 * @param coordX
	 *            The X coordinate.
	 * @param coordY
	 *            The Y coordinate.
	 */
	public void onRightMouseClicked(int coordX, int coordY);

	/**
	 * Start the timer.
	 */
	public void startTimer();

	/**
	 * Stop the timer.
	 */
	public void stopTimer();

	/**
	 * Get the number of mines.
	 * 
	 * @return The number of mines.
	 */
	public int getNbMines();
}