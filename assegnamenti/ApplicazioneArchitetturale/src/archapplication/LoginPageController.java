package archapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import archapplication.HomePageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* The class {@code LoginPageController} provides a simple controller for the Login Page.
*
**/
public class LoginPageController implements Initializable {

	User l;
	private ConnectSql cs;
	private Stage os;
	
	/**
	 * Sets the model.
	 *
	 **/
	public void setModel(ConnectSql c)
	{
		this.cs = c;
	}
	
	/**
	 * Sets the old stage.
	 *
	 **/
	public void setOldStage(Stage s)
	{
		this.os = s;
	}

	@FXML
    private ImageView exitbtn;

    @FXML
    private Button logBtn;

    @FXML
    private Text msgText;

    @FXML
    private PasswordField pswField;

    @FXML
    private TextField idField;
    
    @FXML
    private Text signupBtn;

    
    @FXML
    void Quit() {
    	System.exit(0);
    }
    
    
    @FXML
    void tryLogin() throws IOException {

    	if((idField.getText().isEmpty()) || (pswField.getText().isEmpty()))
    	{
    		msgText.setText("Please enter your data!");
    	}
    	else if(!(isOnlyInt(idField.getText())) || idField.getText().length()!=4)
    	{
    		msgText.setText("Please insert a valid ID (4 ciphers)!");
    	}
    	else
    	{
    		int id= Integer.parseInt(idField.getText());
    		String psw = pswField.getText();
    		User l = cs.getUser(id);
    		
    		if(pswField.getText().equals(l.getuserPsw())) {
    			Stage stage=new Stage(); 
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
    			AnchorPane pane= loader.load(); 
    			Scene scene = new Scene(pane,700,450);
    			HomePageController homepagecontroller = loader.getController();
    			homepagecontroller.setModel(cs);
    			homepagecontroller.setUser(l);
    			ObservableList<Appointment> fa = cs.getFreeAppointments();
    			homepagecontroller.setListA(fa);
    			homepagecontroller.fillAppointmentsTable(fa);
    			homepagecontroller.fillUserDetails();				
    			stage.setScene(scene);
    			stage.setTitle("HomePage");
    			stage.setResizable(false);
    			stage.show();
    			os.close();
    		}
    		else 
    		{
    			msgText.setText("Wrong password!");
    		}
    	}
    }
    
    @FXML
    void openSignUpPage() throws IOException {
    	Stage stage=new Stage(); 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpPage.fxml"));
		AnchorPane pane= loader.load(); 
		Scene scene = new Scene(pane,300,400);
		SignUpPageController supc = loader.getController();
		supc.setModel(cs);
		stage.setScene(scene);
		stage.setTitle("Sign Up");
		stage.setResizable(false);
		supc.setOldStage(stage);
		stage.show();
    }
    
	/**
	 * Verify if a string is formed only of int.
	 *
	 * @param the string.
	 **/
    private static boolean isOnlyInt(String str) {
    	return str.matches("\\d+");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    
}
