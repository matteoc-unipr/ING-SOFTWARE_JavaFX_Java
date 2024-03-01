package application;


import java.io.Serializable;

/**
*
* The class {@code MessageProduct} defines a simple message model for passing products.
*
**/

public final class MessageProduct implements Serializable
{
  private static final long serialVersionUID = 1L;

  private User user;
  private String content;
  private Product product;

  /**
   * Class constructor.
   *
   * @param u  the user.
   * @param c  the content.   
   * @param p  the product.   
  **/
  public MessageProduct(final User u, final String c, final Product p)
  {
    this.user    = u;
    this.content = c;
    this.product = p;
  }

  /**
   * Get the user.
   *
   * @return the user.
   *
  **/
  public User getUser()
  {
    return this.user;
  }

  /**
   * Get the content.
   *
   * @return the content.
   *
  **/
  public String getContent()
  {
    return this.content;
  }

  /**
   * Get the product.
   *
   * @return the product.
   *
   **/
  public Product getProduct()
  {
	  return this.product;
  }

  /**
   * Sets the content.
   *
   * @param c  the content.
   *
  **/
  public void setContent(final String c)
  {
    this.content = c;
  }

  /**
   * Sets the product.
   *
   * @param p  the product.
   *
   **/
  public void setProduct(final Product p)
  {
	  this.product = p;
  }
}