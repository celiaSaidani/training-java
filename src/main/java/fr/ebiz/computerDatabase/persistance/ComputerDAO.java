package fr.ebiz.computerDatabase.persistance;

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

  private static String[] computerColumns = { "id", "name", "introduced", "discontinued",
      "company_id", "companyName" };
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
  private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

  /**
   * @param computer
   * @return 1 if the insert successful 0 else
   */
  public void insert(Computer computer) throws DAOException {

    String insertComputer = "insert into computer(name,introduced,discontinued,company_id) values(?,?,?,?)";
    PreparedStatement ps = null;
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      ps = c.getConnectionP().prepareStatement(insertComputer);
      ps.setString(1, computer.getName());
      ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
      ps.setString(3,
          computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
      if (computer.getCompagnyId() == 0) {
        ps.setNull(4, java.sql.Types.INTEGER);
      } else
        ps.setInt(4, computer.getCompagnyId());
      ps.executeUpdate();
      logger.info("insert computer successful");

    } catch (SQLException e) {
      logger.error("[Error ComputerDao] in function insert");
      throw new DAOException(
          "[DAO EXCEPTION] enable to insert computer in data base, id company not found");
    } finally {
      try {
        if ((ps != null) && (c != null)) {
          ps.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }

  }

  /**
   * @param id
   *          of computer that we want delete
   * @return 1 if the delete successful 0 else
   */
  public void delete(int id) throws DAOException {
    String deleteComputer = "delete from computer where id= " + id;
    int delete = 0;
    Statement statement = null;
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      delete = statement.executeUpdate(deleteComputer);
      if (delete == 1)
        logger.info("delete computer successful");
      else
        logger.error("computer not found, enable to delete");

    } catch (SQLException e) {
      logger.error("[Error ComputerDao] in function delete");
      throw new DAOException("[DAO EXCEPTION] Impossible to delete computer delete from database");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  /**
   * @param computer
   * @return 1 if the update successful 0 else
   */
  public int update(Computer computer) throws DAOException {
    PreparedStatement ps = null;
    String updateComputer = "update computer set " + computerColumns[1] + "=? ,"
        + computerColumns[2] + "=? ," + computerColumns[3] + "=? ," + computerColumns[4]
        + "= ? where " + computerColumns[0] + "= ?";
    ConnectionDB c = null;
    try {
      c = ConnectionDB.getInstance();
      ps = c.getConnectionP().prepareStatement(updateComputer);
      ps.setString(1, computer.getName());
      ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
      ps.setString(3,
          computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
      if (computer.getCompagnyId() == 0)
        ps.setNull(4, java.sql.Types.INTEGER);
      else
        ps.setInt(4, computer.getCompagnyId());
      ps.setInt(5, computer.getId());
      if (ps.executeUpdate() == 1) {
        logger.info("update computer successful");
        return 1;
      } else {
        logger.error("Impossible to update: The computer is not found");
      }

    } catch (SQLException e) {
      logger.error("[Error ComputerDao] in function update");
      throw new DAOException("[DAO EXCEPTION] Impossible to update computer delete from database");
    } finally {
      try {
        if ((ps != null) && (c != null)) {
          ps.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
    return 0;
  }

  public List<Computer> getAllComputer() throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectAllComputer);
      List<Computer> cp = mappDaoL(rs);
      return cp;
    } catch (SQLException e) {
      logger.error("[Error ComputerDao] in function getAllComputer");
      throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  /**
   * @return list off all computer
   */

  public List<Computer> getAllComputerPage(int start, int end) throws DAOException {
    String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id limit "
        + start + "," + end;

    ResultSet rs = null;
    Statement statement = null;
    ConnectionDB c = null;
    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectAllComputer);
      List<Computer> cp = mappDaoL(rs);
      return cp;

    } catch (SQLException e) {
      logger.error("[Error ComputerDao] in function getAllComputer(int start, int end)");
      throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  /**
   * @param id
   * @return computer that have their id equal to id in parameter
   */
  public Computer getComputerById(int id) throws DAOException {
    Statement statement = null;
    String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
        + " where computer.id=" + Integer.toString(id);
    ResultSet rs = null;
    ConnectionDB c = null;
    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectComputerByid);
      Computer cp = mappDao(rs);

      return cp;

    } catch (SQLException e) {
      logger.error("[Error ComputerDAO] in function getCompanyById");
      throw new DAOException("[DAO EXCEPTION] error in function getComputerById");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }

  }

  /**
   * @param name
   * @return list of computer that have same name
   */
  public List<Computer> getComputerByName(String name) throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    ConnectionDB c = null;
    String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name= "
        + "'" + name + "'";
    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectComputeryByName);
      List<Computer> cp = mappDaoL(rs);
      return cp;
    } catch (SQLException e) {
      logger.error("[Error ComputerDAO] in function getComputerByName ");
      throw new DAOException(
          "[DAO EXCEPTION] Impossible to get All computer by Name from dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  public List<Computer> Serach(String name, int start, int end) throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    ConnectionDB c = null;
    String search = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name like "
        + "'%" + name + "%'" + "or company.name like " + "'%" + name + "%'" + "limit " + start + ","
        + end;
    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(search);
      List<Computer> cp = mappDaoL(rs);
      return cp;
    } catch (SQLException e) {
      logger.error("[Error DAO] in function getComputerByName ");
      throw new DAOException("[DAO EXCEPTION] Impossible to find computer in dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  public int CountTotalLine() throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    String selectAllComputer = "select count(1) from computer";
    int row_count = 0;
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectAllComputer);
      while (rs.next()) {
        row_count = rs.getInt("count(1)");
      }
      return row_count;
    } catch (SQLException e) {
      logger.error("[Error DAO] in function getCountLine");
      throw new DAOException("Impossible to count computer in dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }

  }

  public int CountTotalLine(String search) throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    String selectAllComputer = "select count(1) from computer left join company on computer.company_id = company.id where computer.name like "
        + "'%" + search + "%'";
    int row_count = 0;
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(selectAllComputer);
      while (rs.next()) {
        row_count = rs.getInt("count(1)");
      }
      return row_count;
    } catch (SQLException e) {
      logger.error("[Error DAO] in function CountTotalLine");
      throw new DAOException("Impossible to count computer for this search in dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }

  }

  public List<Computer> getComputerOrderBy(int start, int end, String reqBy, String name)
      throws DAOException {
    ResultSet rs = null;
    Statement statement = null;
    String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
        + "company.id as company_id, company.name as companyName from computer left join company "
        + "on computer.company_id = company.id" + " ORDER BY " + name + " " + reqBy + " limit "
        + start + "," + end;
    ConnectionDB c = null;

    try {
      c = ConnectionDB.getInstance();
      statement = c.getConnection();
      rs = statement.executeQuery(orderBy);

      List<Computer> cp = mappDaoL(rs);
      return cp;
    } catch (SQLException e) {
      logger.error("[Error DAO] in function getComputerOrderBy");
      throw new DAOException("Impossible to find computer for this order in dataBase");
    } finally {
      try {
        if ((statement != null) && (c != null)) {
          statement.close();
          c.closeConnection();
        }

      } catch (SQLException e) {
        throw new DAOException("enable to close connection");
      }
    }
  }

  private List<Computer> mappDaoL(ResultSet rs) {

    List<Computer> allComputer = new ArrayList<>();
    try {
      while (rs.next()) {
        allComputer.add(getComputer(rs));
      }
      return allComputer;

    } catch (SQLException e) {
      logger.error("[Error DAO] can't mapp resultset to list of computer");
    }
    return allComputer;
  }

  private Computer mappDao(ResultSet rs) {

    try {
      if (rs.next()) {
        return (getComputer(rs));
      }
    } catch (SQLException e) {
      logger.error("[Error DAO] can't mapp resultset to computer");
    }
    return new Computer();

  }

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
      Computer comp = new Computer(idComputer, nameComputer, inDate, outDate,
          new Company(companyId, companyName));

      return comp;

    } catch (SQLException e) {
      logger.error("[Error DAO] can't get computer from data base");

    }
    return new Computer();
  }

}
