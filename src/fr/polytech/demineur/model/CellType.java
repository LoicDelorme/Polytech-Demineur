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
	EMPTY(""),

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
}