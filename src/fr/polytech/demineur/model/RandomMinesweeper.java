package fr.polytech.demineur.model;

import java.util.Random;

import fr.polytech.demineur.controller.IMinesweeperObserver;

/**
 * This class represents a random Minesweeper.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class RandomMinesweeper extends Minesweeper
{
	/**
	 * Create a random Minesweeper.
	 * 
	 * @param width
	 *            The board game width.
	 * @param height
	 *            The board game height.
	 * @param difficulty
	 *            The difficulty.
	 * @param minesweeperObserver
	 *            The Minesweeper observer.
	 */
	public RandomMinesweeper(int width, int height, Difficulty difficulty, IMinesweeperObserver minesweeperObserver)
	{
		super(width, height, difficulty, minesweeperObserver);
	}

	/**
	 * @see fr.polytech.demineur.model.Minesweeper#initializeBoardGame()
	 */
	@Override
	public void initializeBoardGame()
	{
		for (int x = 0; x < this.width; x++)
		{
			for (int y = 0; y < this.height; y++)
			{
				this.boardGame[x][y] = new Cell(x, y, CellType.EMPTY, this, this.observer);
			}
		}

		final double computedMinesNumber = Math.floor(this.width * this.height * this.difficulty.getPercentage());
		final Random generator = new Random(System.currentTimeMillis());
		while (this.nbMines != computedMinesNumber)
		{
			final int x = generator.nextInt(this.width);
			final int y = generator.nextInt(this.height);

			final Cell cell = this.boardGame[x][y];
			if (cell.getCellType() != CellType.MINE)
			{
				cell.setCellType(CellType.MINE);
				computeMineNeightbors(x, y);
				this.nbMines++;
			}
		}

		for (int x = 0; x < this.width; x++)
		{
			for (int y = 0; y < this.height; y++)
			{
				computeNeightbors(x, y);
			}
		}
	}

	/**
	 * Compute mine's neightbors.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 */
	private void computeMineNeightbors(int x, int y)
	{
		final int[] dx = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
		final int[] dy = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };

		int xTemp;
		int yTemp;
		for (int offset = 0; offset < dx.length; offset++)
		{
			xTemp = x + dx[offset];
			yTemp = y + dy[offset];
			if (isInBound(xTemp, yTemp))
			{
				final Cell currentNeightbor = this.boardGame[xTemp][yTemp];
				if (currentNeightbor.getCellType() != CellType.MINE)
				{
					this.boardGame[xTemp][yTemp].setCellType(CellType.getCellTypeFromValue(CellType.getValueFromCellType(currentNeightbor.getCellType()) + 1));
				}
			}
		}
	}

	/**
	 * Compute neightbors.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 */
	private void computeNeightbors(int x, int y)
	{
		final int[] dx = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
		final int[] dy = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };

		final Cell cell = this.boardGame[x][y];
		int xTemp;
		int yTemp;
		for (int offset = 0; offset < dx.length; offset++)
		{
			xTemp = x + dx[offset];
			yTemp = y + dy[offset];
			if (isInBound(xTemp, yTemp))
			{
				cell.addNeightbor(this.boardGame[xTemp][yTemp]);
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
	private boolean isInBound(int x, int y)
	{
		return ((x >= 0) && (x < this.width) && (y >= 0) && (y < this.height));
	}
}