package archapplication;


import java.io.Serializable;
import java.util.Random;

/**
*
* The class {@code User} provides a simple user model.
*
**/

public final class User implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final String firstName;
  private final String lastName;
  private final int id;
  private final String password;


  /**
   * Class constructor.
   * User id is generated randomly.
   *
   * @param f  the first name.
   * @param l  the last name.
   */
  public User(final String f, final String l, final int i,final String p)
  {
    this.firstName = f;
    this.lastName  = l;
    this.id=i;
    this.password=p;

  }

  /**
   * Create a string that describe the user.
   *
   * @return a string with all user informations.
   *
   **/
  public String toString()
  {
	  return "First Name: "+this.firstName+"    Last Name: "+this.lastName+"    ID: "+this.id;
  }
  
  /**
   * Gets the first name.
   *
   * @return the content.
   *
  **/
  public String getFirstName()
  {
    return this.firstName;
  }

  /**
   * Gets the last name.
   *
   * @return the content.
   *
  **/
  public String getLastName()
  {
    return this.lastName;
  }
  
  /**
   * Gets the user id.
   *
   * @return the content.
   *
   **/
  public int getuserID()
  {
	  return this.id;
  }

  /**
   * Gets the user psw.
   *
   * @return the content.
   *
   **/
  public String getuserPsw()
  {
	  return this.password;
  }


}