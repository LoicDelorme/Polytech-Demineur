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
	 * The on left mouse click.
	 * 
	 * @param coordX
	 *            The X coordinate.
	 * @param coordY
	 *            The Y coordinate.
	 */
	public void onLeftMouseClick(int coordX, int coordY);

	/**
	 * The on right mouse click.
	 * 
	 * @param coordX
	 *            The X coordinate.
	 * @param coordY
	 *            The Y coordinate.
	 */
	public void onRightMouseClick(int coordX, int coordY);
}