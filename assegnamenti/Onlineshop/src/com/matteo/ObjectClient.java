package com.matteo;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
  private ObjectOutputStream os;
  private ObjectInputStream  is;
  /**
   * Sends a e message.
   *
  **/  
  public void send() 
  {
	  try
	  {
		  client = new Socket(SHOST, SPORT);
		  User u = createuser();
		  Message m      = new Message(u, "");
		  os = new ObjectOutputStream(client.getOutputStream());
		  is = new ObjectInputStream(client.getInputStream());
		  
		  Scanner input = new Scanner(System.in);
		  
		  while (true)
		  {
			  // Sends messages until it receives an ï¿½endï¿½ message
			  displayactions();
			  String c = input.nextLine();
			  if(c.equals("0"))
			  {
				  u = createuser();
				  m      = new Message(u, "");
			  }
			  else {
				  m.setContent(c);


				  os.writeObject(m);
				  os.flush();
				  os.reset();

				  Object o = is.readObject();

				  if ((o != null) && (o instanceof Message))
				  {
					  Message n = (Message) o;

					  System.out.format(n.getContent());

				  }
				  else if((o != null) && (o instanceof MessageList))
				  {
					  MessageList n = (MessageList) o;
					  for (Product i : n.getContent()) {
	        			  System.out.println(i);
	        		  }
				  }
				  else 
				  {
					  break;
				  }
			  }
		  }
		  client.close();
	  }
	  catch (IOException | ClassNotFoundException e)
	  {
		  e.printStackTrace();
	  }
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
   * Display possible actions .
   *
   * @param the method does not requires arguments.
   *
  **/
  public static void displayactions() {
	  System.out.println("");
	  System.out.println("");
	  System.out.println("PLEASE SELECT AN ACTION:");
	  System.out.println("0) Change credentials for login.");
	  System.out.println("1) View product list.");
	  System.out.println("2) Shop a product.");
	  System.out.println("3) View shopping cart.");
	  System.out.println("4) Return a product.");
	  System.out.println("5) Request to add a product.");
	  System.out.println("6) Close client.");
	  System.out.println("");
  }
  
  /**
   * Starts the demo.
   *
   * @param args  the method does not requires arguments.
   *
  **/
  public static void main(final String[] args)
  {

    new ObjectClient().send();
  }
}