package com.matteo;


import java.io.Serializable;
import java.util.ArrayList;

/**
*
* The class {@code MessageList} defines a simple message model with ArrayLists as argument.
*
**/

public final class MessageList implements Serializable
{
  private static final long serialVersionUID = 1L;

  private User user;
  private ArrayList<Product> content;

  /**
   * Class constructor.
   *
   * @param u  the user.
   * @param c  the content.
   *
  **/
  public MessageList(final User u, final ArrayList<Product> c)
  {
    this.user    = u;
    this.content = c;
  }

  /**
   * Gets the user.
   *
   * @return the user.
   *
  **/
  public User getUser()
  {
    return this.user;
  }

  /**
   * Gets the content.
   *
   * @return the content.
   *
  **/
  public ArrayList<Product> getContent()
  {
    return this.content;
  }

}