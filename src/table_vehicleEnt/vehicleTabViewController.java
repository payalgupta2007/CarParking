/**
 * Sample Skeleton for 'vehicleTabView.fxml' Controller Class
 */

package table_vehicleEnt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import connectDB.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import table_vehicleEnt.vehicleEntBean;

public class vehicleTabViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tbl"
    private TableView<vehicleEntBean> tbl; // Value injected by FXMLLoader

    @FXML // fx:id="comboVno"
    private ComboBox<String> comboVno; // Value injected by FXMLLoader

    @FXML // fx:id="datepick1"
    private DatePicker datepick1; // Value injected by FXMLLoader

    @FXML // fx:id="datepick2"
    private DatePicker datepick2; // Value injected by FXMLLoader
    Connection con;
    @FXML
    void doExcel(ActionEvent event) 
    {
             try {
				writeExcel();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
         	
    	
    }
    
    public void writeExcel() throws Exception
    {
        Writer writer = null;
        try {
        	FileChooser chooser=new FileChooser();
	    	
        	chooser.setTitle("Select Path:");
        	
        	chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                    
                );
        	 File file=chooser.showSaveDialog(null);
        	String filePath=file.getAbsolutePath();
        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
        	{
        		filePath=filePath+".csv";
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Vehicle no.,Vehicle type,Customer Type,Floor,entryTime,entryDate\n";
            writer.write(text);
            for (vehicleEntBean p : list)
            {
				text = p.getVno()+ "," + p.getVtype()+ "," + p.getCtype()+ "," + p.getFloor()+","+p.getEntTime()+","+p.getEntDate()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        }
    }
    void filltable()
    {
    TableColumn<vehicleEntBean, String> ctype=new TableColumn<vehicleEntBean, String>("Customer Type");//Dikhava Title
	ctype.setCellValueFactory(new PropertyValueFactory<>("ctype"));//bean field name, no link with table col name

	TableColumn<vehicleEntBean, String> vno=new TableColumn<vehicleEntBean, String>("Vehicle no.");//Dikhava Title
	vno.setCellValueFactory(new PropertyValueFactory<>("vno"));//bean field name, no link with table col name
	
	TableColumn<vehicleEntBean, String> vtype=new TableColumn<vehicleEntBean, String>("Vehicle Type");//Dikhava Title
	vtype.setCellValueFactory(new PropertyValueFactory<>("vtype"));//bean field name, no link with table col name

	TableColumn<vehicleEntBean, String> floor=new TableColumn<vehicleEntBean, String>("floor");//Dikhava Title
	floor.setCellValueFactory(new PropertyValueFactory<>("floor"));//bean field name, no link with table col name
	
	TableColumn<vehicleEntBean, String> entTime=new TableColumn<vehicleEntBean, String>("Entry Time");//Dikhava Title
	entTime.setCellValueFactory(new PropertyValueFactory<>("entTime"));//bean field name, no link with table col name

	
	TableColumn<vehicleEntBean, String> entDate=new TableColumn<vehicleEntBean, String>("Entry Date");//Dikhava Title
	entDate.setCellValueFactory(new PropertyValueFactory<>("entDate"));//bean field name, no link with table col name
	
	tbl.getColumns().clear();
	tbl.getColumns().addAll(ctype,vno,vtype,floor,entTime,entDate);
    }
    ObservableList<vehicleEntBean> list;
    void fetchAll(PreparedStatement pst)
    {
    	list=FXCollections.observableArrayList();
    	try{
         
        	ResultSet table= pst.executeQuery();
        		boolean jasus=false;
        		while(table.next())
        		{ jasus=true;
        			String ctype=table.getString("ctype");//col. name acc. to table
        			String vno=table.getString("vno");
        			String vtype=table.getString("type");
        			String floor=table.getString("floor");
        			String entTime=table.getString("entTime");
        			String entDate=table.getString("entDate");
        			
        			System.out.println(ctype+"  "+vno+"  "+vtype+"   "+floor+" "+entTime+" "+entDate);
        			vehicleEntBean sb=new vehicleEntBean(vno,vtype,ctype,floor,entTime,entDate);
        			list.add(sb);
        			
        		}
        		if(jasus==false)
        		{
        			Alert a1=new Alert(AlertType.ERROR);
                	a1.setTitle("Error");
                	a1.setHeaderText("Invalid Record");
                	a1.showAndWait(); 
        		}
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    	
    	
    }
    @FXML
    void doSearch1(ActionEvent event) {
    	filltable();
    	String vn=comboVno.getSelectionModel().getSelectedItem();
    	try {
			PreparedStatement pst=con.prepareStatement("select * from vehicle_entry where vno=?");
			pst.setString(1,vn);
			fetchAll(pst);
	    	tbl.setItems(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void doSearchAll(ActionEvent event) {
    	filltable();
    	try {
			PreparedStatement pst=con.prepareStatement("select * from vehicle_entry");
			fetchAll(pst);
	    	tbl.setItems(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    
    }

    @FXML
    void doSelected(ActionEvent event)
    {
      
    	
    }
    void fillRolls()
    {
    	ArrayList<String> rollsAry=new ArrayList<>();
    	
    	try{
   PreparedStatement pst=con.prepareStatement("select vno from vehicle_entry ");
         
   ResultSet table= pst.executeQuery();
        		
        		while(table.next())
        		{
        			String rollno=table.getString("vno");//vcol. name acc. to table
        			rollsAry.add(String.valueOf(rollno));
        			
        		}
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    	comboVno.getItems().addAll(rollsAry);
    }

    @FXML
    void dosearch2(ActionEvent event)
    {
    	LocalDate d=datepick1.getValue();
    	java.sql.Date start=java.sql.Date.valueOf(d);
    	 SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd");
    	 String start1=ft.format(start);
    	 
    	 LocalDate d2=datepick2.getValue();
     	java.sql.Date end=java.sql.Date.valueOf(d2);
     	 SimpleDateFormat fot=new SimpleDateFormat("yyyy-MM-dd");
     	 String end2=ft.format(end);
     	 
     	filltable();
    	
    	try {
			PreparedStatement pst=con.prepareStatement("select * from vehicle_entry where entDate between ? and ?");
			pst.setString(1,start1);
			pst.setString(2, end2);
			fetchAll(pst);
	    	tbl.setItems(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
       con=DbConnection.doConnect();
         fillRolls();
    }
}
