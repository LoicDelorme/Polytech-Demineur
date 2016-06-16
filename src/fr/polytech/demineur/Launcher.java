package fr.polytech.demineur;

import fr.polytech.demineur.controller.MinesweeperController;
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
			final FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fr/polytech/demineur/view/Minesweeper.fxml"));
			final BorderPane root = (BorderPane) fxmlLoader.load();
			final Scene scene = new Scene(root);

			final MinesweeperController controller = fxmlLoader.getController();
			controller.setPrimaryStage(primaryStage);

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