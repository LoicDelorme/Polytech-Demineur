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
		final double computedMinesNumber = Math.floor(this.width * this.height * this.difficulty.getPercentage());
		final Random generator = new Random(System.currentTimeMillis());
		do
		{
			final int x = generator.nextInt(this.width);
			final int y = generator.nextInt(this.height);
			if (this.boardGame[x][y].getCellType() != CellType.MINE)
			{
				this.boardGame[x][y].setCellType(CellType.MINE);
				computeMineNeightbors(x, y);
				this.nbMines++;
			}
		} while (this.nbMines != computedMinesNumber);
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
				final CellType neightborCellType = this.boardGame[xTemp][yTemp].getCellType();
				if (neightborCellType != CellType.MINE)
				{
					this.boardGame[xTemp][yTemp].setCellType(CellType.getCellTypeFromValue(CellType.getValueFromCellType(neightborCellType) + 1));
				}
			}
		}
	}
}