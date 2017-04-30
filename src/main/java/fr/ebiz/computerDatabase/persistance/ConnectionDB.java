package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionDB {

    //private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
    public static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&zeroDateTimeBehavior=convertToNull";
    public static final String USER = "admincdb";
    public static final String PASSWORD = "qwerty1234";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static ConnectionDB instance= new ConnectionDB();
    private static final Logger logger = LoggerFactory.getLogger(ConnectionDB.class);
    String configFile = "/src/main/resources/db.propreties";
    private HikariDataSource dataSource = null;
    private Connection dbConnection;
   

    /**
     * Contructor for connection to mysql db.
     * @throws ConnectionException if error on co to db
     */
    private ConnectionDB() {
        super();
        
        HikariConfig config  = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&zeroDateTimeBehavior=convertToNull");
        config.setMaximumPoolSize(20);
        config.setUsername("admincdb");
        config.setPassword("qwerty1234");
        config.setConnectionTimeout(10000);
        dataSource = new HikariDataSource(config);
    }

    
    private Connection createConnection() {

        Connection connection = null;
        try {
          
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error Unable to Connect to Database");
        }
        return connection;
    }
    public static ConnectionDB getInstance() {
        if (ConnectionDB.instance == null) {
            ConnectionDB.instance = new ConnectionDB();
        }
        return ConnectionDB.instance;
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