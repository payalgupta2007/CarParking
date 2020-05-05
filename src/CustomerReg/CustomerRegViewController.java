/**
 * Sample Skeleton for 'CustomerRegView.fxml' Controller Class
 */

package CustomerReg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import connectDB.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CustomerRegViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

    @FXML // fx:id="txtname"
    private TextField txtname; // Value injected by FXMLLoader

    @FXML // fx:id="txtcity"
    private TextField txtcity; // Value injected by FXMLLoader

    @FXML // fx:id="txtadd"
    private TextArea txtadd; // Value injected by FXMLLoader


    @FXML // fx:id="imgview1"
    private ImageView imgview1; // Value injected by FXMLLoader

    @FXML // fx:id="radMale"
    private RadioButton radMale; // Value injected by FXMLLoader

    @FXML // fx:id="gender"
    private ToggleGroup gender; // Value injected by FXMLLoader

    @FXML // fx:id="radFemale"
    private RadioButton radFemale; // Value injected by FXMLLoader
    
    @FXML
    private Label lblAlert;
    
    @FXML // fx:id="btnbrowse"
    private Button btnbrowse; // Value injected by FXMLLoader
    
    @FXML // fx:id="lblAlert2"
    private Label lblAlert2; // Value injected by FXMLLoader
    
    Connection con;
    String imgpath=null;
    File f;
    InputStream fin;
    FileOutputStream fos;
    
    int len;
    @FXML
    void doBrowse(ActionEvent event)
    { 
    	FileChooser chooser=new FileChooser();
    	chooser.setTitle("Choose Customer's Pic");
    	chooser.getExtensionFilters().addAll(new ExtensionFilter("Image files","*.png","*.jpg","*.gif"));
       f=chooser.showOpenDialog(new Stage());
        
        if(f!=null)
        {
           String path;
		try {
			fin=new FileInputStream(f);
			len=(int)f.length();
			imgpath=f.getAbsolutePath();
			path = f.toURI().toURL().toString();
		    Image img=new Image(path);
           imgview1.setImage(img);
           imgview1.setFitHeight(135);
           imgview1.setFitWidth(134);} 
           catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} 

        }
        else
        {
        	Alert a1=new Alert(AlertType.ERROR);
        	a1.setTitle("Error");
        	a1.setHeaderText("Select an Image");
        	a1.showAndWait(); 
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
    	con=DbConnection.doConnect();
     	try {
     		String mob=txtmob.getText();
 			PreparedStatement pre= con.prepareStatement("Select * from customers where Mno=?");
 			pre.setString(1, mob);
 		ResultSet table= pre.executeQuery();
 		boolean jasus=false;
 		while(table.next())
 		{
            jasus=true;
            fos=new FileOutputStream("copy.jpg");
            Blob blob=table.getBlob("Pic");
            int len=(int)blob.length();
            byte[] buf=blob.getBytes(1, len);
            fos.write(buf,0,len);
            Image imgcpy=new Image("file:copy.jpg");
            imgview1.setImage(imgcpy);
            imgview1.setFitHeight(135);
            imgview1.setFitWidth(134);
            
 			String name=table.getString("Name");
 			String add=table.getString("add");
 			String city=table.getString("City");
 			String gender=table.getString("Gender");
 			txtname.setText(name);
 			txtadd.setText(add);
 			txtcity.setText(city);
                if(gender.equals("Male"))
                radMale.setSelected(true);
                else
                	radFemale.setSelected(true);
 		}
 		if(jasus==false)
 			lblAlert.setText("Invalid Record");
 		} catch (Exception e) 
     	{
 			e.printStackTrace();
 		}
    }

    @FXML
    void doNew(ActionEvent event) {
    	txtmob.setText("");
  	  txtname.setText("");
  	  txtcity.setText("");
  	  txtadd.setText("");
  	 radMale.setSelected(false);
  	 radFemale.setSelected(false);
  	 imgview1.setImage(null);
  	 lblAlert.setText(" ");
    }

    @FXML
    void doSave(ActionEvent event) {
    	
    	String mob,name,city,add,gender="Male";
    	
    	if(txtmob.getText().equals("")==false && txtname.getText().equals("")==false && txtadd.getText().equals("")==false && txtcity.getText().equals("")==false && (radMale.isSelected()==true|| radFemale.isSelected()==true) && imgpath!=null)
    	{
    	 mob=txtmob.getText();
    	 name=txtname.getText();
    	 city=txtcity.getText();
    	 add=txtadd.getText();
    	
    	 
    	if(radMale.isSelected()==true)
    				gender="Male";
    			else if(radFemale.isSelected()==true)
    				gender="Female";
    	try {
			PreparedStatement pre= con.prepareStatement("insert into customers values(?,?,?,?,?,?)");
//			InputStream is=new FileInputStream(new File(imgpath));
			pre.setString(1,mob);
		    pre.setString(2, name);
		    pre.setString(3, add);
		    pre.setString(4, city);
		    pre.setBinaryStream(5,(InputStream)fin,len);
		    pre.setString(6,gender);
		
		pre.executeUpdate();

		lblAlert.setText("Saved");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	 
    	}
    	else
    	{
    	      lblAlert.setText("All fields are compulsory");	
    	}
    }

    @FXML
    void doUpdate(ActionEvent event)
    {
    	String mob=txtmob.getText();
    	String name=txtname.getText();
    	String city=txtcity.getText();
    	String add=txtadd.getText();
    	String type;
    	if(radMale.isSelected()==true)
    	{
    		type="Male";
    	}
    	else
    	{
    		type="Female";
    	}
    	try {
			PreparedStatement pre= con.prepareStatement("update `customers` set `Add`=?,Name=?,Gender=?,City=? where `Mno`=?");
			pre.setString(5, mob);
		pre.setString(2, name);
			pre.setString(1, add);
		
			pre.setString(4,city);
		pre.setString(3, type);
			
			pre.executeUpdate();
			
			lblAlert.setText("Updated");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	  con=DbConnection.doConnect();
    	 
    }
}
