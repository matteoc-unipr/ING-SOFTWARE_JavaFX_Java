package application;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * The class {@code ObjectClient} defines a client that sends
 * an object message and then waits for an answer.
 *
**/
public class ObjectClient
{
  private static final int SPORT = 4444;
  private static final String SHOST = "localhost";
  private Socket  client;
  public ObjectOutputStream os;
  public ObjectInputStream  is;
  
  
  public void startClient() throws UnknownHostException, IOException
  {
	  client = new Socket(SHOST, SPORT);
	  os = new ObjectOutputStream(client.getOutputStream());
	  is = new ObjectInputStream(client.getInputStream());

  }
  
  /**
   * Sends a message.
 * @throws IOException 
 * @throws ClassNotFoundException 
 * @return a Message object.

  **/  
  public Message sendmsg(User u, Message m) throws IOException, ClassNotFoundException 
  {
	  
		  os.writeObject(m);
		  os.flush();
		  os.reset();

		  Object o = is.readObject();

		  if ((o != null) && (o instanceof Message))
		  {
			  Message n = (Message) o;

			  return n;
		  }
		  else 
		  {
			  return null;
		  }
  }
  
  /**
   * Sends a message and return a messsagelist object.
   * @throws IOException 
   * @throws ClassNotFoundException 
   * @args the User, the Message.
   * @return a MessageList object.
   **/  
  public MessageList sendmsglist(User u, Message m) throws IOException, ClassNotFoundException 
  {
	  
	  os.writeObject(m);
	  os.flush();
	  os.reset();

	  Object o = is.readObject();
	  
	  if((o != null) && (o instanceof MessageList))
	  {
		  MessageList n = (MessageList) o;
		  return n;
	  }
	  else 
	  {
		  return null;
	  }
  }
  
  /**
   * Sends a message to buy a product.
   * @throws IOException 
   * @throws ClassNotFoundException 
   * @args the User, the MessageProduct.
   * @return a Message object.
   **/  
  public Message sendbuymsg(User u, MessageProduct m) throws IOException, ClassNotFoundException 
  {
	  os.writeObject(m);
	  os.flush();
	  os.reset();
	  
	  Object o = is.readObject();
	  
	  
	  if((o != null) && (o instanceof Message))
	  {
		  Message n = (Message) o;
		  return n;
	  }
	  else 
	  {
		  return null;
	  }
  }
  
  /**
   * Get the ProductList.
   * @throws IOException 
   * @throws ClassNotFoundException 
   * @args the User.
   * @return the ProductList.
   **/ 
  public ArrayList<Product> getPList(User user) throws ClassNotFoundException, IOException
  {
	  Message t1      = new Message(user, "1"); 
	  MessageList tl1 = sendmsglist(user, t1);
	  return tl1.getContent();
  }

  /**
   * Get the CartList.
   * @throws IOException 
   * @throws ClassNotFoundException 
   * @args the User.
   * @return the CartList.
   **/ 
  public ArrayList<Product> getCartList(User user) throws ClassNotFoundException, IOException
  {
	  Message t2      = new Message(user, "3"); 
	  MessageList tl2 = sendmsglist(user, t2);
	  return tl2.getContent();
  }

  /**
   * Create a new User for login.
   * @args this method not requires arguments.
   * @return the User.
  **/  
  public User createuser() {
	  Scanner input = new Scanner(System.in);
	  System.out.println("Insert user credentials:");
	  System.out.print("Username: ");
	  String u = input.nextLine();
	  System.out.print("Password: ");
	  String p = input.nextLine();
	  
	  User newuser = new User("","",u,"",p);
	  return newuser;
  }
  
  /**
   * Close the client.
   * @args this method not requires arguments.
  **/  
  public void closeClient() throws IOException
  {
	  client.close();
  }
  
}