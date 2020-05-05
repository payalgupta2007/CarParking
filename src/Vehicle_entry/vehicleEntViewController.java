/**
 * Sample Skeleton for 'vehicleEntView.fxml' Controller Class
 */

package Vehicle_entry;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connectDB.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class vehicleEntViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rad2"
    private RadioButton rad2; // Value injected by FXMLLoader

    @FXML // fx:id="type"
    private ToggleGroup type; // Value injected by FXMLLoader

    @FXML // fx:id="rad4"
    private RadioButton rad4; // Value injected by FXMLLoader

    @FXML // fx:id="lstA"
    private ListView<String> lstA; // Value injected by FXMLLoader

    @FXML // fx:id="lstB"
    private ListView<Integer> lstB; // Value injected by FXMLLoader

    @FXML // fx:id="radRegular"
    private RadioButton radRegular; // Value injected by FXMLLoader

    @FXML // fx:id="type1"
    private ToggleGroup type1; // Value injected by FXMLLoader

    @FXML // fx:id="typecust"
    private ToggleGroup typecust; // Value injected by FXMLLoader

    @FXML // fx:id="radRandom"
    private RadioButton radRandom; // Value injected by FXMLLoader

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

    @FXML // fx:id="txtVehicle"
    private TextField txtVehicle; // Value injected by FXMLLoader

    
    @FXML // fx:id="lblAlert"
    private Label lblAlert; // Value injected by FXMLLoader

    
    Connection con;
    PreparedStatement pst;
    
    @FXML
    void doRad2(ActionEvent event) {
    	lstA.getItems().clear();
    	lstB.getItems().clear();
    	ArrayList<String> floorAry=new ArrayList<>();
    	ArrayList<Integer> floorAry1=new ArrayList<>();
		try {
			pst=con.prepareStatement("select floor,capacity,occupied from playout where type=?");
			pst.setString(1,"Two");
        	ResultSet table= pst.executeQuery();
        	
        	while(table.next())
    		{ 
    			String floor=table.getString("floor");//col. name acc. to table
    			int capa=table.getInt("capacity");
    			int occu=table.getInt("occupied");
    			floorAry.add(floor);
    			floorAry1.add(capa-occu);
    		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		lstA.getItems().addAll(floorAry);
		lstB.getItems().addAll(floorAry1);
    }

    @FXML
    void doRad4(ActionEvent event) {
    	lstA.getItems().clear();
    	lstB.getItems().clear();
    	ArrayList<String> floorAry=new ArrayList<>();
    	ArrayList<Integer> floorAry1=new ArrayList<>();
		try {
			pst=con.prepareStatement("select floor,capacity,occupied from playout where type=?");
			pst.setString(1,"Four");
        	ResultSet table= pst.executeQuery();
        	
        	while(table.next())
    		{ 
    			String floor=table.getString("floor");//col. name acc. to table
    			int capa=table.getInt("capacity");
    			int occu=table.getInt("occupied");
    			floorAry.add(floor);
    			floorAry1.add(capa-occu);
    		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		lstA.getItems().addAll(floorAry);
		lstB.getItems().addAll(floorAry1);
    }
    @FXML
    void doSave(ActionEvent event)
    {
    	String floor=lstA.getSelectionModel().getSelectedItem();
           String mob=txtmob.getText();
           String vehi=txtVehicle.getText();
           String type;
           if(rad4.isSelected()==true)
        	   type="Four";
           else
        	   type="Two";
           String custype;
           if(radRegular.isSelected()==true)
        	   custype="Regular";
           else
        	   custype="Random";
           try {
			PreparedStatement pre= con.prepareStatement("insert into vehicle_entry values(null,?,?,?,?,1,curtime(),curdate())");
			pre.setString(1, custype);
			pre.setString(2, vehi);
			pre.setString(3,type);
			pre.setString(4, floor);
			pre.executeUpdate();
			lblAlert.setText("Saved");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
           doUpdate(floor,type);
           dosms(floor);
           
   		
    }
    
    void dosms(String floor)
    {
    	System.out.println("******");
       	String resp=SMS.SST_SMS.bceSunSoftSend(txtmob.getText(),"Kindly park your vehicle on floor "+floor );
       	if(resp.contains("successfully"))
   			System.out.println("Sent...");
   	else
   		if(resp.contains("Unknown"))
   			System.out.println("Check Internet connection");
   		else
   			System.out.println("Invalid Mobile Number");
    }
           void doUpdate(String f,String ty)
      {
        	   try {
				PreparedStatement pre= con.prepareStatement("update playout set occupied=occupied+1 where floor=? and type=?");
	               pre.setString(1, f);
	               pre.setString(2, ty);
	               pre.executeUpdate();
	               System.out.println("Playout updated");
        	   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	 con=DbConnection.doConnect();
    	
    	

    }
}
