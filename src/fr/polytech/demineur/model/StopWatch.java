package fr.polytech.demineur.model;

/**
 * This class represents a stop watch.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class StopWatch
{
	/**
	 * The start time.
	 */
	private long startTime = 0;

	/**
	 * The stop time.
	 */
	private long stopTime = 0;

	/**
	 * If the stop watch is running.
	 */
	private boolean running = false;

	/**
	 * Check if the stop watch is running.
	 * 
	 * @return True or False.
	 */
	public boolean isRunning()
	{
		return this.running;
	}

	/**
	 * Start the stop watch.
	 */
	public void start()
	{
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	/**
	 * Stop the stop watch.
	 */
	public void stop()
	{
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	/**
	 * Get the elapsed time.
	 * 
	 * @return The elapsed time.
	 */
	public long getElapsedTime()
	{
		long elapsed;
		if (this.running)
		{
			elapsed = (System.currentTimeMillis() - this.startTime);
		}
		else
		{
			elapsed = (this.stopTime - this.startTime);
		}

		return elapsed;
	}

	/**
	 * Get the elapsed times in seconds.
	 * 
	 * @return The elapsed times in seconds.
	 */
	public long getElapsedTimeSecs()
	{
		long elapsed;
		if (this.running)
		{
			elapsed = ((System.currentTimeMillis() - this.startTime) / 1000);
		}
		else
		{
			elapsed = ((this.stopTime - this.startTime) / 1000);
		}

		return elapsed;
	}
}