package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
*
* The class {@code HomePageController} defines a javafx controller for the fxml file HomePage.fxml.
*
**/
public class HomePageController implements Initializable{
	
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
    private ImageView Logout;

    @FXML
    private ImageView Cart;

    @FXML
    private ImageView CartBack;

    @FXML
    private AnchorPane slider;
    
    @FXML
    private AnchorPane centerslide;

    @FXML
    private AnchorPane HomeRootPane;
    
    @FXML 
    private BorderPane bp;
    
        
    @FXML
    private TableColumn<Product, String> NameColumn;

    @FXML
    private TableColumn<Product, String> PriceColumn;

    @FXML
    private TableColumn<Product, Integer> QtyColumn;

    @FXML
    private TableColumn<Product, String> IdColumn;
    
    @FXML
    private TableView<Product> TableProducts;
    
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtOutputBuy;

    @FXML
    private Button BuyButton;

    @FXML
    private TableView<Product> TableCart;
    

    @FXML
    private TableColumn<Product, String> NameCColumn;
    

    @FXML
    private TableColumn<Product, Integer> QtyCColumn;
    
    @FXML
    private TableColumn<Product, String> IdCColumn;
    
    @FXML
    private ImageView exit;
    
    @FXML
    public void AddPage() {
    	loadAddPage("AddProductPage"); 
    }
    
    @FXML
    public void ReturnPage() {
    	loadReturnPage("ReturnProductPage");
    }

    @FXML
    public void ShopPage() throws ClassNotFoundException, IOException {
    	PList = client.getPList(user);
		setPol(PList);

    	bp.setCenter(centerslide);
    }
    
    @FXML
    public void getProduct() {
    	index = TableProducts.getSelectionModel().getSelectedIndex();
    	if (index <= -1) {
    		return;
    	}
    	
    	txtName.setText(NameColumn.getCellData(index).toString());
    	txtPrice.setText(PriceColumn.getCellData(index).toString());
    	txtID.setText(IdColumn.getCellData(index).toString());
    	txtQty.setText(QtyColumn.getCellData(index).toString());
    	
    }
        
 
    /**
     * Load the Return Product Page.
     *
     * @args the String of page name.
     *
    **/
    private void loadReturnPage(String page)
    {
    	Parent root = null;
    	try {    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(page+".fxml"));
			root = loader.load();
			ReturnProductPageController returnpagecontroller = loader.getController();

			returnpagecontroller.setClient(client);
			returnpagecontroller.setUser(user);
			returnpagecontroller.setPList(PList);
			returnpagecontroller.setCartList(CartList);
			returnpagecontroller.setPol(PList);
			returnpagecontroller.setCol(CartList);

		} catch (IOException e) {
			Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, e);
		}
    	bp.setCenter(root);
    }

    /**
     * Load the Add Product Page.
     *
     * @args the String of page name.
     *
    **/
    private void loadAddPage(String page)
    {
    	Parent root = null;
    	try {    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(page+".fxml"));
			root = loader.load();
			AddProductPageController returnpagecontroller = loader.getController();

			returnpagecontroller.setClient(client);
			returnpagecontroller.setUser(user);

		} catch (IOException e) {
			Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, e);
		}
    	bp.setCenter(root);
    }
    

    @FXML
    void buyProduct() throws ClassNotFoundException, IOException {
    	int q = Integer.parseInt(txtQty.getText()); 
    	Product p = new Product(txtName.getText(),txtPrice.getText(),txtID.getText(),q);
    	MessageProduct mp = new MessageProduct(user, "2", p);
    	Message m = client.sendbuymsg(user, mp);
    	txtOutputBuy.setText(m.getContent());
    	
    	
    	PList = client.getPList(user);
		setPol(PList);

    	CartList = client.getCartList(user);
		setCol(CartList);

    }
    

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	NameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
    	PriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductPrice"));
    	IdColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductID"));
    	QtyColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductQty"));
    	
    	TableProducts.setItems(Pol);
    	
    	NameCColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
    	IdCColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductID"));
    	QtyCColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductQty"));
    	
    	TableCart.setItems(Col);
    
    	
    	
    	
    	slider.setTranslateX(-200);
    	centerslide.setTranslateX(-200);
    	Cart.setVisible(true);
		CartBack.setVisible(false);
    	
    	Cart.setOnMouseClicked(event -> {
    		TranslateTransition slide = new TranslateTransition();
    		TranslateTransition slidec = new TranslateTransition();
    		slide.setDuration(Duration.seconds(0.4));
    		slidec.setDuration(Duration.seconds(0.4));
    		slide.setNode(slider);
    		slidec.setNode(centerslide);
    		slide.setToX(0);
    		slidec.setToX(0);
    		slide.play();
    		slidec.play();
    		
    		slider.setTranslateX(-200);
    		centerslide.setTranslateX(-200);
    		
    		slide.setOnFinished((ActionEvent e)->{
    			Cart.setVisible(false);
    			CartBack.setVisible(true);
    		});
    	});
    	
    	CartBack.setOnMouseClicked(event -> {
    		TranslateTransition slide = new TranslateTransition();
    		TranslateTransition slidec = new TranslateTransition();
    		slide.setDuration(Duration.seconds(0.4));
    		slidec.setDuration(Duration.seconds(0.4));
    		slide.setNode(slider);
    		slidec.setNode(centerslide);
    		slide.setToX(-200);
    		slidec.setToX(-200);
    		slide.play();
    		slidec.play();
    		
    		slider.setTranslateX(0);
    		centerslide.setTranslateX(0);
    		slide.setOnFinished((ActionEvent e)->{
    			Cart.setVisible(true);
    			CartBack.setVisible(false);
    		});
    	});
    	
    	
    	
    	exit.setOnMouseClicked(event -> {
    		try {
				client.closeClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		System.exit(0);
    	});
    	
    	
    	
    	
    }
    
}