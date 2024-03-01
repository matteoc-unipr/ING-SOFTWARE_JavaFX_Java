package archapplication;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

/**
 * The class{@code ConnectSql} provides a model class for the mvc architecture.
 */
public class ConnectSql {

	public static Connection conn;
	
	public ConnectSql()
	{
		String dbName = "apparchitetturale";
		String url = "jdbc:mysql://localhost/" + dbName;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "", "");
			System.out.println("ConnectionEstablished");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a User from database.
	 *
	 * @param the user id. 
	 * @return User.
	 *
	 **/
	public static User getUser(int id) {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `patients` WHERE `PatientID` =" + id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User n = new User(rs.getString("FirstName"),rs.getString("LastName"),rs.getInt("PatientId"),rs.getString("Password"));
				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Create a new user in the database.
	 *
	 * @param firstName, lastName, password.
	 * @return User id.
	 *
	 **/
	public int createUser(String f, String l, String p) throws SQLException
	{
		Random random = new Random();
		int id = random.nextInt(9000) + 1000;
		PreparedStatement ps = conn.prepareStatement("INSERT INTO `patients` (`FirstName`, `LastName`, `PatientID`, `Password`) VALUES ('"+f+"', '"+l+"', '"+Integer.toString(id)+"', '"+p+"');");
		ps.executeUpdate();		
		
		return  id;
	}
	
	/**
	 * Gets the list of free appointments.
	 *
	 * @return appointments observable list.
	 *
	 **/
	public ObservableList<Appointment> getFreeAppointments()
	{
		ObservableList<Appointment> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from all_appointments where PatientId is NULL");
			ResultSet rs = ps.executeQuery();
		
			while(rs.next()) {
				list.add(new Appointment(rs.getString("DocName"), rs.getDate("Datetime").toString(), rs.getString("Place"),rs.getString("Type"), rs.getInt("AppId"),rs.getInt("PatientId") ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * Gets the list of user appointments.
	 *
	 * @param User.
	 * @return observable list of user appointments.
	 *
	 **/
	public ObservableList<Appointment> getUserAppointments(User u)
	{
		ObservableList<Appointment> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from all_appointments where PatientId="+u.getuserID()+";");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Appointment(rs.getString("DocName"), rs.getDate("Datetime").toString(), rs.getString("Place"),rs.getString("Type"), rs.getInt("AppId"),rs.getInt("PatientId") ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Sets the patient id into an appointment.
	 *
	 * @param appid, patid.
	 *
	 **/
	public void setAppPatientID(String string, int patid) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE `all_appointments` SET `PatientId` = " +patid+" WHERE `all_appointments`.`AppId` = "+string+";");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the patient id as NULL in an appointment.
	 *
	 * @param appid, patid.
	 *
	 **/
	public void RemoveAppPatientID(String string, int patid) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE `all_appointments` SET `PatientId` = NULL WHERE `all_appointments`.`AppId` = " +string+";");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
