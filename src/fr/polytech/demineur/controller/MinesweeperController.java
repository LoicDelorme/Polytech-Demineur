package fr.polytech.demineur.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.polytech.demineur.model.Cell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
	 * The number of mines remaining.
	 */
	@FXML
	private Text nbMinesRemaining;

	/**
	 * The current status.
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
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerIsDead()
	 */
	@Override
	public void playerIsDead()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#playerHasWon()
	 */
	@Override
	public void playerHasWon()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#updateCell(int, int, fr.polytech.demineur.model.Cell)
	 */
	@Override
	public void updateCell(int coordX, int coordY, Cell cell)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#setNbMinesRemaining(int)
	 */
	@Override
	public void setNbMinesRemaining(int nbMinesRemaining)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see fr.polytech.demineur.controller.IMinesweeperObserver#setScore(int)
	 */
	@Override
	public void setScore(int score)
	{
		// TODO Auto-generated method stub

	}
}