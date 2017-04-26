package fr.ebiz.computerDatabase.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.model.Computer;

public class ComputerDAO {

    private static Statement statement = null;
    private static PreparedStatement ps;
    private static JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();
    private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id" };
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
    private ComputerDAOMapper cpm= new ComputerDAOMapper();

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

    /*public ResultSet getAllComputer() {
        ResultSet rs = null;
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
        statement = c.getConnection();

        try {
            rs = statement.executeQuery(selectAllComputer);
        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return rs;

    }*/
    public List<Computer> getAllComputer() {
        ResultSet rs = null;
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
        statement = c.getConnection();
    
        try {
            rs = statement.executeQuery(selectAllComputer);
            return cpm.getAllComputer(rs);
        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return null;

    }
/*
    
    public ResultSet getAllComputer(int start, int end) {
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id limit "+ start+","+end;

        ResultSet rs = null;
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllComputer);

        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return rs;
    }*/
    /**
     * @return list off all computer
     */

    public List<Computer> getAllComputerPage(int start, int end) {
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id limit "+ start+","+end;

        ResultSet rs = null;
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllComputer);
            return cpm.getAllComputerMapperPage(rs);

        } catch (SQLException e) {
            logger.error("Error in function getAllComputer");
        }
        return null;
    }


    /*
    public ResultSet getComputerById(int id) {
        String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
                + " where computer.id=" + Integer.toString(id);
        ResultSet rs = null;
        try {

            statement = c.getConnection();
            rs = statement.executeQuery(selectComputerByid);

        } catch (Exception e) {
            logger.error("Error in function getCompanyById");
        }
        return rs;
    }*/
    
    /**
     * @param id
     * @return computer that have their id equal to id in parameter
     */
    public Computer getComputerById(int id) {
        String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
                + " where computer.id=" + Integer.toString(id);
        ResultSet rs = null;
        try {

            statement = c.getConnection();
            rs = statement.executeQuery(selectComputerByid);
            return cpm.getComputerByIdMapper(id, rs);
           

        } catch (Exception e) {
            logger.error("Error in function getCompanyById");
        }
        return  null;
    }


  /*
    public ResultSet getComputerByName(String name) {
        ResultSet rs = null;
        String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name= " + "'" + name + "'";
        try {
            statement = c.getConnection();
            rs = statement.executeQuery(selectComputeryByName);
        } catch (SQLException e) {
            logger.error("Error in function getComputerByName ");
        }
        return rs;

    }
    */
    /**
    * @param name
    * @return list of computer that have same name
    */
   public List<Computer> getComputerByName(String name) {
       ResultSet rs = null;
       String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
               + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name= " + "'" + name + "'";
       try {
           statement = c.getConnection();
           rs = statement.executeQuery(selectComputeryByName);
           return cpm.getComputerByNameMapper(rs);
       } catch (SQLException e) {
           logger.error("Error in function getComputerByName ");
       }
       return null;

   }
    
    public int CountTotalLine() {
        ResultSet rs = null;
        String selectAllComputer = "select count(1) from computer";
        int row_count = 0;
        statement = c.getConnection();

        try {
            rs = statement.executeQuery(selectAllComputer);
            while(rs.next())
            {
            row_count= rs.getInt("count(1)");
            }
            return row_count;
        } catch (SQLException e) {
            logger.error("Error in function getCount");
        }
        return 0;
  
        
    }

}
