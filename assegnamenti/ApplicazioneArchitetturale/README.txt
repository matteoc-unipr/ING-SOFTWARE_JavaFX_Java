
                             _       _                        _       
     /\                     (_)     | |                      | |      
    /  \   _ __  _ __   ___  _ _ __ | |_ _ __ ___   ___ _ __ | |_ ___ 
   / /\ \ | '_ \| '_ \ / _ \| | '_ \| __| '_ ` _ \ / _ \ '_ \| __/ __|
  / ____ \| |_) | |_) | (_) | | | | | |_| | | | | |  __/ | | | |_\__ \
 /_/    \_\ .__/| .__/ \___/|_|_| |_|\__|_| |_| |_|\___|_| |_|\__|___/
          | |   | |                                                   
          |_|   |_|                                                   


@author Costella Matteo (330671)

-> INTRODUCTION:

This is a simple application for online managing of Medical Appointments, created using the Model-View-Controller Architecture and a PhpMyAdmin database.
The Model is represented by the 'ConnectSql' class and it manages all the Controllers of the fxml pages.
The graphic interface is managed with javafx and all classes are written in Java language.

-> HOW TO RUN:

First of all, you need to create a new database on PhpMyAdmin and then import the file 'apparchitetturale.sql' that you'll find in this folder. This operation will inizialize all the tables the application need to run and this tables will be filled with some example elements.

Now you can run the application and choose between:
- Log in using the admin account -> ID: 1234 , Psw: admin
- Sign up as a new user -> the app will create a new ID for your new account and you will use that for log in.

-> FEATURES:
HOME PAGE:
- You can see the list of all free appointments.
- You can Book an appointment by selecting it in the table and then pressing the button.
- You can see your booked appointments by clicking the 'My Appointments' button.
- You can refresh the table with the button 'Refresh'.

MY APPOINTMENTS PAGE:
- You can see your booked appointmens.
- You can Cancel a prenotation.
