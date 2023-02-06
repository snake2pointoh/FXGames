package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppTests extends Application
{

	@Override
	public void start(Stage obStage) throws Exception
	{
		
		Group obGroup = new Group();
//		Button obButton = new Button("Button");
//		obButton.setMaxSize(100, 100);
//		obButton.setMinSize(80, 80);
		
		Text text = new Text(50,100,"Test Text");
		text.setX(50);
		text.setY(200);
		
//		obGroup.getChildren().add(obButton);
//		obGroup.getChildren().add(text);
		
		Scene scene = new Scene(obGroup,300,300);
		obStage.setScene(scene);
		obStage.show();
	}

	public static void main(String[] args)
	{
		Application.launch(args);

	}


}
