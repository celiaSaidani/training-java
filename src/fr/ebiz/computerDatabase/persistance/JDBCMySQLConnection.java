package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class JDBCMySQLConnection {

	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	public static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&zeroDateTimeBehavior=convertToNull";
	public static final String USER = "admincdb";
	public static final String PASSWORD = "qwerty1234";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public Connection dbConnection;

	/**
	 * constructor
	 */
	private JDBCMySQLConnection() {
		try {
			// Load MySQL Java driver
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return connection
	 */
	private Connection createConnection() {

		Connection connection = null;
		try {
			// Establish Java MySQL connection
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}

	public static JDBCMySQLConnection getInstance() {
		return instance;
	}

	public Statement getConnection() {

		dbConnection = createConnection();
		try {
			Statement statement = dbConnection.createStatement();
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public int closeConnection() {
		try {
			dbConnection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;

	}
}
