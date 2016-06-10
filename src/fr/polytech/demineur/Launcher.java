package fr.polytech.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class represents the launcher of the application.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Launcher extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			final BorderPane root = (BorderPane) FXMLLoader.load(this.getClass().getResource("/fr/polytech/demineur/view/Minesweeper.fxml"));
			final Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * The entry of the application.
	 * 
	 * @param args
	 *            Some arguments.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}