package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.mapper.ComputerDAO;

public class JDBCMySQLConnection {

	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	public static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&zeroDateTimeBehavior=convertToNull";
	public static final String USER = "admincdb";
	public static final String PASSWORD = "qwerty1234";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public Connection dbConnection;
	private static final Logger logger = LoggerFactory.getLogger(JDBCMySQLConnection.class);

	/**
	 * constructor
	 */
	private JDBCMySQLConnection() {
		try {
			// Load MySQL Java driver
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			logger.error("Error Driver MySQL");
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
			logger.error("Error Unable to Connect to Database");
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
			logger.error("Error in function getConnection");
		}
		return null;

	}

	public Connection getConnectionP() {

		dbConnection = createConnection();
		return dbConnection;

	}

	/**
	 * close connexion
	 * 
	 * @return
	 */
	public int closeConnection() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			logger.error("Error Unable to close connection");
			return 1;
		}
		return 0;

	}
}
