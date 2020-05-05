/**
 * Sample Skeleton for 'tabCustView.fxml' Controller Class
 */

package table_customers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import connectDB.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import table_vehicleEnt.vehicleEntBean;

public class tabCustViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader
    
    @FXML // fx:id="tbl"
    private TableView<CustBean> tbl; // Value injected by FXMLLoader
      Connection con;
    @FXML
    void doFetch(ActionEvent event) {
    	filltable();
    	try {
			PreparedStatement pst=con.prepareStatement("select * from customers");
			fetchAll(pst);
	    	tbl.setItems(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	 
    }
    
    @FXML
    void doExcel(ActionEvent event)
    {
                 try {
					writeExcel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
    public void writeExcel() throws Exception {
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
            String text="Name,Mobile no.,Address,City,Gender\n";
            writer.write(text);
            for (CustBean p : list)
            {
				text = p.getName()+ "," + p.getMob()+ "," + p.getAddress()+ "," + p.getCity()+","+p.getGender()+"\n";
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
    @FXML
    void doSearch(ActionEvent event) {
    	filltable();
    	try {
    		String mob=txtmob.getText();
			PreparedStatement pst=con.prepareStatement("select * from customers where Mno=?");
			pst.setString(1, mob);
			fetchAll(pst);
	    	tbl.setItems(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    void filltable()
    {
    	TableColumn<CustBean, String> mob=new TableColumn<CustBean, String>("Mobile Number");//Dikhava Title
    	mob.setCellValueFactory(new PropertyValueFactory<>("mob"));//bean field name, no link with table col name

    	TableColumn<CustBean, String> name=new TableColumn<CustBean, String>("Name");//Dikhava Title
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name
    	
    	TableColumn<CustBean, String> address=new TableColumn<CustBean, String>("Address");//Dikhava Title
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));//bean field name, no link with table col name

    	TableColumn<CustBean, String> city=new TableColumn<CustBean, String>("City");//Dikhava Title
    	city.setCellValueFactory(new PropertyValueFactory<>("city"));//bean field name, no link with table col name
    	
    	TableColumn<CustBean, String> gender=new TableColumn<CustBean, String>("Gender");//Dikhava Title
    	gender.setCellValueFactory(new PropertyValueFactory<>("gender"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
    	tbl.getColumns().addAll(mob,name,address,city,gender);
    }
    
    ObservableList<CustBean> list;
    void fetchAll(PreparedStatement pst)
    {
    	list=FXCollections.observableArrayList();
    	try{
         
        	ResultSet table= pst.executeQuery();
        		boolean jasus=false;
        		while(table.next())
        		{ jasus=true;
        			String mob=table.getString("Mno");//col. name acc. to table
        			String name=table.getString("Name");
        			String address=table.getString("Add");
        			String city=table.getString("City");
        			String gender=table.getString("Gender");
        			
        			System.out.println(mob+"  "+name+"  "+address+"   "+city+" "+gender);
        			CustBean sb=new CustBean(name,mob,address,city,gender);
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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	 con=DbConnection.doConnect();
    	
    }
}
