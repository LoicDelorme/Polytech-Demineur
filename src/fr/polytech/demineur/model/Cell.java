package fr.polytech.demineur.model;

/**
 * This class represents a cell.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Cell
{
	/**
	 * The cell type.
	 */
	private final CellType cellType;

	/**
	 * If the cell is hidden.
	 */
	private boolean isHidden;

	/**
	 * If the cell is marked.
	 */
	private boolean isMarked;

	/**
	 * Create a cell.
	 * 
	 * @param cellType
	 *            The cell type.
	 */
	public Cell(CellType cellType)
	{
		this.cellType = cellType;
		this.isHidden = true;
		this.isMarked = false;
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
	 * Show the cell.
	 */
	public void show()
	{
		this.isHidden = false;
	}

	/**
	 * Hide the cell.
	 */
	public void hide()
	{
		this.isHidden = true;
	}

	/**
	 * Mark the cell.
	 */
	public void mark()
	{
		this.isMarked = true;
	}

	/**
	 * Unmark the cell.
	 */
	public void unmark()
	{
		this.isMarked = false;
	}
}
