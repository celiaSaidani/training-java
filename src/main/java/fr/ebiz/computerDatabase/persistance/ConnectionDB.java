package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionDB {

  // private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
  public static ConnectionDB instance = new ConnectionDB();
  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionDB.class);
  static final String configFile = "/db.propreties";
  private HikariDataSource dataSource = null;
  private Connection dbConnection;

  /**
   * Contructor for connection to mysql db.
   * @throws ConnectionException
   *           if error on co to db
   */
  private ConnectionDB() {
    super();

    HikariConfig config = new HikariConfig(configFile);
    dataSource = new HikariDataSource(config);
  }

  /**
   *
   * @return new connection
   */
  private Connection createConnection() {

    Connection connection = null;
    try {

      connection = dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
      LOGGER.error("Error Unable to Connect to Database");
    }
    return connection;
  }

  /**
   * @return instance of connection
   */
  public static ConnectionDB getInstance() {
    if (ConnectionDB.instance == null) {
      ConnectionDB.instance = new ConnectionDB();
    }
    return ConnectionDB.instance;
  }

  /**
   * @return connection
   */
  public Connection getConnection() {

    dbConnection = createConnection();
    return dbConnection;

  }

  /**
   * close connexion.
   * @return 0 if fine else 0
   */
  public int closeConnection() {
    try {
      dbConnection.close();
      dataSource.close();
    } catch (SQLException e) {
      LOGGER.error("Error Unable to close connection");
      return 1;
    }
    return 0;

  }
}