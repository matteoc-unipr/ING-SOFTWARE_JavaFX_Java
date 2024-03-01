package application;

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


/**
*
* The class {@code LoginPageController} defines a javafx controller for the fxml file LoginPage.fxml.
*
**/
public class LoginPageController implements Initializable{

	private User newuser;
	private ObjectClient oClient;

	/**
     * Get the client.
     *
     * @return ObjectClient client.
     *
    **/
	public ObjectClient getOClient()
	{
		return this.oClient;
	}
	
	/**
     * Get the user.
     *
     * @args the User.
     *
    **/
	public User getUser()
	{
		return this.newuser;
	}
	
	@FXML
	private Button LoginButton;

	@FXML
	private PasswordField PasswordField;

	@FXML
	private TextField UsernameField;

	@FXML
	private Text Wrong;

	@FXML
	private AnchorPane RootPane;

	@FXML
	private ImageView Quit;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		oClient = new ObjectClient();
		try {
			oClient.startClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void quit() throws IOException {
		oClient.closeClient();
		System.exit(0);
	}

	@FXML
	void tryLogIn(ActionEvent event) throws IOException, ClassNotFoundException 
	{
					
		newuser = new User("","",UsernameField.getText(),"",PasswordField.getText());
		Message m      = new Message(newuser, "0");
		Message n = oClient.sendmsg(newuser, m);

		
		if (n.getContent().equals("true")) 
		{			
			Message p      = new Message(newuser, "1");
			MessageList ml = oClient.sendmsglist(newuser, p);
			
			Message c      = new Message(newuser, "3");
			MessageList cl = oClient.sendmsglist(newuser, c);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			AnchorPane pane; 
			pane = loader.load();
			HomePageController homepagecontroller = loader.getController();
			RootPane.getChildren().setAll(pane);
			homepagecontroller.setClient(oClient);
			homepagecontroller.setUser(newuser);
			homepagecontroller.setPList(ml.getContent());
			homepagecontroller.setCartList(cl.getContent());
			homepagecontroller.setPol(ml.getContent());
			homepagecontroller.setCol(cl.getContent());
			
		}
		else if((UsernameField.getText().isEmpty()) && (PasswordField.getText().isEmpty()))
		{
			Wrong.setText("Please enter your data!");
		}
		else 
		{
			Wrong.setText("Wrong username or password!");
		}

	}

}

