package fr.polytech.demineur.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.polytech.demineur.model.Cell;
import fr.polytech.demineur.model.CellType;
import fr.polytech.demineur.model.Difficulty;
import fr.polytech.demineur.model.IMinesweeperObservable;
import fr.polytech.demineur.model.RandomMinesweeper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * This class represents the Minesweeper controller.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class MinesweeperController implements Initializable, IMinesweeperObserver
{
	/**
	 * The default Minesweeper width.
	 */
	public static final int DEFAULT_MINESWEEPER_WIDTH = 16;

	/**
	 * The default Minesweeper height.
	 */
	public static final int DEFAULT_MINESWEEPER_HEIGHT = 16;

	/**
	 * The number of mines remaining.
	 */
	@FXML
	private Text nbMinesRemaining;

	/**
	 * The current face button.
	 */
	@FXML
	private Button faceButton;

	/**
	 * The timer.
	 */
	@FXML
	private Text timer;

	/**
	 * The current score.
	 */
	@FXML
	private Text score;

	/**
	 * The board game.
	 */
	@FXML
	private GridPane boardGame;

	/**
	 * The current difficulty.
	 */
	private Difficulty currentDifficulty;

	/**
	 * The close menu item.
	 */
	@FXML
	private MenuItem close;

	/**
	 * The easy difficulty radio menu item.
	 */
	@FXML
	private RadioMenuItem easyDifficulty;

	/**
	 * The medium difficulty radio menu item.
	 */
	@FXML
	private RadioMenuItem mediumDifficulty;

	/**
	 * The hard difficulty radio menu item.
	 */
	@FXML
	private RadioMenuItem hardDifficulty;

	/**
	 * The hardcore difficulty radio menu item.
	 */
	@FXML
	private RadioMenuItem hardcoreDifficulty;

	/**
	 * The about menu item.
	 */
	@FXML
	private MenuItem about;

	/**
	 * The Minesweeper observable.
	 */
	private IMinesweeperObservable minesweeperObservable;

	/**
	 * The executor service.
	 */
	private ExecutorService executorService;

	/**
	 * The stop watch.
	 */
	private StopWatch stopWatch;

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerIsDead()
	 */
	@Override
	public void playerIsDead()
	{
		disableAllButtons();
		this.stopWatch.stop();
		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/sad_smiley.png"));
		this.faceButton.setDisable(false);
	}

	/**
	 * Disable all buttons.
	 */
	private void disableAllButtons()
	{
		for (Node node : this.boardGame.getChildren())
		{
			if (node instanceof Button)
			{
				((Button) node).setDisable(true);
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerHasWon()
	 */
	@Override
	public void playerHasWon()
	{
		disableAllButtons();
		this.stopWatch.stop();
		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/sun_glasses_smiley.png"));
		this.faceButton.setDisable(false);
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#updateCell(int, int, fr.polytech.demineur.model.Cell)
	 */
	@Override
	public void updateCell(int coordX, int coordY, Cell cell)
	{
		Button buttonToUpdate = null;
		for (Node node : this.boardGame.getChildren())
		{
			if ((node instanceof Button) && (GridPane.getRowIndex(node) == coordX) && (GridPane.getColumnIndex(node) == coordY))
			{
				buttonToUpdate = (Button) node;
				break;
			}
		}

		if (cell.isMarked())
		{
			buttonToUpdate.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/red_flag.png"));
		}
		else
		{
			if (cell.isHidden())
			{
				buttonToUpdate.setGraphic(new ImageView(CellType.EMPTY.getImagePath()));
			}
			else
			{
				buttonToUpdate.setGraphic(new ImageView(cell.getCellType().getImagePath()));
				buttonToUpdate.setDisable(true);
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#setNbMines(int)
	 */
	@Override
	public void setNbMines(int nbMinesRemaining)
	{
		this.nbMinesRemaining.setText(String.valueOf(nbMinesRemaining));
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#setScore(int)
	 */
	@Override
	public void setScore(int score)
	{
		this.score.setText(String.valueOf(score));
	}

	/**
	 * Update the timer.
	 */
	private void updateTimer()
	{
		final long elapsedTimeSecs = this.stopWatch.getElapsedTimeSecs();
		final int nbMinutes = (int) (elapsedTimeSecs / 60);
		final int nbSeconds = (int) (elapsedTimeSecs % 60);

		final StringBuilder timerRepresentation = new StringBuilder();
		timerRepresentation.append(nbMinutes > 9 ? nbMinutes : "0" + nbMinutes);
		timerRepresentation.append(" : ");
		timerRepresentation.append(nbSeconds > 9 ? nbSeconds : "0" + nbSeconds);

		this.timer.setText(timerRepresentation.toString());
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#resetBoardGame()
	 */
	@Override
	public void resetBoardGame()
	{
		this.nbMinesRemaining.setText(String.valueOf(this.minesweeperObservable.getNbMines()));
		this.timer.setText("00 : 00");
		this.score.setText(String.valueOf(0));
		this.boardGame.getChildren().remove(1, this.boardGame.getChildren().size());

		for (int x = 0; x < DEFAULT_MINESWEEPER_WIDTH; x++)
		{
			for (int y = 0; y < DEFAULT_MINESWEEPER_HEIGHT; y++)
			{
				final int xTemp = x;
				final int yTemp = y;
				final Button cellButton = new Button(null, new ImageView(CellType.EMPTY.getImagePath()));
				cellButton.setOnMouseClicked(e ->
				{
					if (!this.stopWatch.isRunning())
					{
						this.stopWatch.start();
					}

					final MouseButton mouseClickedButton = e.getButton();
					if (mouseClickedButton == MouseButton.PRIMARY)
					{
						this.executorService.execute(() -> this.minesweeperObservable.onLeftMouseClick(xTemp, yTemp));
						return;
					}

					if (mouseClickedButton == MouseButton.SECONDARY)
					{
						this.executorService.execute(() -> this.minesweeperObservable.onRightMouseClick(xTemp, yTemp));
						return;
					}
				});

				this.boardGame.add(cellButton, y, x);
			}
		}
	}

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.easyDifficulty.selectedProperty().addListener(e ->
		{
			this.currentDifficulty = Difficulty.EASY;
			this.faceButton.setDisable(false);
			this.faceButton.fire();
		});

		this.mediumDifficulty.selectedProperty().addListener(e ->
		{
			this.currentDifficulty = Difficulty.MEDIUM;
			this.faceButton.setDisable(false);
			this.faceButton.fire();
		});

		this.hardDifficulty.selectedProperty().addListener(e ->
		{
			this.currentDifficulty = Difficulty.HARD;
			this.faceButton.setDisable(false);
			this.faceButton.fire();
		});

		this.hardcoreDifficulty.selectedProperty().addListener(e ->
		{
			this.currentDifficulty = Difficulty.HARDCORE;
			this.faceButton.setDisable(false);
			this.faceButton.fire();
		});

		this.close.setOnAction(e ->
		{
			System.exit(0);
		});

		this.currentDifficulty = Difficulty.EASY;
		this.minesweeperObservable = new RandomMinesweeper(DEFAULT_MINESWEEPER_WIDTH, DEFAULT_MINESWEEPER_HEIGHT, this.currentDifficulty, this);
		this.executorService = Executors.newFixedThreadPool(5);
		this.stopWatch = new StopWatch();

		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/happy_smiley.png"));
		this.faceButton.setDisable(true);
		this.faceButton.setOnAction(e ->
		{
			this.minesweeperObservable = new RandomMinesweeper(DEFAULT_MINESWEEPER_WIDTH, DEFAULT_MINESWEEPER_HEIGHT, this.currentDifficulty, this);
			this.stopWatch = new StopWatch();
			resetBoardGame();
			this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/happy_smiley.png"));
			this.faceButton.setDisable(true);
		});

		for (int offset = 0; offset < DEFAULT_MINESWEEPER_WIDTH; offset++)
		{
			this.boardGame.getColumnConstraints().add(new ColumnConstraints(32, 32, 32, Priority.ALWAYS, HPos.CENTER, true));
		}

		for (int offset = 0; offset < DEFAULT_MINESWEEPER_HEIGHT; offset++)
		{
			this.boardGame.getRowConstraints().add(new RowConstraints(32, 32, 32, Priority.ALWAYS, VPos.CENTER, true));
		}

		resetBoardGame();

		Thread timerThread = new Thread(() ->
		{
			try
			{
				while (true)
				{
					Thread.sleep(100);
					updateTimer();
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		timerThread.start();
	}
}