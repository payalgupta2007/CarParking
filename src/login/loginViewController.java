/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */

package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import connectDB.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class loginViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtid"
    private TextField txtid; // Value injected by FXMLLoader

    @FXML // fx:id="txtpwd"
    private PasswordField txtpwd; // Value injected by FXMLLoader
    Connection con;
    
    @FXML
    void doLogin(ActionEvent event)
    {
            String id= txtid.getText();
            String pwd=txtpwd.getText();
            try {
         		
     			PreparedStatement pre= con.prepareStatement("Select * from admintbl");
     		String id2,pwd2;	
     		ResultSet table= pre.executeQuery();
     		boolean jasus=false;
     		while(table.next())
     		{
     			jasus=true;
     			id2=table.getString("Id");
     			pwd2=table.getString("password");
     			
     			if(id.equals(id2)==true && pwd.equals(pwd2))
         		{
     				Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/dashboardView.fxml")); 
    			Scene scene = new Scene(root,706,661);
    			
    			Stage primaryStage=new Stage();
    			primaryStage.setScene(scene);
    			primaryStage.show();
    			
    			//to hide the opened window
    			 
    			   Scene scene1=(Scene)txtid.getScene();
    			   scene1.getWindow().hide();
         		}
     			else
     			{
     				
     				Alert a1=new Alert(AlertType.ERROR);
     	        	a1.setTitle("Error");
     	        	a1.setHeaderText("Wrong User Id Or Password");
     	        	a1.showAndWait(); 
     			}
     		
     		}
     		
            } catch (Exception e) 
         	{
     			e.printStackTrace();
     		}
            
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize()
    {
    	con=DbConnection.doConnect();
    }
}
