package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.ebiz.computerDatabase.Exception.DAOException;

public class ConnectionManager {

    // private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
    public static ConnectionManager instance = new ConnectionManager();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    static final String CONFIGFILE = "/db.propreties";
    private HikariDataSource dataSource = null;
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Contructor for connection to mysql db. if error on co to db
     */
    private ConnectionManager() {
        super();
        try {

            Class.forName(instance.JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            throw new RuntimeException("Impossible de charger jdbc driver");

        }
        HikariConfig config = new HikariConfig(CONFIGFILE);
        dataSource = new HikariDataSource(config);
    }

    /**
     * @return instance of connection
     */
    public static ConnectionManager getInstance() {
        if (ConnectionManager.instance == null) {
            ConnectionManager.instance = new ConnectionManager();
        }
        return ConnectionManager.instance;
    }

    /**
     * @return connection
     */
    public Connection getConnection() {
        Connection connection = connections.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connections.set(connection);
            } catch (SQLException e) {
                LOGGER.error("Error Unable to Connect to Database");
                throw new IllegalStateException(e);
            }
        }
        return connection;
    }

    public void startTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Error while starting transaction");
            throw new IllegalStateException(e);
        }
    }

    public boolean isTransactional() {
        try {
            return !getConnection().getAutoCommit();
        } catch (SQLException e) {
            LOGGER.error("Error while getting transaction state");
            throw new IllegalStateException(e);
        }
    }

    public void rollback() {
        Connection connection = connections.get();
        if (connection == null) {
            LOGGER.error("Cannot rollback non-existent transaction");
            throw new IllegalStateException("Cannot rollback non-existent transaction");
        }
        try {
            connection.rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Error while rolling back transaction");
            throw new IllegalStateException(e);
        }
    }

    public void commit() {
        Connection connection = connections.get();
 
        if (connection == null) {
            LOGGER.error("Cannot commit non-existent transaction");
            throw new IllegalStateException("Cannot commit non-existent transaction");
        }
        try {
            connection.commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Error while committing transaction");
            throw new IllegalStateException(e);
        }
    }

    public void closeConnection() {
        Connection connection = connections.get();
        if (connection == null) {
            LOGGER.error("Cannot close non-existent transaction");
            throw new IllegalStateException("Cannot close non-existent transaction");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error while closing connection");
            throw new IllegalStateException(e);
        } finally {
            connections.remove();
        }
    }

    public void closeObjects(Statement st) throws DAOException {
        closeObjects(st, null);
    }

    public void closeObjects(Statement st, ResultSet rs) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

}