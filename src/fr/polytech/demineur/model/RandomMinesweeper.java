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
	 * The default mines number.
	 */
	public static final int DEFAULT_MINES_NUMBER = 16;

	/**
	 * Create a random Minesweeper.
	 * 
	 * @param size
	 *            The size.
	 * @param minesweeperObserver
	 *            The Minesweeper observer.
	 */
	public RandomMinesweeper(int size, IMinesweeperObserver minesweeperObserver)
	{
		super(size, minesweeperObserver);
	}

	/**
	 * @see fr.polytech.demineur.model.Minesweeper#initializeBoardGame()
	 */
	@Override
	public void initializeBoardGame()
	{
		final Random generator = new Random(System.currentTimeMillis());
		do
		{
			final int x = generator.nextInt(DEFAULT_MINES_NUMBER);
			final int y = generator.nextInt(DEFAULT_MINES_NUMBER);
			if (this.boardGame[x][y].getCellType() != CellType.MINE)
			{
				this.boardGame[x][y].setCellType(CellType.MINE);
				this.nbMinesToBeMarked++;
				computeNeightbors(x, y);
			}
		} while (this.nbMinesToBeMarked != DEFAULT_MINES_NUMBER);
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
		for (int offset = 0; offset < dx.length; offset++)
		{
			final int xTemp = x + dx[offset];
			final int yTemp = y + dy[offset];
			if ((xTemp >= 0) && (xTemp < this.size) && (yTemp >= 0) && (yTemp < this.size))
			{
				final CellType neightborsCellType = this.boardGame[xTemp][yTemp].getCellType();
				if (neightborsCellType != CellType.MINE)
				{
					this.boardGame[xTemp][yTemp].setCellType(CellType.getCellTypeFromValue(CellType.getValueFromCellType(neightborsCellType) + 1));
				}
			}
		}
	}
}