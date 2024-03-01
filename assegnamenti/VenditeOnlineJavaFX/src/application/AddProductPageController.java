package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
*
* The class {@code AddProductPageController} defines a javafx controller for the fxml file AddProductPage.fxml.
*
**/
public class AddProductPageController implements Initializable{

	private ObjectClient client;
	private User user;
	
    /**
     * Set the client.
     *
     * @args ObjectClient client.
     *
    **/
	public void setClient(ObjectClient c)
	{
		client = c;
	}

    /**
     * Set the User.
     *
     * @args the User.
     *
    **/
	public void setUser(User u)
	{
		user = u;
	}
	
    @FXML
    private TextField ANameField;

    @FXML
    private TextField APriceField;
    
    @FXML
    private Spinner<Integer> AQtyField;

    @FXML
    private Button AddBtn;

    @FXML
    private AnchorPane addpagepane;
    
    @FXML
    private Text txtadd;

    @FXML
    void addProduct(MouseEvent event) throws ClassNotFoundException, IOException {
    	Product p = new Product(ANameField.getText(),APriceField.getText(), "",AQtyField.getValue());
    	MessageProduct mp = new MessageProduct(user, "5", p);
    	Message m = client.sendbuymsg(user, mp);
    	txtadd.setText(m.getContent());
    	
    	
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
    	vf.setValue(1);
    	AQtyField.setValueFactory(vf);
    	
    	addpagepane.setTranslateX(-200);
    }
    

    
}
