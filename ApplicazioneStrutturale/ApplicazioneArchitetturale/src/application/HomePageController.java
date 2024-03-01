package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HomePageController implements Initializable {
	ObservableList<User> listU;
	int index = -1;
	
	Connection conn;
	ResultSet rs;
	PreparedStatement pst;
	
    @FXML
    private AnchorPane HomeRootPane;

    @FXML
    private TableColumn<User,String> LastNameCol;

    @FXML
    private TableColumn<User,String> NameCol;

    @FXML
    private TableView<User> Table;
    


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		NameCol.setCellValueFactory(new PropertyValueFactory<User, String>("FirstName"));
		LastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));
		
		listU = ConnectSql.getUserData();
		Table.setItems(listU);
	}

}
