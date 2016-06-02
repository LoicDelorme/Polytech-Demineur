package fr.polytech.demineur.model;

/**
 * This enumeration represents the difficulty.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public enum Difficulty
{
    /**
     * The difficulty is easy.
     */
	EASY(0.125),

	/**
	 * The difficulty is medium.
	 */
	MEDIUM(0.1429),

	/**
	 * The difficulty is hard.
	 */
	HARD(0.2),

	/**
	 * The difficulty is hard core.
	 */
	HARDCORE(0.25);

	/**
	 * The percentage of mines.
	 */
	private final double percentage;

	/**
	 * Private constructor.
	 * 
	 * @param percentage
	 *            The percentage.
	 */
	private Difficulty(double percentage)
	{
		this.percentage = percentage;
	}

	/**
	 * Get the percentage.
	 * 
	 * @return The percentage.
	 */
	public double getPercentage()
	{
		return this.percentage;
	}
}