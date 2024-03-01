package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
*
* The class {@code ReturnProductPageController} defines a javafx controller for the fxml file ReturnProductPage.fxml.
*
**/
public class ReturnProductPageController implements Initializable{
	private ObjectClient client;
	private User user;
	private ArrayList<Product> PList;
	private ArrayList<Product> CartList;
	private int index;
    ObservableList<Product> Pol = FXCollections.observableArrayList();
    ObservableList<Product> Col = FXCollections.observableArrayList();
	
	
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

    /**
     * Set the ProductList.
     *
     * @args the ArrayList.
     *
    **/
	public void setPList(ArrayList<Product> l)
	{
		PList = l;
	}

    /**
     * Set the CartList.
     *
     * @args the ArrayList.
     *
    **/
	public void setCartList(ArrayList<Product> l)
	{
		CartList = l;
	}

    /**
     * Set the Observable Cart List.
     *
     * @args the ArrayList.
     *
    **/
	public void setCol(ArrayList<Product> l)
	{
		Col.clear();
		for (Product x:l)
    	{
			Col.add(x);
    	}
	}
	
    /**
     * Set the Observable Product List.
     *
     * @args the ArrayList.
     *
    **/
	public void setPol(ArrayList<Product> l)
	{
		Pol.clear();
		for (Product x:l)
    	{
			Pol.add(x);
    	}
	}
	
	
    @FXML
    private TableColumn<Product, String> RIdColumn;

    @FXML
    private TableColumn<Product, String> RNameColumn;

    @FXML
    private TableColumn<Product, String> RPriceColumn;

    @FXML
    private TableColumn<Product, Integer> RQtyColumn;

    @FXML
    private Button ReturnButton;

    @FXML
    private TableView<Product> TableRCart;

    @FXML
    private TextField txtOutputBuy;

    @FXML
    private TextField txtRID;

    @FXML
    private TextField txtRName;

    @FXML
    private TextField txtRPrice;

    @FXML
    private TextField txtRQty;
    
    @FXML
    private AnchorPane returnpane;
    
 

    @FXML
    public void getProduct() {
    	index = TableRCart.getSelectionModel().getSelectedIndex();
    	if (index <= -1) {
    		return;
    	}
    	
    	txtRName.setText(RNameColumn.getCellData(index).toString());
    	txtRPrice.setText(RPriceColumn.getCellData(index).toString());
    	txtRID.setText(RIdColumn.getCellData(index).toString());
    	txtRQty.setText(RQtyColumn.getCellData(index).toString());
    	
    }

    @FXML
    public void returnProduct() throws ClassNotFoundException, IOException {
    	
    	int q = Integer.parseInt(txtRQty.getText()); 
    	Product p = new Product(txtRName.getText(),txtRPrice.getText(),txtRID.getText(),q);
    	MessageProduct mp = new MessageProduct(user, "4", p);
    	Message m = client.sendbuymsg(user, mp);
    	txtOutputBuy.setText(m.getContent());
    	
    	PList = client.getPList(user);
		setPol(PList);

    	CartList = client.getCartList(user);
		setCol(CartList);

    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	
    	RNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
    	RPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductPrice"));
    	RIdColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductID"));
    	RQtyColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductQty"));
    	
    	TableRCart.setItems(Col);
    	
    	returnpane.setTranslateX(-200);


    
    }
}
