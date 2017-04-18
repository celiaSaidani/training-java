package fr.ebiz.computerDatabase.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyDAO {

    private Statement statement = null;
    private JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * @param id
     * @return company
     */
    /*
     * public static Company getCompanyID(int id) { String selectCompanyByID =
     * "select * from company where id=" + Integer.toString(id); String name =
     * null; try {
     *
     * statement = c.getConnection(); ResultSet rs =
     * statement.executeQuery(selectCompanyByID); if (rs.next()) { name =
     * rs.getString("name"); } rs.close(); c.closeConnection();
     *
     * return new Company(id, name);
     *
     * } catch (Exception e) { logger.error("Error in function getCompanyID"); }
     * return new Company(); }
     */
    public ResultSet getCompanyID(int id) {
        String selectCompanyByID = "select * from company where id=" + Integer.toString(id);
        ResultSet rs = null;
        try {

            statement = c.getConnection();
            rs = statement.executeQuery(selectCompanyByID);
        } catch (Exception e) {
            logger.error("Error in function getCompanyID");
        }
        return rs;
    }

    /**
     * @return all company of data base
     */
    public ResultSet getAllCompany() {
        String selectAllCompany = "select * from company";
        ResultSet rs = null;
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllCompany);
        } catch (SQLException e) {
            logger.error("Error in function getAllCompany");
        }
        return rs;
    }

    /*
     * public static List<Company> getAllCompany() { String selectAllCompany =
     * "select * from company ;"; List<Company> allCompany = new ArrayList<>();
     * try { statement = c.getConnection(); ResultSet rs =
     * statement.executeQuery(selectAllCompany);
     *
     * while (rs.next()) { int idCompany = rs.getInt(companyId); String name =
     * rs.getString(companyName); allCompany.add(new Company(idCompany, name));
     * } rs.close(); c.closeConnection(); return allCompany;
     *
     * } catch (SQLException e) {
     * logger.error("Error in function getAllCompany"); } return allCompany; }
     */

    /**
     * @return all company of data base
     */

    public ResultSet getAllCompany(int start) {
        String selectAllCompany = "select * from company limit 10 offset " + start;
        ResultSet rs = null;

        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllCompany);
        } catch (SQLException e) {
            logger.error("Error in function getAllCompany");
        }
        return rs;
    }
    /*
     * public static List<Company> getAllCompany(int start) { String
     * selectAllCompany = "select * from company limit 10 offset "+start;
     * List<Company> allCompany = new ArrayList<>(); try { statement =
     * c.getConnection(); ResultSet rs =
     * statement.executeQuery(selectAllCompany);
     *
     * while (rs.next()) { int idCompany = rs.getInt(companyId); String name =
     * rs.getString(companyName); allCompany.add(new Company(idCompany, name));
     * } rs.close(); c.closeConnection(); return allCompany;
     *
     * } catch (SQLException e) {
     * logger.error("Error in function getAllCompany"); } return allCompany; }
     */

}
