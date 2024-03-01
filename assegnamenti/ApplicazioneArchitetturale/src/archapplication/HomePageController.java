package archapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
*
* The class {@code HomePageController} provides a simple controller for the Home Page.
*
**/
public class HomePageController implements Initializable {
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
	private TableView<Appointment> Appointments;

	@FXML
	private TableColumn<Appointment, String> DateCol;

	@FXML
	private TableColumn<Appointment, String> DocNameCol;

	@FXML
	private AnchorPane HomeRootPane;

	@FXML
	private TableColumn<Appointment, Integer> AppIdCol;

	@FXML
	private TableColumn<Appointment, String> PlaceCol;

	@FXML
	private TableColumn<Appointment, String> TypeCol;
    
    @FXML
    private TextField dateField;

    @FXML
    private TextField docnameField;

	@FXML
    private ImageView exitbtn;

    @FXML
    private TextField placeField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField appidField;
    
    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField PatIDField;
    
    @FXML
    private Text WarningText;
    
    @FXML
    private Button toMyAppPage;

    @FXML
    private Button refreshBtn;

    @FXML
    void Quit() {
    	System.exit(0);
    }

    @FXML
    void refreshTable() {
    	fillAppointmentsTable(cs.getFreeAppointments());
    }
	
    @FXML
    public void getAppointment() {
    	index = Appointments.getSelectionModel().getSelectedIndex();
    	if (index <= -1) {
    		return;
    	}
    	
    	docnameField.setText(DocNameCol.getCellData(index).toString());
    	dateField.setText(DateCol.getCellData(index).toString());
    	placeField.setText(PlaceCol.getCellData(index).toString());
    	typeField.setText(TypeCol.getCellData(index).toString());
    	appidField.setText(AppIdCol.getCellData(index).toString());
    }
    

    @FXML
    void bookApp() {
    	if(appidField.getText().equals("")) {
    		WarningText.setText("Select an Appointment first!");
    	}
    	else {
    		cs.setAppPatientID(appidField.getText(), user.getuserID());
    		fillAppointmentsTable(cs.getFreeAppointments());
    		bookedMsg(appidField.getText());
    	}
    }
    
    @FXML
    void bookedMsg(String appid) {
    	AlertType type = AlertType.INFORMATION;
    	Alert alert = new Alert(type, "");
    	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(HomeRootPane.getScene().getWindow());
    	alert.getDialogPane().setContentText("APPOINTMENT ID: " + appid);
    	alert.getDialogPane().setHeaderText("BOOKED SUCCESSFULLY!");
    	alert.showAndWait();
    	
    }
    
    @FXML
    void openMyAppPage() throws IOException {
		Stage stage=new Stage(); 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAppointmentsPage.fxml"));
		AnchorPane pane= loader.load(); 
		Scene scene = new Scene(pane,600,350);
		MyAppointmentsPageController myapppagepagecontroller = loader.getController();
		myapppagepagecontroller.setModel(cs);
		myapppagepagecontroller.setUser(user);
		ObservableList<Appointment> fa = cs.getUserAppointments(user);
		myapppagepagecontroller.setListA(fa);
		myapppagepagecontroller.fillAppointmentsTable(fa);
		stage.setScene(scene);
		stage.setTitle("Your Appointments");
		stage.setResizable(false);
		stage.show();
				

    }

    /**
     * Fills the Appointments table with elements of an observable list.
     * 
     * @param the observable list.
     *
     **/
    public void fillAppointmentsTable(ObservableList<Appointment> l) {
    	Appointments.setItems(l);
    }
    
	/**
	 * Fills some fields with user details.
	 *
	 **/
    public void fillUserDetails() {					
    	FirstNameField.setText(user.getFirstName());
    	LastNameField.setText(user.getLastName());
    	PatIDField.setText(Integer.toString(user.getuserID()));
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
