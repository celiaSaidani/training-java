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

    public static ConnectionManager instance = new ConnectionManager();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    static final String CONFIGFILE = "/db.propreties";
    private HikariDataSource dataSource = null;
    private ThreadLocal<Connection> connections = new ThreadLocal<>();

    /* Private class Singleton Holder */
    private static class SingletonManagerHolder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    /**
     * @return ConnectionDB Singleton
     */
    public static ConnectionManager getInstance() {
        return SingletonManagerHolder.INSTANCE;
    }

    /**
     * Contructor for connection to mysql db.
     */
    private ConnectionManager() {
        HikariConfig config = new HikariConfig(CONFIGFILE);
        dataSource = new HikariDataSource(config);
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

    /**
     * start transaction.
     */
    public void startTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Error while starting transaction");
            throw new IllegalStateException(e);
        }
    }

    /**
     * check if it's transactional state.
     * @return true or false
     */
    public boolean isTransactional() {
        try {
            return !getConnection().getAutoCommit();
        } catch (SQLException e) {
            LOGGER.error("Error while getting transaction state");
            throw new IllegalStateException(e);
        }
    }

    /**
     * rollback transaction.
     */

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

    /**
     * commit transaction.
     */

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

    /**
     * close connection.
     */
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

    /**
     * close statement.
     * @param st statement
     * @throws DAOException for sql exceptions
     */

    public void closeObjects(Statement st) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    /**
     * @param st statement
     * @param rs result
     * @throws DAOException for sql exceptions
     */

    public void closeObjects(Statement st, ResultSet rs) throws DAOException {
        closeObjects(st);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

}