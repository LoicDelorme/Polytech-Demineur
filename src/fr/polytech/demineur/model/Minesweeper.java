package fr.polytech.demineur.model;

import fr.polytech.demineur.controller.IMinesweeperObserver;
import javafx.application.Platform;

/**
 * This class represents a Minesweeper.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class Minesweeper implements IMinesweeperObservable, ICellObserver
{
	/**
	 * The board game width.
	 */
	protected final int width;

	/**
	 * The board game height.
	 */
	protected final int height;

	/**
	 * The difficulty.
	 */
	protected final Difficulty difficulty;

	/**
	 * The Minesweeper observer.
	 */
	protected final IMinesweeperObserver observer;

	/**
	 * The board game.
	 */
	protected final Cell[][] boardGame;

	/**
	 * The number of mines.
	 */
	protected int nbMines;

	/**
	 * The score.
	 */
	private int score;

	/**
	 * The timer thread.
	 */
	private Thread timerThread;

	/**
	 * Create a Minesweeper.
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
	public Minesweeper(int width, int height, Difficulty difficulty, IMinesweeperObserver minesweeperObserver)
	{
		this.width = width;
		this.height = height;
		this.difficulty = difficulty;
		this.observer = minesweeperObserver;
		this.boardGame = new Cell[width][height];
		this.nbMines = 0;
		this.score = 0;
		this.timerThread = null;

		initializeBoardGame();
	}

	/**
	 * Initialize the board game.
	 */
	public abstract void initializeBoardGame();

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#incrementScore()
	 */
	@Override
	public void incrementScore()
	{
		updateScore(1);
		Platform.runLater(() -> this.observer.setScore(this.score));
	}

	/**
	 * Update the score.
	 * 
	 * @param value
	 *            The value to add.
	 */
	private void updateScore(int value)
	{
		this.score += value;
	}

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#decrementScore()
	 */
	@Override
	public void decrementScore()
	{
		updateScore(-1);
		Platform.runLater(() -> this.observer.setScore(this.score));
	}

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#incrementNbMines()
	 */
	@Override
	public void incrementNbMines()
	{
		updateNbMines(1);
		Platform.runLater(() -> this.observer.setNbMines(this.nbMines));
	}

	/**
	 * Update the number of mines.
	 * 
	 * @param value
	 *            The value to add.
	 */
	private void updateNbMines(int value)
	{
		this.nbMines += value;
	}

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#decrementNbMines()
	 */
	@Override
	public void decrementNbMines()
	{
		updateNbMines(-1);
		Platform.runLater(() -> this.observer.setNbMines(this.nbMines));
	}

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#checkVictory()
	 */
	@Override
	public void checkVictory()
	{
		if (this.nbMines == 0)
		{
			int nbRemainingCellToShow = 0;
			for (int x = 0; x < this.width; x++)
			{
				for (int y = 0; y < this.height; y++)
				{
					if (this.boardGame[x][y].isHidden() && !this.boardGame[x][y].isMarked())
					{
						nbRemainingCellToShow++;
					}
				}
			}

			if (nbRemainingCellToShow == 0)
			{
				Platform.runLater(() -> this.observer.playerHasWon());
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.model.ICellObserver#displayAllMines()
	 */
	@Override
	public void displayAllMines()
	{
		for (int x = 0; x < this.width; x++)
		{
			for (int y = 0; y < this.height; y++)
			{
				if (this.boardGame[x][y].getCellType() == CellType.MINE)
				{
					final Cell currentMine = this.boardGame[x][y];
					currentMine.show();
					Platform.runLater(() -> this.observer.updateCell(currentMine));
				}
			}
		}

		Platform.runLater(() -> this.observer.playerIsDead());
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#onLeftMouseClicked(int, int)
	 */
	@Override
	public void onLeftMouseClicked(int coordX, int coordY)
	{
		this.boardGame[coordX][coordY].notifyHasBeenLeftClicked();
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#onRightMouseClicked(int, int)
	 */
	@Override
	public void onRightMouseClicked(int coordX, int coordY)
	{
		this.boardGame[coordX][coordY].notifyHasBeenRightClicked();
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#startTimer()
	 */
	@Override
	public void startTimer()
	{
		if (this.timerThread == null)
		{
			this.timerThread = new Thread(() ->
			{
				final StopWatch stopWatch = new StopWatch();
				stopWatch.start();

				while (!Thread.currentThread().isInterrupted())
				{
					try
					{
						Thread.sleep(250);
						Platform.runLater(() -> this.observer.setTime(stopWatch.getElapsedTimeSecs()));
					}
					catch (Exception e)
					{
						break;
					}
				}
			});

			this.timerThread.start();
		}
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#stopTimer()
	 */
	@Override
	public void stopTimer()
	{
		if (this.timerThread != null)
		{
			this.timerThread.interrupt();
			this.timerThread = null;
		}
	}

	/**
	 * @see fr.polytech.demineur.model.IMinesweeperObservable#getNbMines()
	 */
	@Override
	public int getNbMines()
	{
		return this.nbMines;
	}
}