package com.matteo;

import java.io.Serializable;

/**
*
* The class {@code Product} provides a simple product model.
*
**/

public final class Product implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final String nameProduct;
  private final String priceProduct;
  private final String idProduct;
  private int qtyProduct;

  /**
   * Class constructor.
   *
   * @param n  the product name.
   * @param p  the product price.
   * @param i  the product ID.
   */
  public Product(final String n, final String p, final String i, final int q)
  {
    this.nameProduct = n;
    this.priceProduct  = p;   
    this.idProduct  = i;
    this.qtyProduct  = q;
  }
  
  /**
   * Create a string that describe the product.
   *
   * @return a string with all product informations.
   *
   **/
  public String toString()
  {
	  return "Name: "+this.nameProduct+"    Price: "+this.priceProduct+"    ID: "+this.idProduct+"    Q.TY: "+this.qtyProduct;
  }
  
  
  /**
   * Gets the product name.
   *
   * @return the name.
   *
  **/
  public String getProductName()
  {
    return this.nameProduct;
  }
  
  /**
   * Gets the product price.
   *
   * @return the price.
   *
   **/
  public String getProductPrice()
  {
	  return this.priceProduct;
  }
  
  /**
   * Gets the product ID.
   *
   * @return the ID.
   *
   **/
  public String getProductID()
  {
	  return this.idProduct;
  }
  
  /**
   * Gets the product quantity.
   *
   * @return the quantity.
   *
   **/
  public int getProductQty()
  {
	  return this.qtyProduct;
  }
  
  /**
   * Sets the product quantity.
   *
   * @args the new quantity.
   *
   **/
  public void setProductQty(final int q)
  {
	 this.qtyProduct = q;
  }
  
}