   ____  _   _ _      _____ _   _ ______    _____ _    _  ____  _____  
  / __ \| \ | | |    |_   _| \ | |  ____|  / ____| |  | |/ __ \|  __ \ 
 | |  | |  \| | |      | | |  \| | |__    | (___ | |__| | |  | | |__) |
 | |  | | . ` | |      | | | . ` |  __|    \___ \|  __  | |  | |  ___/ 
 | |__| | |\  | |____ _| |_| |\  | |____   ____) | |  | | |__| | |     
  \____/|_| \_|______|_____|_| \_|______| |_____/|_|  |_|\____/|_|     
                                                                       
@author Costella Matteo (330671)

-> INTRODUCTION:

This is a simple online shop application based on client-server interactions with sockets. The graphic interface is managed with javafx and all classes are written in Java language.

-> HOW TO RUN:

First of all, you need to start the Server by running the "ObjectServer.java" file. The server shell will open and now you have to make a choice:
- Operate on the server (Add/remove users or products, view user/product lists)
- Start server comunications

Once you've started the communications you can start the client and the application by running the "Main.java" file.  
Now you can login with your credentials or with "admin - admin" if you've not added your user profile.

-> FEATURES:
SHOP PAGE:
- You can see the product list.
- You can buy a product by clicking on it and you can also change the quantity that you're buying.
- By clicking the cart button on the left side it'll show your cart with the products you've purchased.

RETURN PRODUCT PAGE:
- You can see your cart.
- You can return a product by clicking on it and you can also change the quantity that you're returning.
- Once you've returned a product you'll see it again in the shop's product list.

ADD PRODUCT PAGE:
- You can add a product by compiling some fields.
- You can't add a product that already exists in the shop.
- If you insert a correct product it'll be displayed in the shop and you can buy it.