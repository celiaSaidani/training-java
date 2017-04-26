package fr.ebiz.computerDatabase.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Company;

public class CompanyDAO {

    private Statement statement = null;
    private JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
    public CompanyDAOMapper cdm= new CompanyDAOMapper();

    /**
     * @param id
     * @return company
     */
    public Company getCompanyID(int id) {
        String selectCompanyByID = "select * from company where id=" + Integer.toString(id);
        ResultSet rs = null;
        try {

            statement = c.getConnection();
            rs = statement.executeQuery(selectCompanyByID);
         
        } catch (Exception e) {
            logger.error("Error in function getCompanyID");
        }
        return cdm.getCompanyID(id, rs);
    }

    
    
    /**
     * @return all company of data base
     */
    public List<Company> getAllCompany() {
        String selectAllCompany = "select * from company";
        ResultSet rs = null;
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllCompany);
        } catch (SQLException e) {
            logger.error("Error in function getAllCompany");
        }
        return cdm.getAllCompany(rs);
    }

    /**
     * @return all company of data base
     */

    public List<Company> getAllCompany(int start) {
        String selectAllCompany = "select * from company limit 10 offset " + start;
        ResultSet rs = null;

        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllCompany);
        } catch (SQLException e) {
            logger.error("Error in function getAllCompany");
        }
        return  cdm.getAllCompanyPage(rs);
    }
}
