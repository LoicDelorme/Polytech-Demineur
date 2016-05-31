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
	 * The board game size.
	 */
	protected final int size;

	/**
	 * The board game.
	 */
	protected final Cell[][] boardGame;

	/**
	 * The number of mines.
	 */
	protected int nbMines;

	/**
	 * The score.
	 */
	private int score;

	/**
	 * The Minesweeper observer.
	 */
	private final IMinesweeperObserver observer;

	/**
	 * Create a Minesweeper.
	 * 
	 * @param size
	 *            The board game size.
	 * @param minesweeperObserver
	 *            The Minesweeper observer.
	 */
	public Minesweeper(int size, IMinesweeperObserver minesweeperObserver)
	{
		this.size = size;
		this.boardGame = new Cell[size][size];
		this.nbMines = 0;
		this.score = 0;
		this.observer = minesweeperObserver;

		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				this.boardGame[x][y] = new Cell(CellType.EMPTY);
			}
		}

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
		final Cell selectedCell = this.boardGame[coordX][coordY];
		if (selectedCell.isHidden())
		{
			if (selectedCell.getCellType() == CellType.MINE)
			{
				for (int x = 0; x < this.size; x++)
				{
					for (int y = 0; y < this.size; y++)
					{
						if (this.boardGame[x][y].getCellType() == CellType.MINE)
						{
							this.boardGame[x][y].show();
							this.observer.updateCell(x, y, this.boardGame[x][y]);
						}
					}
				}

				this.observer.playerIsDead();
			}
			else
			{
				if (selectedCell.getCellType() == CellType.EMPTY)
				{
					discoverCellAndNeighborsFrom(coordX, coordY);
					this.observer.setScore(this.score);
				}
				else
				{
					selectedCell.show();
					this.score++;
					this.observer.updateCell(coordX, coordY, selectedCell);
					this.observer.setScore(this.score);
				}

				checkPossibleVictory();
			}
		}
	}

	/**
	 * Discover the current cell and its neighbors.
	 * 
	 * @param coordX
	 *            The X coordinate.
	 * @param coordY
	 *            The Y coordinate.
	 */
	private void discoverCellAndNeighborsFrom(int coordX, int coordY)
	{
		final Cell cell = this.boardGame[coordX][coordY];
		if (cell.isHidden())
		{
			cell.show();
			this.score++;
			this.observer.updateCell(coordX, coordY, cell);

			if (cell.getCellType() == CellType.EMPTY)
			{
				final int[] dx = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
				final int[] dy = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };
				int xTemp;
				int yTemp;
				for (int offset = 0; offset < dx.length; offset++)
				{
					xTemp = coordX + dx[offset];
					yTemp = coordY + dy[offset];
					if (isInBound(xTemp, yTemp))
					{
						discoverCellAndNeighborsFrom(xTemp, yTemp);
					}
				}
			}
		}
	}

	/**
	 * Check if a coordinate is on the board game.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 * @return True or False.
	 */
	protected boolean isInBound(int x, int y)
	{
		return ((x >= 0) && (x < this.size) && (y >= 0) && (y < this.size));
	}

	/**
	 * Check for a possible victory.
	 */
	private void checkPossibleVictory()
	{
		if (this.nbMines == 0)
		{
			int nbRemainingCell = 0;
			for (int x = 0; x < this.size; x++)
			{
				for (int y = 0; y < this.size; y++)
				{
					if (this.boardGame[x][y].isHidden() && !this.boardGame[x][y].isMarked())
					{
						nbRemainingCell++;
					}
				}
			}

			if (nbRemainingCell == 0)
			{
				this.observer.playerHasWon();
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#onRightMouseClick(int, int)
	 */
	@Override
	public void onRightMouseClick(int coordX, int coordY)
	{
		final Cell selectedCell = this.boardGame[coordX][coordY];
		if (selectedCell.isHidden())
		{
			if (selectedCell.isMarked())
			{
				selectedCell.unmark();
				this.score--;
				this.nbMines++;
			}
			else
			{
				selectedCell.mark();
				this.score++;
				this.nbMines--;
			}

			this.observer.setNbMines(this.nbMines);
			this.observer.setScore(this.score);
			this.observer.updateCell(coordX, coordY, selectedCell);

			checkPossibleVictory();
		}
	}
}