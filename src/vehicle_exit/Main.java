package vehicle_exit;

import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("exitView.fxml"));
			Scene scene=new Scene(root,600,600);
	
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Vehicle Exit");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
