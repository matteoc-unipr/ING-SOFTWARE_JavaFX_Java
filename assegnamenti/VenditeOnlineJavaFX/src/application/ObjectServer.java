package application;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * The class {@code ObjectServer} defines a server that waits
 * for an object message and then sends an answer.
 *
**/
public class ObjectServer
{
  private static final int SPORT  = 4444;
  private ArrayList<User> UserList;
  private ArrayList<Product> ProductList;
  private ArrayList<Product> ShoppingCart;
  private ServerSocket server;
  private Socket client;
  private ObjectInputStream  is;
  private ObjectOutputStream os;
  public boolean found = false;
  Product to_remove;
  public static ObjectServer instance = new ObjectServer();


  public ObjectServer() {
      UserList = new ArrayList<User>(); 
      ProductList = new ArrayList<Product>();
      ShoppingCart = new ArrayList<Product>();
      
      //Example user
      UserList.add(new User("admin","admin","admin","admin","admin"));
  
      //Some example products
      ProductList.add(new Product("Eggs","2.99","5512",10));
      ProductList.add(new Product("Chocolate","4.50","4458",6));
      ProductList.add(new Product("Beer","3.50","4412",5));
      ProductList.add(new Product("Chicken wings","6.00","7856",8));
      ProductList.add(new Product("Tomato sauce","1.99","4575",7));
      
  }
  
  /**
   * Sends the answer.
   * @throws ClassNotFoundException 
   *
  **/  
  public void reply() throws ClassNotFoundException 
  {
	  try
	  {
		  server = new ServerSocket(SPORT);
		  System.out.println("Server ready: Port " + SPORT);

		  client = server.accept();
		  System.out.println("Server connected to client!");

		  is = new ObjectInputStream(client.getInputStream());
		  os = new ObjectOutputStream(client.getOutputStream());
		  while (true)
		  {

			  Object o = is.readObject();
			  if ((o != null) && (o instanceof Message))
			  {
				  Message m = (Message) o;
				  User l = m.getUser();


				  switch(m.getContent())
				  {
				  case "0": //Login
					  if (login(l)) 
					  {
						  os.writeObject(new Message(m.getUser(), "true"));
						  os.flush();
						  os.reset();
					  }
					  else
					  {
						  os.writeObject(new Message(m.getUser(), "false"));
						  os.flush();
						  os.reset(); 
					  }
					  break;


				  case "1":  //View product list
					  os.writeObject(new MessageList(m.getUser(), ProductList));
					  os.flush();
					  os.reset();
					  break;


				  case "3":  //View shopping cart
					  os.writeObject(new MessageList(m.getUser(), ShoppingCart));
					  os.flush();
					  os.reset();
					  break;


				  case "6":  //Close client
					  client.close();
					  server.close();
					  break;
				  default:
					  os.writeObject(new Message(m.getUser(), "Invalid action!"));
					  os.flush();
					  break;
				  }

			  }
			  else if ((o != null) && (o instanceof MessageProduct))
			  {
				  MessageProduct m = (MessageProduct) o;

				  switch(m.getContent())
				  {
				  case "2":  //Shop a product
					  Product g = m.getProduct();
					  found = false;
					  for (Product p : ProductList) {
						  if(p.getProductID().equals(g.getProductID()))
						  {
							  found = true;
							  if(p.getProductQty()>0)
							  {
								  int sq = g.getProductQty();
								  int pq = p.getProductQty();
								  if ((pq - sq) >= 0)
								  {
									  p.setProductQty(pq - sq);
									  ShoppingCart.add(g);

									  os.writeObject(new Message(m.getUser(), "Purchased successfully!"));
									  os.flush();
								  }
								  else
								  {
									  os.writeObject(new Message(m.getUser(), "Not enough products..."));
									  os.flush();
								  }
							  }
							  else
							  {
								  os.writeObject(new Message(m.getUser(), "Product out of stock!"));
								  os.flush();
							  }
						  }
					  }
					  if(!found)
					  {
						  os.writeObject(new Message(m.getUser(), "Product ID not found!"));
						  os.flush();
					  } 


					  break;

				  case "4":  //Return a product

					  Product e = m.getProduct();

					  boolean remove = false;
					  found = false;
					  for(Product x :ShoppingCart)
					  {
						  if(e.getProductID().equals(x.getProductID()))
						  {
							  int pq,sq;
							  found = true;

							  sq = e.getProductQty();
							  pq = x.getProductQty();

							  if(sq > pq)
							  {
								  os.writeObject(new Message(m.getUser(), ""));
								  os.flush();
								  os.reset();
								  break;
							  }

							  if ((pq - sq) == 0)
							  {
								  remove = true;
								  to_remove = x;
							  }
							  else
							  {
								  x.setProductQty(pq - sq);
							  }
							  
							  boolean found2 = false;
							  for(Product y : ProductList)
							  {
								  if(e.getProductID().equals(y.getProductID()))
								  {
									  found2 = true;
									  int yq = y.getProductQty();
									  y.setProductQty(yq + sq);
									  os.writeObject(new Message(m.getUser(), "Product returned successfully!"));
									  os.flush();
									  os.reset();
									  break;
								  }
							  }
							  if(!found2)
							  {
								  os.writeObject(new Message(m.getUser(), "Error: Product not found in ProductList."));
								  os.flush();
								  os.reset();
							  }


						  }
					  }
					  if(!found) {
						  os.writeObject(new Message(m.getUser(), "Product does not exist in your shopping cart!"));
						  os.flush();  
						  os.reset();
					  }
					  
					  if(remove)
					  {
						  ShoppingCart.remove(to_remove);
					  }

					  break;
					  

				  case "5":  //Request to add product 
					  Product r = m.getProduct();
					  found = false;
					  for(Product p : ProductList)
					  {
						  if(r.getProductName().equals(p.getProductName()))
						  {
							  found = true;
							  os.writeObject(new Message(m.getUser(), "This product already exists!"));
							  os.flush();  
						  }
					  }
					  if(!found)
					  {
						  Random random = new Random();										//Product ID is generated randomly
						  int randomNumber = random.nextInt(9000) + 1000;
						  String rnumber = Integer.toString(randomNumber);
						  r.setProductID(rnumber);
						  
						  ProductList.add(r);

						  os.writeObject(new Message(m.getUser(), "Product added to ProductList!"));
						  os.flush();
					  }

					  break;
				  }

			  }
			  else
			  {
				  break;
			  }   
		  }

		  client.close();
		  server.close();
	  }
	  catch (IOException e)
	  {
		  System.out.println("Connection Failed...");
		  e.printStackTrace();
	  }
  }

  /**
   * Provides user login .
   *
   * @param the User.
   * @return true if username-password is correct, else return false.
   **/
  public boolean login(User i) throws IOException
  {
	  for (User u : UserList)
	  {
		  if (i.getuserName().equals(u.getuserName()))
		  {
			  if(i.getPassword().equals(u.getPassword()))
			  {
				  return true;
			  } else
			  {
				  return false;
			  }
		  }
	  }
	  return false;
  }  
  
  /**
   * Display possible actions .
   *
   * @param the method does not requires arguments.
   *
  **/
  public static void displayactions() {
	  System.out.println("");
	  System.out.println("");
	  System.out.println("PLEASE SELECT AN ACTION:");
	  System.out.println("1) Add new user.");
	  System.out.println("2) Add new product.");
	  System.out.println("3) View user list.");
	  System.out.println("4) View product list.");
	  System.out.println("5) Delete user.");
	  System.out.println("6) Delete product.");
	  System.out.println("7) Close server.");
	  System.out.println("8) Start server comunications.");
  }
  
  /**
   * Manage possible actions on server:
   * - Add Users/Products
   * - Delete Users/Products
   * - View User/Product Lists
   * - Close server
   * - Start server comunications
   * @param the method does not requires arguments.
   *
  **/
  public static void firstactions() {
	  Scanner input = new Scanner(System.in);
	  int i;
	  
	  while(true)
	  {
		  System.out.println("SELECT AN ACTION:");
		  System.out.println("1) Make actions on server.");
		  System.out.println("2) Start server comunications.");
		  i = input.nextInt();
		  if(i==1) 
		  {
			  boolean x = true;
			  while(x) 
			  {
				  displayactions();
				  int n = 0;
				  n = input.nextInt();

				  switch(n) {
				  case 1:	//Add user
					  instance.useradd();
					  break;

				  case 2:	//Add product
					  instance.productadd();
					  break;

				  case 3:	//View user list
					  instance.pUserList();
					  break;

				  case 4:	//View product list
					  instance.pProductList();
					  break;

				  case 5:	//Delete user
					  instance.userdel();
					  break;
				  case 6:	//Delete product
					  instance.productdel();	
					  break;
				  case 7:   //Close server 
					  System.out.println("Server closed... Program terminated!");
					  System.exit(0);
				  case 8:	//Start server comunications
					  i = 2;
					  x = false;
					  break;
				  default:
					  break;
				  }
			  }
		  }
		  if(i==2) {
			  return;
		  }
		  System.out.println("Insert a valid action...");
	  }
  }
  
  /**
   * Creates a new user from server prompt.
   *
   * @param the method does not requires arguments.
   * @return the new user object.
   *
  **/
  public void useradd() {
	  Scanner input = new Scanner(System.in);
	  System.out.print("First name: ");
	  String f = input.nextLine();
	  System.out.print("Last name: ");
	  String l = input.nextLine();
	  System.out.print("Username: ");
	  String u = input.nextLine();
	  System.out.print("Address: ");
	  String a = input.nextLine();
	  System.out.print("Password: ");
	  String p = input.nextLine();
	  
	  User newuser = new User(f,l,u,a,p);
	  UserList.add(newuser);  
	  
	  System.out.println("User " + f + " " + l + " created successfully!");
	  
  }

  /**
   * Creates a new product from server prompt.
   *
   * @param the method does not requires arguments.
   * @return the new product object.
   *
   **/
  public void productadd() {
	  Scanner input = new Scanner(System.in);
	  System.out.print("Product name: ");
	  String n = input.nextLine();
	  System.out.print("Product ID: ");
	  String i = input.nextLine();
	  System.out.print("Product price: ");
	  String p = input.nextLine();
	  System.out.print("Product quantity: ");
	  int q = input.nextInt();
	  
	  
	  Product newproduct = new Product(n,p,i,q);
	  ProductList.add(newproduct);
	  System.out.println("Product " + n + " added successfully!");
	  
  }

  /**
   * Print UserList.
   *
  **/  
  public void pUserList() {
	  for (User u : UserList) {
		  System.out.println(u);
	  }
  }
  
  /**
   * Print ProductList.
   *
   **/  
  public void pProductList() {
	  for (Product p : ProductList) {
		  System.out.println(p);
	  }
  }
  
  /**
   * Delete user from UserList.
   *
   **/  
  public void userdel() {
	  Scanner input = new Scanner(System.in);
	  System.out.print("Insert username you want to delete: ");
	  String n = input.nextLine();

	  for (User u : UserList) {
		  if(n.equals(u.getuserName())) {
			  System.out.print("Insert password for "+n+": ");
			  String p = input.nextLine();
			  if(p.equals(u.getPassword())) {
				  UserList.remove(u);
				  System.out.println("User removed successfully!");
				  return;
			  }else {
				  System.out.println("Wrong password...Try again.");
				  return;
			  }
		  }
	  }
	  System.out.println("Username not found!");

  }
  
  /**
   * Delete product from ProductList.
   *
   **/  
  public void productdel() {
	  Scanner input = new Scanner(System.in);
	  System.out.print("Insert ID of the product you want to delete: ");
	  String n = input.nextLine();
	  
	  for (Product p : ProductList) {
		  if(n.equals(p.getProductID())) {
			  UserList.remove(p);
			  System.out.println("Product removed successfully!");
			  return;
		  }
	  }
	  System.out.println("ID not found!");
	  
  }
  
  
  
  
  
  /**
   * Provides several actions to server users.
   *
   * @param args  the method does not requires arguments.
   * @throws ClassNotFoundException 
   *
  **/
  public static void main(final String[] args) throws ClassNotFoundException
  {
	  firstactions();
	  instance.reply();

  }
}