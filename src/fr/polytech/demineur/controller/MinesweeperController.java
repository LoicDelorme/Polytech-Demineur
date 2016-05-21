package fr.polytech.demineur.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.polytech.demineur.model.Cell;
import fr.polytech.demineur.model.CellType;
import fr.polytech.demineur.model.IMinesweeperObservable;
import fr.polytech.demineur.model.RandomMinesweeper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * This class represents the Demineur controller.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class MinesweeperController implements Initializable, IMinesweeperObserver
{
	/**
	 * The default Minesweeper size.
	 */
	public static final int DEFAULT_MINESWEEPER_SIZE = 16;

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
	 * The Minesweeper observable.
	 */
	private IMinesweeperObservable minesweeperObservable;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.nbMinesRemaining.setText(String.valueOf(0));
		this.score.setText(String.valueOf(0));
		this.minesweeperObservable = new RandomMinesweeper(DEFAULT_MINESWEEPER_SIZE, this);

		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/happy_smiley.png"));
		// this.faceButton.setOnAction(e ->
		// {
		// this.minesweeperObservable = new RandomMinesweeper(DEFAULT_MINESWEEPER_SIZE, this);
		// });

		for (int x = 0; x < DEFAULT_MINESWEEPER_SIZE; x++)
		{
			for (int y = 0; y < DEFAULT_MINESWEEPER_SIZE; y++)
			{
				final int xTemp = x;
				final int yTemp = y;

				final Pane imagePane = new Pane();
				imagePane.setOnMouseClicked(e ->
				{
					final MouseButton mouseButton = e.getButton();
					if (mouseButton == MouseButton.PRIMARY)
					{
						this.minesweeperObservable.onLeftMouseClick(xTemp, yTemp);
						return;
					}

					if (mouseButton == MouseButton.SECONDARY)
					{
						this.minesweeperObservable.onRightMouseClick(xTemp, yTemp);
						return;
					}
				});

				this.boardGame.add(imagePane, y, x);
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerIsDead()
	 */
	@Override
	public void playerIsDead()
	{
		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/sad_smiley.png"));
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerHasWon()
	 */
	@Override
	public void playerHasWon()
	{
		this.faceButton.setGraphic(new ImageView("/fr/polytech/demineur/view/resources/sun_glasses_smiley.png"));
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#updateCell(int, int, fr.polytech.demineur.model.Cell)
	 */
	@Override
	public void updateCell(int coordX, int coordY, Cell cell)
	{
		if (cell.getCellType() != CellType.EMPTY)
		{
			for (Node node : this.boardGame.getChildren())
			{
				if ((node instanceof Pane) && (GridPane.getRowIndex(node) == coordX) && (GridPane.getColumnIndex(node) == coordY))
				{
					final Pane imagePane = (Pane) node;

					final ImageView imageView = new ImageView();
					imageView.fitWidthProperty().bind(imagePane.widthProperty());
					imageView.fitHeightProperty().bind(imagePane.heightProperty());

					if (cell.isMarked())
					{
						imageView.setImage(new Image("/fr/polytech/demineur/view/resources/red_flag.png"));
						imagePane.getChildren().add(imageView);
					}
					else
					{
						if (cell.isHidden())
						{
							imagePane.getChildren().clear();
						}
						else
						{
							imageView.setImage(new Image(cell.getCellType().getImagePath()));
							imagePane.getChildren().add(imageView);
						}
					}
				}
			}
		}
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#setNbMinesRemaining(int)
	 */
	@Override
	public void setNbMinesRemaining(int nbMinesRemaining)
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
}