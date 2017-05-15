package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.Exception.DAOException;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;

public class ComputerDAO {

    private static String[] computerColumns = {"id", "name", "introduced", "discontinued", "company_id",
            "companyName" };

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    private ConnectionManager cm = ConnectionManager.getInstance();
    /**
     * @param computer valid computer object
     * @throws DAOException for sql Exceptions
     */
    public void insert(Computer computer) throws DAOException {

        String insertComputer = "insert into computer(name,introduced,discontinued,company_id) values(?,?,?,?)";
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = cm.getConnection();
            statement = connection.prepareStatement(insertComputer);
            statement.setString(1, computer.getName());
            statement.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
            statement.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
            if (computer.getCompagnyId() == 0) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setInt(4, computer.getCompagnyId());
            }
            statement.executeUpdate();
            LOGGER.info("insert computer successful");

        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDao] in function insert");
            throw new DAOException("[DAO EXCEPTION] enable to insert computer in data base, id company not found");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement);
        }

    }

    /**
     * @param id of computer that we want delete
     * @throws DAOException for sql Exceptions
     */
    public void delete(int id) throws DAOException {
        String deleteComputer = "delete from computer where id= " + id;
        int delete = 0;
        Statement statement = null;
        Connection connection = null;

        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            delete = statement.executeUpdate(deleteComputer);
            if (delete == 1) {
                LOGGER.info("delete computer successful");
            } else {
                LOGGER.error("computer not found, enable to delete");
            }
        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDao] in function delete");
            throw new DAOException("[DAO EXCEPTION] Impossible to delete computer delete from database");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement);
        }
    }

    /**
     * @param computer valid computer object to update
     * @throws DAOException for sql Exceptions
     * @return 1 if the update successful 0 else
     */
    public int update(Computer computer) throws DAOException {
        PreparedStatement statement = null;
        Connection connection = null;
        String updateComputer = "update computer set " + computerColumns[1] + "=? ," + computerColumns[2] + "=? ,"
                + computerColumns[3] + "=? ," + computerColumns[4] + "= ? where " + computerColumns[0] + "= ?";
        try {
            connection = cm.getConnection();
            statement = connection.prepareStatement(updateComputer);
            statement.setString(1, computer.getName());
            statement.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
            statement.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
            if (computer.getCompagnyId() == 0) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setInt(4, computer.getCompagnyId());
            }
            statement.setInt(5, computer.getId());
            if (statement.executeUpdate() == 1) {
                LOGGER.info("update computer successful");
                return 1;
            } else {
                LOGGER.error("Impossible to update: The computer is not found");
            }

        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDao] in function update");
            throw new DAOException("[DAO EXCEPTION] Impossible to update computer delete from database");
        } finally {
           if (!cm.isTransactional()) {
               cm.closeConnection();
           }
           cm.closeObjects(statement);
        }
        return 0;
    }

    /**
     * @return list of computer
     * @throws DAOException for sql exceptions
     */
    public List<Computer> getAllComputer() throws DAOException {
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";

        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllComputer);
            List<Computer> cp = mappDaoL(rs);
            return cp;
        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDao] in function getAllComputer");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @param start start page
     * @param end end page
     * @throws DAOException for sql exceptions
     * @return list off all computer
     */

    public List<Computer> getAllComputerPage(int start, int end) throws DAOException {
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id limit "
                + start + "," + end;

        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllComputer);
            List<Computer> cp = mappDaoL(rs);
            return cp;

        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDao] in function getAllComputer(int start, int end)");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @param id id of computer
     * @throws DAOException for sql error
     * @return computer that have their id equal to id in parameter
     */
    public Computer getComputerById(int id) throws DAOException {
        Statement statement = null;
        String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
                + " where computer.id=" + Integer.toString(id);
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectComputerByid);
            Computer cp = mappDao(rs);

            return cp;

        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDAO] in function getCompanyById");
            throw new DAOException("[DAO EXCEPTION] error in function getComputerById");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }

    }

    /**
     * @param name of computer
     * @throws DAOException for sql error
     * @return list of computer that have same name
     */
    public List<Computer> getComputerByName(String name) throws DAOException {
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name= "
                + "'" + name + "'";
        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(selectComputeryByName);
            List<Computer> cp = mappDaoL(rs);
            return cp;
        } catch (SQLException e) {
            LOGGER.error("[Error ComputerDAO] in function getComputerByName ");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer by Name from dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @param name of computer
     * @param start page
     * @param end page
     * @return list of computer
     * @throws DAOException for sql exceptions
     */
    public List<Computer> search(String name, int start, int end) throws DAOException {
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String search = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name like "
                + "'%" + name + "%'" + "or company.name like " + "'%" + name + "%'" + "limit " + start + "," + end;
        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(search);
            List<Computer> cp = mappDaoL(rs);
            return cp;
        } catch (SQLException e) {
            LOGGER.error("[Error DAO] in function getComputerByName ");
            throw new DAOException("[DAO EXCEPTION] Impossible to find computer in dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @return number of line
     * @throws DAOException for sql exceptions
     */
    public int countTotalLine() throws DAOException {
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String selectAllComputer = "select count(1) from computer";
        int rowCount = 0;

        try {
            connection = cm.getConnection();
            System.out.println(connection);
            statement = connection.createStatement();
            System.out.println(statement);
            rs = statement.executeQuery(selectAllComputer);
            while (rs.next()) {
                rowCount = rs.getInt("count(1)");
            }
            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("[Error DAO] in function getCountLine");
            throw new DAOException("Impossible to count computer in dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }

    }

    /**
     * @param search name of computer
     * @return number of line in database
     * @throws DAOException for sql errors
     */
    public int countTotalLine(String search) throws DAOException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        String selectAllComputer = "select count(1) from computer left join company on computer.company_id = company.id where computer.name like "
                + "'%" + search + "%' OR company.name like " + "'%" + search + "%' ";
        int rowcount = 0;

        try {
            connection = cm.getConnection();

            statement = connection.createStatement();
            rs = statement.executeQuery(selectAllComputer);

            while (rs.next()) {
                rowcount = rs.getInt("count(1)");
            }
            return rowcount;
        } catch (SQLException e) {
            LOGGER.error("[Error DAO] in function CountTotalLine");
            throw new DAOException("Impossible to count computer for this search in dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }

    }

    /**
     * @param start page
     * @param end page
     * @param reqBy ASC or DESC
     * @param name of computer
     * @return list of computer
     * @throws DAOException of sql exceptions
     */
    public List<Computer> getComputerOrderBy(int start, int end, String reqBy, String name) throws DAOException {
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null;
        String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company "
                + "on computer.company_id = company.id" + " ORDER BY " + name + " " + reqBy + " limit " + start + ","
                + end;

        try {
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(orderBy);

            List<Computer> cp = mappDaoL(rs);
            return cp;
        } catch (SQLException e) {
            LOGGER.error("[Error DAO] in function getComputerOrderBy");
            throw new DAOException("Impossible to find computer for this order in dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @param start page
     * @param end page
     * @param reqBy ASC or DESC
     * @param search of computer
     * @param reqorder name of coulumn
     * @return list of computer
     * @throws DAOException of sql exceptions
     */
    public List<Computer> getComputerOrderBy(int start, int end, String reqorder, String reqBy, String search)
            throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;
        String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company "
                + "on computer.company_id = company.id where computer.name like '%" + search
                + "%' or company.name like '%" + search + "%' " + "ORDER BY " + reqorder + " " + reqBy + " limit "
                + start + "," + end;
        try {
            System.err.println("DAO " + reqorder + " " + reqBy + " " + search + " " + start + " " + end);
            System.out.println(orderBy);
            connection = cm.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(orderBy);

            List<Computer> cp = mappDaoL(rs);
            System.out.println(cp.size());
            return cp;
        } catch (SQLException e) {
            LOGGER.error("[Error DAO] in function getComputerOrderBy");
            throw new DAOException("Impossible to find computer for this order in dataBase");
        } finally {
            if (!cm.isTransactional()) {
                cm.closeConnection();
            }
            cm.closeObjects(statement, rs);
        }
    }

    /**
     * @param rs resultSet to map
     * @return list of computer
     */
    private List<Computer> mappDaoL(ResultSet rs) {

        List<Computer> allComputer = new ArrayList<>();
        try {
            while (rs.next()) {
                allComputer.add(getComputer(rs));
            }
            return allComputer;

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("[Error DAO] can't mapp resultset to list of computer");
        }
        return allComputer;
    }

    /**
     * @param rs resultSet to mapp
     * @return computer object
     */
    private Computer mappDao(ResultSet rs) {

        try {
            if (rs.next()) {
                return (getComputer(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("[Error DAO] can't mapp resultset to computer");
        }
        return new Computer();

    }

    /**
     * @param rs Result set
     * @return Computer object
     */
    private Computer getComputer(ResultSet rs) {
        LocalDateTime inDate = null;
        LocalDateTime outDate = null;
        int idComputer;
        try {
            idComputer = rs.getInt(computerColumns[0]);
            String nameComputer = rs.getString(computerColumns[1]);
            String introduced = rs.getString(computerColumns[2]);

            if (introduced != null) {

                inDate = LocalDateTime.parse(introduced, formatter);
            }
            String discontinued = rs.getString(computerColumns[3]);
            if (discontinued != null) {

                outDate = LocalDateTime.parse(discontinued, formatter);
            }
            int companyId = rs.getInt(computerColumns[4]);
            String companyName = rs.getString(computerColumns[5]);
            Computer comp = new Computer(idComputer, nameComputer, inDate, outDate, new Company(companyId, companyName));
            return comp;

        } catch (SQLException e) {
            LOGGER.error("[Error DAO] can't get computer from data base");

        }
        return new Computer();
    }

}
