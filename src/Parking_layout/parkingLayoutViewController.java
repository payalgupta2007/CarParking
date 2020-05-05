/**
 * Sample Skeleton for 'parkingLayoutView.fxml' Controller Class
 */

package Parking_layout;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectDB.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class parkingLayoutViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCombo"
    private ComboBox<String> txtCombo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCapacity"
    private TextField txtCapacity; // Value injected by FXMLLoader

    @FXML // fx:id="rad2"
    private RadioButton rad2; // Value injected by FXMLLoader

    @FXML // fx:id="type"
    private ToggleGroup type; // Value injected by FXMLLoader

    @FXML // fx:id="rad4"
    private RadioButton rad4; // Value injected by FXMLLoader
    
    @FXML // fx:id="lblAlert"
    private Label lblAlert; // Value injected by FXMLLoader
    Connection con;
   
    
    @FXML
    void doClose(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void doSave(ActionEvent event)
    {
    	String type;
       	String floor=txtCombo.getSelectionModel().getSelectedItem();
       	String capa=txtCapacity.getText();
       	if(rad2.isSelected()==true)
       	  type="Two";
       	else
       		type="Four";
       	try {
			PreparedStatement pre= con.prepareStatement("insert into playout values(?,?,?,0)");
			pre.setString(1,floor);
		    pre.setInt(2,Integer.parseInt(capa));
		    pre.setString(3, type);
		   
		    pre.executeUpdate();
		    lblAlert.setText("Saved");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	 con=DbConnection.doConnect();
    	String floor[]={"1","2","3","4","5"};
        txtCombo.getItems().addAll(floor);
         
    }
}
