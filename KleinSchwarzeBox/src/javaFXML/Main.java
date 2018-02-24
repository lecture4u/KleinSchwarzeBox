package javaFXML;
	
//import java.io.IOException;

import javafx.application.Application;
//import javafx.stage.Screen;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
//import javafx.geometry.Rectangle2D;


public class Main extends Application {
	FXMLLoader loader;
	@Override
	public void start(Stage primaryStage) throws Exception{
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ScrollSample.fxml"));
		Parent root;
		
		root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("AI & IA lab, Dankook university, Blockchain tutorial program");
		SampleController controller = loader.getController();
		controller.setStage(primaryStage);
		
		
		
	} 
	
	public static void main(String[] args) {
		launch(args);
	}
}
