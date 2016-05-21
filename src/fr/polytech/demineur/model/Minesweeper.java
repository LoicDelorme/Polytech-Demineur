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
	 * The size.
	 */
	protected final int size;

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
		this.size = size;
		this.boardGame = new Cell[size][size];
		this.nbMinesToBeMarked = 0;
		this.score = 0;
		this.minesweeperObserver = minesweeperObserver;

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
		final Cell cell = this.boardGame[coordX][coordY];
		if (cell.getCellType() == CellType.EMPTY)
		{
			discoverCellAndNeighborsFrom(coordX, coordY);
		}
		else
		{
			if (cell.getCellType() == CellType.MINE)
			{
				Cell currentCell = null;
				for (int x = 0; x < this.size; x++)
				{
					for (int y = 0; y < this.size; y++)
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
				this.minesweeperObserver.updateCell(coordX, coordY, cell);
				this.score++;
				this.minesweeperObserver.setScore(this.score);
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
			this.minesweeperObserver.updateCell(coordX, coordY, cell);

			if (cell.getCellType() == CellType.EMPTY)
			{
				final int[] dx = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
				final int[] dy = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };
				for (int offset = 0; offset < dx.length; offset++)
				{
					final int xTemp = coordX + dx[offset];
					final int yTemp = coordY + dy[offset];
					if ((xTemp >= 0) && (xTemp < this.size) && (yTemp >= 0) && (yTemp < this.size))
					{
						discoverCellAndNeighborsFrom(xTemp, yTemp);
					}
				}
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