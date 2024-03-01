package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConnectSql {

	public Connection conn = null;
	
	public static Connection connectDb()
	{
		String dbName = "applicazionestrutturaledb";
		String url = "jdbc:mysql://localhost/" + dbName;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			JOptionPane.showMessageDialog(null, "ConnectionEstablished");
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
	public static ObservableList<User> getUserData()
	{
		Connection conn = connectDb();
		ObservableList<User> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from user");
			ResultSet rs = ps.executeQuery();
		
			while(rs.next()) {
				list.add(new User(rs.getString("Name"), rs.getString("LastName"), "", "", ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
