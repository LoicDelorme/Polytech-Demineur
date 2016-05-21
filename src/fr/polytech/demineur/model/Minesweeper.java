package fr.polytech.demineur.model;

import fr.polytech.demineur.controller.IMinesweeperObserver;

/**
 * This class represents a Minesweeper.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class Minesweeper implements IMinesweeperObservable
{
	/**
	 * The board game.
	 */
	protected final Cell[][] boardGame;

	/**
	 * The number of mines to be marked.
	 */
	protected int nbMinesToBeMarked;

	/**
	 * The score.
	 */
	private int score;

	/**
	 * The Minesweeper observer.
	 */
	private final IMinesweeperObserver minesweeperObserver;

	/**
	 * Create a Minesweeper.
	 * 
	 * @param size
	 *            The size.
	 * @param minesweeperObserver
	 *            The Minesweeper observer.
	 */
	public Minesweeper(int size, IMinesweeperObserver minesweeperObserver)
	{
		this.boardGame = new Cell[size][size];
		this.score = 0;
		this.minesweeperObserver = minesweeperObserver;

		initializeBoardGame();
	}

	/**
	 * Initialize the board game.
	 */
	public abstract void initializeBoardGame();

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#onLeftMouseClick(int, int)
	 */
	@Override
	public void onLeftMouseClick(int coordX, int coordY)
	{
		final Cell cell = this.boardGame[coordX][coordY];
		if (cell.getCellType() == CellType.EMPTY)
		{
			// Recursive display neightbours.
		}
		else
		{
			if (cell.getCellType() == CellType.MINE)
			{
				Cell currentCell = null;
				for (int x = 0; x < this.boardGame.length; x++)
				{
					for (int y = 0; y < this.boardGame.length; y++)
					{
						currentCell = this.boardGame[x][y];
						if (currentCell.getCellType() == CellType.MINE)
						{
							currentCell.show();
							this.minesweeperObserver.updateCell(x, y, currentCell);
						}
					}
				}

				this.minesweeperObserver.playerIsDead();
			}
			else
			{
				cell.show();
				this.minesweeperObserver.setScore(++this.score);
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#onRightMouseClick(int, int)
	 */
	@Override
	public void onRightMouseClick(int coordX, int coordY)
	{
		final Cell cell = this.boardGame[coordX][coordY];
		if (cell.isHidden())
		{
			if (cell.isMarked())
			{
				cell.unmark();
				this.nbMinesToBeMarked++;
			}
			else
			{
				cell.mark();
				this.nbMinesToBeMarked--;
			}

			this.minesweeperObserver.setNbMinesRemaining(this.nbMinesToBeMarked);
			this.minesweeperObserver.updateCell(coordX, coordY, cell);
		}
	}
}