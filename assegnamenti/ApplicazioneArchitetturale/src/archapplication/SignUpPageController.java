package archapplication;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
*
* The class {@code SignUpPageController} provides a simple controller for the SignUp Page.
*
**/
public class SignUpPageController implements Initializable {
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
    private TextField FNameField;

    @FXML
    private TextField LNameField;

    @FXML
    private Button logBtn;

    @FXML
    private Text msgText;

    @FXML
    private PasswordField pswField;
    
    @FXML
    private AnchorPane signuppane;

    @FXML
    void Quit() {
    	System.exit(0);
    }

    @FXML
    void trySignUp() throws SQLException {
    	if((FNameField.getText().isEmpty()) || (LNameField.getText().isEmpty()) || (pswField.getText().isEmpty())) 
    	{
    		msgText.setText("Please fill all fields!");
    	}
    	else 
    	{
    		int newId = cs.createUser(FNameField.getText(), LNameField.getText(), pswField.getText());
    		
        	AlertType type = AlertType.INFORMATION;
        	Alert alert = new Alert(type, "");
        	
        	alert.initModality(Modality.APPLICATION_MODAL);
        	alert.getDialogPane().setContentText("REMEMBER THIS ID TO LOG IN! ");
        	alert.getDialogPane().setHeaderText("YOUR NEW ID: "+ newId);
        	alert.showAndWait();
        	os.close();
    	}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
}
