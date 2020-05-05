/**
 * Sample Skeleton for 'exitView.fxml' Controller Class
 */

package vehicle_exit;

import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import connectDB.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class exitViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtVehicle"
    private TextField txtVehicle; // Value injected by FXMLLoader

    @FXML // fx:id="txtFloor"
    private TextField txtFloor; // Value injected by FXMLLoader

    @FXML // fx:id="entDate"
    private TextField entDate; // Value injected by FXMLLoader

    @FXML // fx:id="entTime"
    private TextField entTime; // Value injected by FXMLLoader

    @FXML // fx:id="txtType"
    private TextField txtType; // Value injected by FXMLLoader

    @FXML // fx:id="txtCurDate"
    private TextField txtCurDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtCurTime"
    private TextField txtCurTime; // Value injected by FXMLLoader

    @FXML // fx:id="lblAmount"
    private Label lblAmount; // Value injected by FXMLLoader
    
    @FXML // fx:id="lblAlert"
    private Label lblAlert; // Value injected by FXMLLoader
    
    Connection con;
    String cutype;
    @FXML
    void doBill(ActionEvent event) 
    {
     
    	String start=entDate.getText()+" "+entTime.getText();
    	System.out.println(start);
    	String end=txtCurDate.getText()+" "+txtCurTime.getText();
    	System.out.println(end);
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(start);
			
			d2 = format.parse(end);
			
			long diff = d2.getTime() - d1.getTime();
			
			
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			
			double bill=0,disc;
			if(txtType.getText().equals("Two"))
			{
				bill=50*diffDays+5*diffHours+0.5*diffMinutes;
			
				if(cutype.equals("Regular"))
				{
					disc=20*bill/100;
					bill=bill-disc;
					System.out.println(cutype);
					lblAmount.setText(bill+"/-");
				}
				else if(cutype.equals("Random"))
					lblAmount.setText(bill+"/-");
				
			}
			else if(txtType.getText().equals("Four"))
			{
				bill=100*diffDays+7*diffHours+1*diffMinutes;
			
				if(cutype.equals("Regular"))
				{
					disc=20*bill/100;
					bill=bill-disc;
					System.out.println(cutype);
					lblAmount.setText(bill+"/-");
				}
				else if(cutype.equals("Random"))
					lblAmount.setText(bill+"/-");
				
			}

		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
    }
    

    @FXML
    void doClose(ActionEvent event) {
    System.exit(1);
    }
    PreparedStatement pre;
    @FXML
    void doFetch(ActionEvent event)
    {     String vehi=txtVehicle.getText();
    
    	
		try {
			pre = con.prepareStatement("Select * from vehicle_entry where vno=?");
			 pre.setString(1,vehi);
				ResultSet table= pre.executeQuery();
				boolean jasus=false;
				while(table.next())
				{
		            jasus=true; 
		            String floor=table.getString("floor");
		 			String type=table.getString("type");
		 			String Edate=table.getString("entDate");
		 			String Etime=table.getString("entTime");
		 			cutype=table.getString("ctype");
		 			
		 			txtFloor.setText(floor);
		 			txtType.setText(type);
		 			entTime.setText(Etime);
		 			entDate.setText(Edate);
				}
				if(jasus==false)
				{
					lblAlert.setText("Invalid Record");
				}
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	   
           doSaveDate();
    }
   void doSaveDate()
   {
	   Date d=new Date();
	   SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd");
	   txtCurDate.setText(ft.format(d));
	   Calendar cal=Calendar.getInstance();
		Date dat=cal.getTime();
		DateFormat fot=new SimpleDateFormat("HH:mm:ss");
			txtCurTime.setText(fot.format(dat));	
	}
	   
   
    @FXML
    void doNew(ActionEvent event) {
    	txtVehicle.setText("");
    	txtFloor.setText("");
    	entDate.setText("");
    	entTime.setText("");
    	txtType.setText("");
    	txtCurDate.setText("");
    	txtCurTime.setText("");
    	lblAmount.setText("______________");
    }

    @FXML
    void doUpdate(ActionEvent event)
    {
    	String f=txtFloor.getText();
    	String typ=txtType.getText();
    	try {
			PreparedStatement pre= con.prepareStatement("update playout set occupied=occupied-1 where floor=? and type=?");
			pre.setString(1,f);
			pre.setString(2, typ);
			pre.executeUpdate();
			System.out.println("Playout updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String vehicle=txtVehicle.getText();
    	try {
			PreparedStatement pre= con.prepareStatement("update vehicle_entry set status=0 where vno=?");
			pre.setString(1,vehicle);
			pre.executeUpdate();
			System.out.println("Vehicle entry updated");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	  con=DbConnection.doConnect();

    }
}
