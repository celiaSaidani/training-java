package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.Exception.DAOException;
import fr.ebiz.computerDatabase.model.Company;

public class CompanyDAO {

    private Statement statement = null;
    private final String companyName = "name";
    private final String companyId = "id";
    private Connection connection = null;
    // private JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * @param id
     * @return company
     */
    public Company getCompanyID(int id) throws DAOException {
        String selectCompanyByID = "select * from company where id=" + Integer.toString(id);
        ResultSet rs = null;

        try {
            connection = Transaction.getConnetion();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectCompanyByID);
            Company comp = mappCompany(id, rs);
            return comp;

        } catch (SQLException e) {
            logger.error("[Error DAO] in function getCompanyID");
            throw new DAOException("[DAO EXCEPTION] enable to find company by id in dataBase");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                throw new DAOException("enable to close resultSet");
            }
        }
    }

    /**
     * @return all company of data base
     */
    public List<Company> getAllCompany() throws DAOException {
        String selectAllCompany = "select * from company";
        ResultSet rs = null;
        try {
            connection = Transaction.getConnetion();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllCompany);
            List<Company> compL = mappCompanyL(rs);
            return compL;

        } catch (SQLException e) {
            logger.error("[Error DAO] in function getAllCompany");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company from dataBase");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                throw new DAOException("enable to close resultSet");
            }
        }
    }

    /**
     * @return all company of data base
     */

    public List<Company> getAllCompany(int start) throws DAOException {
        String selectAllCompany = "select * from company limit 10 offset " + start;
        ResultSet rs = null;

        try {
            connection = Transaction.getConnetion();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllCompany);
            List<Company> compL = mappCompanyL(rs);

            return compL;

        } catch (SQLException e) {
            logger.error("[Error DAO] in function getAllCompany by limit");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company  by limit from dataBase");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                throw new DAOException("enable to close resultSet");
            }
        }

    }

    public void delete(int id) throws DAOException {
        String deleteCompany = " delete from company where company.id = " + id;
        
        try {
            connection = ConnectionDB.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeQuery(deleteCompany);
            statement.close();
        } catch (SQLException e) {
            logger.error("[Error DAO] in function delete company");
            throw new DAOException("[DAO EXCEPTION] Impossible to delete this company from dataBase");
        }

    }

    private Company mappCompany(int id, ResultSet rs) {
        String name = null;
        try {
            if (rs.next()) {
                name = rs.getString("name");
            }
            return new Company(id, name);
        } catch (SQLException e) {
            logger.error("[Error DAO] in function mappCompany");
        }

        return new Company();
    }

    private List<Company> mappCompanyL(ResultSet rs) {

        List<Company> allCompany = new ArrayList<>();

        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                Company comp = new Company(idCompany, name);
                allCompany.add(comp);
            }
        } catch (SQLException e) {
            logger.error("[Error DAO] in function mappCompanyL");
            System.err.println("can't mapp company result set");

        }
        return allCompany;
    }

}
