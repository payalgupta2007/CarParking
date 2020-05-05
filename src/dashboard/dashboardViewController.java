/**
 * Sample Skeleton for 'dashboardView.fxml' Controller Class
 */

package dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import connectDB.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class dashboardViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblwlcm1"
    private Label lblwlcm1; // Value injected by FXMLLoader
    Connection con;
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
    void setlbl()
    {
    	  try {
   			PreparedStatement pre= con.prepareStatement("Select * from admintbl");
   		ResultSet table= pre.executeQuery();
   		boolean jasus=false;
   		while(table.next())
   		{
   			jasus=true;
   		String id2=table.getString("Id");
   			lblwlcm1.setText(id2);
   		}
    	  } catch (Exception e) 
       	{
   			e.printStackTrace();
   		}
    }
    
    @FXML
    void custLog(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("table_customers/tabCustView.fxml"));
			Scene scene = new Scene(root,600,600);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Customer Logs");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    void playSound(){
       	url=getClass().getResource("Button-SoundBible.com-1420500901.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }
    @FXML
    void custreg(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/CustomerRegView.fxml"));
			Scene scene = new Scene(root,600,600);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Customer Registration");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void parklayout(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Parking_layout/parkingLayoutView.fxml"));
			Scene scene = new Scene(root,500,500);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Parking Layout");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void vehicleEnt(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Vehicle_entry/vehicleEntView.fxml"));
			Scene scene = new Scene(root,600,600);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Vehicle Entry");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void vehicleExit(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("vehicle_exit/exitView.fxml"));
			Scene scene = new Scene(root,600,600);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Vehicle Exit");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
    }

    @FXML
    void vehicleLog(MouseEvent event) {
    	playSound();
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("table_vehicleEnt/vehicleTabView.fxml"));
			Scene scene = new Scene(root,700,600);
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Vehicle Logs");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
      con=DbConnection.doConnect();
       setlbl();
    }
}
