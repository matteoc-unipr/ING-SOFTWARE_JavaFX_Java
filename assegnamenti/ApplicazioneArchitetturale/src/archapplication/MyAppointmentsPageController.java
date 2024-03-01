package archapplication;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

/**
*
* The class {@code MyAppointmentsPageController} provides a simple controller for the MyAppointments Page.
*
**/
public class MyAppointmentsPageController implements Initializable {
	ObservableList<Appointment> listA;

	int index = -1;
	private User user;
	ConnectSql cs;
	
	/**
	 * Sets the model.
	 *
	 **/
	public void setModel(ConnectSql c)
	{
		this.cs = c;
	}
	
	/**
	 * Sets the user.
	 *
	 **/
	public void setUser(User u)
	{
		this.user = u;
	}
	
	/**
	 * Sets the list of appointments.
	 *
	 **/
	public void setListA(ObservableList<Appointment> listA) {
		this.listA = listA;
	}
	
    @FXML
    private AnchorPane myhomerootpane;
    
    @FXML
    private TableColumn<Appointment, Integer> AppIdCol;

    @FXML
    private TableView<Appointment> MyAppointments;

    @FXML
    private TableColumn<Appointment, String> DateCol;

    @FXML
    private TableColumn<Appointment, String> DocNameCol;

    @FXML
    private TableColumn<Appointment, String> PlaceCol;

    @FXML
    private TableColumn<Appointment, String> TypeCol;

    @FXML
    private TextField dateField;

    @FXML
    private TextField docnameField;

    @FXML
    private Button CancelBtn;
    
    @FXML
    private TextField placeField;

    @FXML
    private TextField typeField;
    
    @FXML
    private TextField appidField;
   
    @FXML
    private Text WarningText;
    

    @FXML
    void Quit() {
    	System.exit(0);
    }
    
    @FXML
    void CancelApp() {
    	if(appidField.getText().equals("")) {
    		WarningText.setText("Select an Appointment first!");
    	}
    	else {
    		cs.RemoveAppPatientID(appidField.getText(), user.getuserID());
    		fillAppointmentsTable(cs.getUserAppointments(user));
    		canceledMsg(appidField.getText());
    	}
    }
    
    @FXML
    void canceledMsg(String appid) {
    	AlertType type = AlertType.INFORMATION;
    	Alert alert = new Alert(type, "");
    	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(myhomerootpane.getScene().getWindow());
    	alert.getDialogPane().setContentText("APPOINTMENT ID: " + appid);
    	alert.getDialogPane().setHeaderText("CANCELED SUCCESSFULLY!");
    	alert.showAndWait();
    	
    }

    @FXML
    public void getAppointment() {
    	index = MyAppointments.getSelectionModel().getSelectedIndex();
    	if (index <= -1) {
    		return;
    	}
    	
    	docnameField.setText(DocNameCol.getCellData(index).toString());
    	dateField.setText(DateCol.getCellData(index).toString());
    	placeField.setText(PlaceCol.getCellData(index).toString());
    	typeField.setText(TypeCol.getCellData(index).toString());
    	appidField.setText(AppIdCol.getCellData(index).toString());
    }

    /**
     * Fills the Appointments table with elements of an observable list.
     * 
     * @param the observable list.
     *
     **/
    public void fillAppointmentsTable(ObservableList<Appointment> l) {
    	MyAppointments.setItems(l);
    }
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		DocNameCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("DocName"));
		DateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("DateTime"));
		PlaceCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Place"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
		AppIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppId"));
		

		
	}


}

