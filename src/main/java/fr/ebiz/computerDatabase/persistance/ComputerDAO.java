package fr.ebiz.computerDatabase.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Computer;

public class ComputerDAO {

    private static Statement statement = null;
    private static PreparedStatement ps;
    private static JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();
    private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id" };
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * @param computer
     * @return 1 if the insert successful 0 else
     */
    public int insert(Computer computer) {

        String insertComputer = "insert into computer(name,introduced,discontinued,company_id) values(?,?,?,?)";

        try {

            ps = c.getConnectionP().prepareStatement(insertComputer);
            ps.setString(1, computer.getName());
            ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
            ps.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
            if (computer.getCompagnyId() == 0)
                ps.setNull(4, java.sql.Types.INTEGER);
            else
                ps.setInt(4, computer.getCompagnyId());
            if (ps.executeUpdate() == 1) {
                c.closeConnection();
                return 1;
            } else
                return 0;

        } catch (SQLException e) {
            logger.error("Error in function insert");
            return 0;
        }

    }

    /**
     * @param id of computer that we want delete
     * @return 1 if the delete successful 0 else
     */
    public int delete(int id) {
        String deleteComputer = "delete from computer where id= " + id;
        int delete = 0;

        try {
            statement = c.getConnection();
            delete = statement.executeUpdate(deleteComputer);
            c.closeConnection();
            if (delete == 1)
                return 1;
            else
                return 0;

        } catch (SQLException e) {
            logger.error("Error in function delete in");
        }
        return 0;
    }

    /**
     * @param computer
     * @return 1 if the update successful 0 else
     */
    public int update(Computer computer) {
        String updateComputer = "update computer set " + computerColumns[1] + "=? ," + computerColumns[2] + "=? ,"
                + computerColumns[3] + "=? ," + computerColumns[4] + "= ? where " + computerColumns[0] + "= ?";
        try {
            ps = c.getConnectionP().prepareStatement(updateComputer);
            ps.setString(1, computer.getName());
            ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
            ps.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
            if (computer.getCompagnyId() == 0)
                ps.setNull(4, java.sql.Types.INTEGER);
            else
                ps.setInt(4, computer.getCompagnyId());
            ps.setInt(5, computer.getId());
            if (ps.executeUpdate() == 1) {
                c.closeConnection();
                return 1;

            } else {
                c.closeConnection();
                return 0;
            }

        } catch (SQLException e) {
            logger.error("Error in function update");
        }
        return 0;
    }

    /**
     * @return list off all computer
     */
    /*
     * public static List<Computer> getAllComputer() {
     *
     * String selectAllComputer = "select * from computer"; statement =
     * c.getConnection(); ResultSet rs; List<Computer> allComputer = new
     * ArrayList<>(); try { rs = statement.executeQuery(selectAllComputer);
     *
     * while (rs.next()) { allComputer.add(getComputer(rs)); } rs.close();
     * c.closeConnection(); return allComputer; } catch (SQLException e) {
     * logger.error("Error in function getAllComputer"); } return allComputer;
     *
     * }
     */

    public ResultSet getAllComputer() {

        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
        statement = c.getConnection();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(selectAllComputer);
        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return rs;

    }


    /**
     * @return list off all computer
     */
    /*
     * public static List<Computer> getAllComputer(int start) {
     *
     * String selectAllComputer =
     * "select * from computer limit 100 offset "+start ; statement =
     * c.getConnection(); ResultSet rs; List<Computer> allComputer = new
     * ArrayList<>(); try { rs = statement.executeQuery(selectAllComputer);
     *
     * while (rs.next()) { allComputer.add(getComputer(rs)); } rs.close();
     * c.closeConnection(); return allComputer; } catch (SQLException e) {
     * logger.error("Error in function getAllComputer"); } return allComputer;
     *
     * }
     */

    public ResultSet getAllComputer(int start) {
        String selectAllComputer = "select * from computer limit 100 offset " + start;

        ResultSet rs = null;
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllComputer);

        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return rs;
    }

    /**
     * @param id
     * @return computer that have their id equal to id in parameter
     */
    public ResultSet getComputerById(int id) {
        String selectComputerByid = "select * from computer where id=" + Integer.toString(id);
        ResultSet rs = null;
        try {

            statement = c.getConnection();
            rs = statement.executeQuery(selectComputerByid);

        } catch (Exception e) {
            logger.error("Error in function getCompanyById");
        }
        return rs;
    }

    /*
     * public static Computer getComputerById(int id) { String
     * selectComputerByid = "select * from computer where id=" +
     * Integer.toString(id); try {
     *
     * statement = c.getConnection(); ResultSet rs =
     * statement.executeQuery(selectComputerByid); if (rs.next()) {
     *
     * return (getComputer(rs)); } rs.close(); c.closeConnection();
     *
     * } catch (Exception e) { logger.error("Error in function getCompanyById");
     * } return new Computer(); }
     */
    /**
     * @param name
     * @return list of computer that have same name
     */
    public ResultSet getComputerByName(String name) {
        ResultSet rs = null;
        String selectComputeryByName = "select * from computer where name= " + "'" + name + "'";
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectComputeryByName);
        } catch (SQLException e) {
            logger.error("Error in function getComputerByName ");
        }
        return rs;

    }

    /*
     * public static List<Computer> getComputerByName(String name) { String
     * selectComputeryByName = "select * from computer where name= " + "'" +
     * name + "'"; List<Computer> listComputer = new ArrayList<>();
     *
     * try { statement = c.getConnection(); ResultSet rs =
     * statement.executeQuery(selectComputeryByName);
     *
     * while (rs.next()) { listComputer.add(getComputer(rs));
     *
     * } return listComputer;
     *
     * } catch (SQLException e) {
     * logger.error("Error in function getComputerByName "); } return
     * listComputer; }
     */

}
