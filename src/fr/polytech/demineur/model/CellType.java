package fr.polytech.demineur.model;

/**
 * This enumeration represents a cell type.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public enum CellType
{
    /**
     * The cell is empty.
     */
	EMPTY("/fr/polytech/demineur/view/resources/empty.png"),

	/**
	 * The cell is a mine.
	 */
	MINE("/fr/polytech/demineur/view/resources/mine.png"),

	/**
	 * The cell has a value of 1.
	 */
	ONE("/fr/polytech/demineur/view/resources/1.png"),

	/**
	 * The cell has a value of 2.
	 */
	TWO("/fr/polytech/demineur/view/resources/2.png"),

	/**
	 * The cell has a value of 3.
	 */
	THREE("/fr/polytech/demineur/view/resources/3.png"),

	/**
	 * The cell has a value of 4.
	 */
	FOUR("/fr/polytech/demineur/view/resources/4.png"),

	/**
	 * The cell has a value of 5.
	 */
	FIVE("/fr/polytech/demineur/view/resources/5.png"),

	/**
	 * The cell has a value of 6.
	 */
	SIX("/fr/polytech/demineur/view/resources/6.png"),

	/**
	 * The cell has a value of 7.
	 */
	SEVEN("/fr/polytech/demineur/view/resources/7.png"),

	/**
	 * The cell has a value of 8.
	 */
	EIGHT("/fr/polytech/demineur/view/resources/8.png");

	/**
	 * The image path.
	 */
	private final String imagePath;

	/**
	 * Private constructor.
	 * 
	 * @param imagePath
	 *            The image path.
	 */
	private CellType(String imagePath)
	{
		this.imagePath = imagePath;
	}

	/**
	 * Get the image path.
	 * 
	 * @return The image path.
	 */
	public String getImagePath()
	{
		return this.imagePath;
	}

	/**
	 * Get value from a cell type.
	 * 
	 * @param cellType
	 *            The cell type.
	 * @return The corresponding value.
	 */
	public static int getValueFromCellType(CellType cellType)
	{
		switch (cellType)
		{
			case MINE:
				return -1;
			case ONE:
				return 1;
			case TWO:
				return 2;
			case THREE:
				return 3;
			case FOUR:
				return 4;
			case FIVE:
				return 5;
			case SIX:
				return 6;
			case SEVEN:
				return 7;
			case EIGHT:
				return 8;
			case EMPTY:
			default:
				return 0;
		}
	}

	/**
	 * Get cell type from a value.
	 * 
	 * @param value
	 *            The value.
	 * @return The corresponding cell type.
	 */
	public static CellType getCellTypeFromValue(int value)
	{
		switch (value)
		{
			case -1:
				return MINE;
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			case 4:
				return FOUR;
			case 5:
				return FIVE;
			case 6:
				return SIX;
			case 7:
				return SEVEN;
			case 8:
				return EIGHT;
			case 0:
			default:
				return EMPTY;
		}
	}
}