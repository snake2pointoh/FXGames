package engine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import minesweeper.Assets;
import minesweeper.Board;


/**
 * This class is the starting point of the whole program
 * @author nick
 *
 */
public class Main extends Application
{
	public static double SCREEN_SIZE_X = 1000;
	public static double SCREEN_SIZE_Y = 1000;
	
	
	@Override
	public void start(Stage mainStage) throws Exception
	{
		Group mainGroup = new Group();
		addTests(mainGroup);
		
		Scene mainScene = new Scene(mainGroup, SCREEN_SIZE_X, SCREEN_SIZE_Y);
		
		mainStage.setScene(mainScene);
		mainStage.getIcons().add(Assets.BOMB_IMG);
		mainStage.setResizable(false);
		mainStage.setTitle("Minesweeper");
		mainStage.show();
	}
	
	/**
	 * A group of tests used to validate graphical functionality
	 * @param obGroup
	 */
	public static void addTests(Group obGroup)
	{	

		Board testBoard = new Board(0, 0, SCREEN_SIZE_X, 10);
		obGroup.getChildren().add(testBoard);
		
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}


}
