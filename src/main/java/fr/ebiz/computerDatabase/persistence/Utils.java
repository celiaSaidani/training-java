package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * close statement.
     *
     * @param st statement
     * @throws DAOException for sql exceptions
     */

    public static void closeObjects(Statement st) throws DAOException {
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

    public static void closeObjects(Statement st, ResultSet rs) throws DAOException {
        closeObjects(st);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }

        }
    }

    /**
     *
     * @param dataSource to close
     * @param connection to close
     */

    public static void closeConnection(DataSource dataSource, Connection connection) {
        try {
            DataSourceUtils.doCloseConnection(connection, dataSource);
        } catch (SQLException e) {
            LOGGER.error("Unable to close connexion");
        }
    }

}