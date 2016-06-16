package fr.polytech.demineur.model;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.demineur.controller.IMinesweeperObserver;
import javafx.application.Platform;

/**
 * This class represents a cell.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Cell
{
	/**
	 * The X coordinate.
	 */
	private final int x;

	/**
	 * The Y coordinate.
	 */
	private final int y;

	/**
	 * The cell type.
	 */
	private CellType cellType;

	/**
	 * If the cell is hidden.
	 */
	private boolean isHidden;

	/**
	 * If the cell is marked.
	 */
	private boolean isMarked;

	/**
	 * The neighbors.
	 */
	private final List<Cell> neightbors;

	/**
	 * The cell observer.
	 */
	private final ICellObserver cellObserver;

	/**
	 * The Minesweeper observer.
	 */
	private final IMinesweeperObserver minesweeperObserver;

	/**
	 * Create a cell.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 * @param cellType
	 *            The cell type.
	 * @param cellObserver
	 *            The cell observer.
	 * @param minesweeperObserver
	 *            The Minesweeper observer.
	 */
	public Cell(int x, int y, CellType cellType, ICellObserver cellObserver, IMinesweeperObserver minesweeperObserver)
	{
		this.x = x;
		this.y = y;
		this.cellType = cellType;
		this.cellObserver = cellObserver;
		this.minesweeperObserver = minesweeperObserver;
		this.isHidden = true;
		this.isMarked = false;
		this.neightbors = new ArrayList<Cell>();
	}

	/**
	 * Get the X coordinate.
	 * 
	 * @return The X coordinate.
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Get the Y coordinate.
	 * 
	 * @return The Y coordinate.
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Set the cell type.
	 * 
	 * @param cellType
	 *            The cell type.
	 */
	public void setCellType(CellType cellType)
	{
		this.cellType = cellType;
	}

	/**
	 * Get the cell type.
	 * 
	 * @return The cell type.
	 */
	public CellType getCellType()
	{
		return this.cellType;
	}

	/**
	 * Show the cell.
	 */
	public void show()
	{
		this.isHidden = false;
	}

	/**
	 * Check if the cell is hidden.
	 * 
	 * @return True or False.
	 */
	public boolean isHidden()
	{
		return this.isHidden;
	}

	/**
	 * Check if the cell is marked.
	 * 
	 * @return True or False.
	 */
	public boolean isMarked()
	{
		return this.isMarked;
	}

	/**
	 * Add a neightbor.
	 * 
	 * @param cell
	 *            A neightbor.
	 */
	public void addNeightbor(Cell cell)
	{
		this.neightbors.add(cell);
	}

	/**
	 * Notify that the cell has been left clicked.
	 */
	public void notifyHasBeenLeftClicked()
	{
		if (this.isHidden)
		{
			if (this.cellType == CellType.MINE)
			{
				this.cellObserver.displayAllMines();
			}
			else
			{
				if (this.cellType == CellType.EMPTY)
				{
					discoverCellAndNeightbors();
				}
				else
				{
					this.isHidden = false;
					this.cellObserver.incrementScore();
					Platform.runLater(() -> this.minesweeperObserver.updateCell(this));
				}

				this.cellObserver.checkVictory();
			}
		}
	}

	/**
	 * Discover cell and neightbors.
	 */
	private void discoverCellAndNeightbors()
	{
		if (this.isHidden)
		{
			this.isHidden = false;
			this.cellObserver.incrementScore();
			Platform.runLater(() -> this.minesweeperObserver.updateCell(this));

			if (this.cellType == CellType.EMPTY)
			{
				for (Cell neightbor : this.neightbors)
				{
					neightbor.discoverCellAndNeightbors();
				}
			}
		}
	}

	/**
	 * Notify that the cell has been right clicked.
	 */
	public void notifyHasBeenRightClicked()
	{
		if (this.isHidden)
		{
			if (this.isMarked)
			{
				this.isMarked = false;
				this.cellObserver.decrementScore();
				this.cellObserver.incrementNbMines();
			}
			else
			{
				this.isMarked = true;
				this.cellObserver.incrementScore();
				this.cellObserver.decrementNbMines();
			}

			Platform.runLater(() -> this.minesweeperObserver.updateCell(this));
			this.cellObserver.checkVictory();
		}
	}
}