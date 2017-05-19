package fr.ebiz.computerDatabase.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.exception.DAOException;
import fr.ebiz.computerDatabase.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAO {

    private final String companyName = "name";
    private final String companyId = "id";
 //   private Utils cm = Utils.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
    @Autowired
    private HikariDataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param id of company
     * @return company
     * @throws DAOException for sql error
     */
    public Company getCompanyID(int id) throws DAOException {
        String selectCompanyByID = "select * from company where id= ? ";
        try {
           return jdbcTemplate.queryForObject(selectCompanyByID,new Object[]{id}, new CompanyDaoMapper());
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function getCompanyID");
            throw new DAOException("[DAO EXCEPTION] enable to find company by id in dataBase");
        }
    }

    /**
     * @return all company of data base
     * @throws DAOException for sql error
     */
    public List<Company> getAllCompany() throws DAOException {
        String selectAllCompany = "select * from company";
       /* ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        try {
            //connection = cm.getConnection();
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllCompany);
            List<Company> compL = mappCompanyL(rs);
            return compL;

        } catch (SQLException e) {
            LOG.error("[Error DAO] in function getAllCompany");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company from dataBase");
        } finally {
            if (!DataSourceUtils.isConnectionTransactional(connection, dataSource)) {
                Utils.closeConnection(dataSource, connection);
            }
            Utils.closeObjects(statement, rs);
        }*/
        try {
            return jdbcTemplate.query(selectAllCompany, new CompanyDaoMapper());
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function getCompanyID");
            throw new DAOException("[DAO EXCEPTION] enable to find company by id in dataBase");
        }
    }

    /**
     * @param start start
     * @return all company of data base
     * @throws DAOException for sql error
     */

    public List<Company> getAllCompany(int start) throws DAOException {
        String selectAllCompany = "select * from company limit 10 offset ? ";
       /* ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        try {
            // connection = cm.getConnection();
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllCompany);
            List<Company> compL = mappCompanyL(rs);
            return compL;
        } catch (SQLException e) {
            LOG.error("[Error DAO] in function getAllCompany by limit");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company  by limit from dataBase");
        } finally {
            if (!DataSourceUtils.isConnectionTransactional(connection, dataSource)) {
                Utils.closeConnection(dataSource, connection);
            }
            Utils.closeObjects(statement, rs);
        }*/
        try {
            return jdbcTemplate.query(selectAllCompany, new Object[]{start},new CompanyDaoMapper());
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function getAllCompany by limit");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company  by limit from dataBase");
        }

    }

    /**
     * @param id of computer to delete
     * @throws DAOException for sql exceptions
     */
    public void delete(int id) throws DAOException {
        String deleteCompany = " delete from company where company.id = ?";
       /* Statement statement = null;
        Connection connection = null;
        try {
            //connection = Utils.getInstance().getConnection();
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.createStatement();
            statement.executeQuery(deleteCompany);
            statement.close();
        } catch (SQLException e) {
            LOG.error("[Error DAO] in function delete company");
            throw new DAOException("[DAO EXCEPTION] Impossible to delete this company from dataBase");
        } finally {
            if (!DataSourceUtils.isConnectionTransactional(connection, dataSource)) {
                Utils.closeConnection(dataSource, connection);
            }
            Utils.closeObjects(statement);
        }*/
       try {
           jdbcTemplate.update(deleteCompany, id);
       }
       catch (DataAccessException e) {
           LOG.error("[Error DAO] in function delete company");
           throw new DAOException("[DAO EXCEPTION] Impossible to delete this company from dataBase");
       }


    }

    /**
     * @param id of company
     * @param rs resultSet to map
     * @return company object
     */
    private Company mappCompany(int id, ResultSet rs) {
        String name = null;
        try {
            if (rs.next()) {
                name = rs.getString("name");
            }
            return new Company(id, name);
        } catch (SQLException e) {
            LOG.error("[Error DAO] in function mappCompany");
        }

        return new Company();
    }

    /**
     * @param rs resultSet to map
     * @return company object
     */
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
            LOG.error("[Error DAO] in function mappCompanyL");
            System.err.println("can't mapp company result set");

        }
        return allCompany;
    }
}
