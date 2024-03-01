package com.matteo;


import java.io.Serializable;

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
  private final String userName;
  private final String address;
  private final String password;

  /**
   * Class constructor.
   *
   * @param f  the first name.
   * @param l  the last name.
   * @param a  the address.
   * @param p  the password.
   */
  public User(final String f, final String l, final String u, final String a, final String p)
  {
    this.firstName = f;
    this.lastName  = l;
    this.userName  = u;
    this.address   = a;
    this.password  = p;
  }

  /**
   * Create a string that describe the user.
   *
   * @return a string with all user informations (without password).
   *
   **/
  public String toString()
  {
	  return "First Name: "+this.firstName+"    Last Name: "+this.lastName+"    Username: "+this.userName+"    Email: "+this.address;
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
   * Gets the username.
   *
   * @return the content.
   *
   **/
  public String getuserName()
  {
	  return this.userName;
  }

  /**
   * Gets the address.
   *
   * @return the address.
   *
  **/
  public String getAddress()
  {
    return this.address;
  }

  /**
   * Gets the password.
   *
   * @return the password.
   *
  **/
  public String getPassword()
  {
    return this.password;
  }
}