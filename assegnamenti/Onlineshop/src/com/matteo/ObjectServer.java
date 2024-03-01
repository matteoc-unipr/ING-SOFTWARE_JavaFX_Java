package com.matteo;


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
  
  public ObjectServer() {
      UserList = new ArrayList<User>(); 
      ProductList = new ArrayList<Product>();
      ShoppingCart = new ArrayList<Product>();
      UserList.add(new User("m","c","matte","sss","password"));
      UserList.add(new User("m","c","cico","sss","cico"));
      UserList.add(new User("m","c","albi","sss","albi"));
      ProductList.add(new Product("uova","15","5512",1));
      ProductList.add(new Product("pane","15","4458",6));
      ProductList.add(new Product("vino","15","4412",5));
      ProductList.add(new Product("pasta","15","7856",1));
      ProductList.add(new Product("acqua","15","4575",7));
      
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

				  if (login(l))
				  {
					  switch(m.getContent())
					  {
					  case "1":  //View product list
						  os.writeObject(new MessageList(m.getUser(), ProductList));
						  os.flush();
						  os.reset();
						  break;


					  case "2":  //Shop a product
						  os.writeObject(new Message(m.getUser(), "Insert ID:"));
						  os.flush();
						  Object s1 = is.readObject();
						  Message ms = (Message) s1;
						  String id = ms.getContent();
						  boolean found = false;
						  for (Product p : ProductList) {
							  if(p.getProductID().equals(id))
							  {
								  found = true;
								  if(p.getProductQty()>0)
								  {
									  os.writeObject(new Message(m.getUser(), "Insert quantity:"));
									  os.flush();

									  Object s2 = is.readObject();
									  Message ms2 = (Message) s2;

									  int sq = Integer.parseInt(ms2.getContent());
									  int pq = p.getProductQty();
									  if ((pq - sq) >= 0)
									  {
										  Product s = new Product(p.getProductName(),p.getProductPrice(),p.getProductID(),sq);
										  ShoppingCart.add(s);
										  p.setProductQty(pq - sq);

										  os.writeObject(new Message(m.getUser(), "Purchased successfully!"));
										  os.flush();
									  }
									  else
									  {
										  os.writeObject(new Message(m.getUser(), "Not enough products...Select smaller quantity."));
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


					  case "3":  //View shopping cart
						  os.writeObject(new MessageList(m.getUser(), ShoppingCart));
						  os.flush();
						  os.reset();
						  break;


					  case "4":  //Return a product
						  os.writeObject(new Message(m.getUser(), "Insert ID:"));
						  os.flush();
						  Object s3 = is.readObject();
						  Message ms3 = (Message) s3;
						  String id3 = ms3.getContent();
						  found = false;
						  for(Product x :ShoppingCart)
						  {
							  if(id3.equals(x.getProductID()))
							  {
								  int pq,sq;
								  found = true;
								  while(true) {
									  os.writeObject(new Message(m.getUser(), "Insert quantity you want to return:"));
									  os.flush();

									  Object s2 = is.readObject();
									  Message ms2 = (Message) s2;
									  sq = Integer.parseInt(ms2.getContent());
									  pq = x.getProductQty();
									  
									  if(sq <= pq)
									  {
										  break;
									  }
								  }
								  x.setProductQty(pq - sq);

								  boolean found2 = false;
								  for(Product y : ProductList)
								  {
									  if(id3.equals(y.getProductID()))
									  {
										  found = true;
										  int yq = y.getProductQty();
										  y.setProductQty(yq + sq);
										  os.writeObject(new Message(m.getUser(), "Product returned successfully!"));
										  os.flush();
										  break;
									  }
								  }
								  if(!found2)
								  {
									  os.writeObject(new Message(m.getUser(), "Error: Product not found in ProductList."));
									  os.flush();
								  }


							  }
						  }
						  if(!found) {
							  os.writeObject(new Message(m.getUser(), "Product does not exist in your shopping cart!"));
							  os.flush();  
						  }

						  break;


					  case "5":  //Request to add product 
						  os.writeObject(new Message(m.getUser(), "Insert product name you want to add:"));
						  os.flush();  
						  Object s5 = is.readObject();
						  Message ms5 = (Message) s5;
						  String id5 = ms5.getContent();
						  found = false;
						  for(Product p : ProductList)
						  {
							  if(id5.equals(p.getProductName()))
							  {
								  found = true;
								  os.writeObject(new Message(m.getUser(), "This product already exists!"));
								  os.flush();  
							  }
						  }
						  if(!found)
						  {
							  os.writeObject(new Message(m.getUser(), "Insert price:"));
							  os.flush();

							  Object s2 = is.readObject();
							  Message ms2 = (Message) s2;
							  String sq = ms2.getContent();
							  
							  os.writeObject(new Message(m.getUser(), "Insert quantity you want to add:"));
							  os.flush();

							  Object s6 = is.readObject();
							  Message ms6 = (Message) s6;
							  int sq6 = Integer.parseInt(ms2.getContent());
							  
							  Random random = new Random();										//Product ID is generated randomly
							  int randomNumber = random.nextInt(9000) + 1000;
							  String rnumber = Integer.toString(randomNumber);
							  
							  Product n = new Product(id5, sq, rnumber, sq6 );
							  ProductList.add(n);
							  
							  os.writeObject(new Message(m.getUser(), "Product added to ProductList!"));
							  os.flush();
						  }
						  
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
				  else
				  {
					  continue;
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
				  os.writeObject(new Message(i, "Password incorrect! Change user credentials and try again..."));
				  os.flush();
				  return false;
			  }
		  }
	  }
	  os.writeObject(new Message(i, "Username not found! Change user credentials and try again..."));
	  os.flush();
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
  }
  
  /**
   * Creates a new user.
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
   * Creates a new product.
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
   * They can add/remove user profiles or products or they can view user and product lists.
   *
   * @param args  the method does not requires arguments.
   * @throws ClassNotFoundException 
   *
  **/
  public static void main(final String[] args) throws ClassNotFoundException
  {
	  new ObjectServer().reply();
	  Scanner input = new Scanner(System.in);
	  ObjectServer instance = new ObjectServer();

	  while(true) {

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
		  case 7:

			  System.out.println("Server closed... Program terminated!");
			  System.exit(0);
		  default:
			  break;

		  }
	  }
	  
	  
	  
	  
	  
  }
}