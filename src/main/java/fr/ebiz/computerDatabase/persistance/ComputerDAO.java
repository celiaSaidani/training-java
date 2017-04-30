package fr.ebiz.computerDatabase.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Computer;

public class ComputerDAO {

    
   
  
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
        PreparedStatement ps=null;

        try {
            ConnectionDB c = ConnectionDB.getInstance();
            ps = c.getConnectionP().prepareStatement(insertComputer);
            ps.setString(1, computer.getName());
            ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
            ps.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
            if (computer.getCompagnyId() == 0)
                ps.setNull(4, java.sql.Types.INTEGER);
            else
                ps.setInt(4, computer.getCompagnyId());
            if (ps.executeUpdate() == 1) {
                ps.close();
                c.closeConnection();
                return 1;
            } else{
                c.closeConnection();
                return 0;
               
            }
                
            
           
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
        Statement statement = null;

        try {
            ConnectionDB c = ConnectionDB.getInstance();
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
        PreparedStatement ps=null;
        String updateComputer = "update computer set " + computerColumns[1] + "=? ," + computerColumns[2] + "=? ,"
                + computerColumns[3] + "=? ," + computerColumns[4] + "= ? where " + computerColumns[0] + "= ?";
        System.err.println(computer.getCompagnyId());
        try {
            ConnectionDB c = ConnectionDB.getInstance();
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
        	e.printStackTrace();
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
        Statement statement=null;
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
        ConnectionDB c = ConnectionDB.getInstance();
        statement = c.getConnection();
    
        try {
            rs = statement.executeQuery(selectAllComputer);
            List<Computer> cp= cpm.getAllComputer(rs);
            if (rs != null && rs.getStatement() != null && c != null) {
               rs.close();
               c.closeConnection();
             
            }
            return cp;
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
        Statement statement=null;
        try {
            ConnectionDB c = ConnectionDB.getInstance();
            statement = c.getConnection();
            rs = statement.executeQuery(selectAllComputer);
            List<Computer> cp= cpm.getAllComputerMapperPage(rs);
            if (rs != null && rs.getStatement() != null && c != null) {
                rs.close();
                c.closeConnection();
             }
            return cp;

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
        Statement statement=null;
        String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
                + " where computer.id=" + Integer.toString(id);
        ResultSet rs = null;
        try {
            ConnectionDB c = ConnectionDB.getInstance();
            statement = c.getConnection();
            rs = statement.executeQuery(selectComputerByid);
            Computer cp= cpm.getComputerByIdMapper(id, rs);
            if (rs != null && rs.getStatement() != null && c != null) {
                rs.close();
                c.closeConnection();
              
             }
            return cp;
           

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
       Statement statement=null;
       String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
               + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name= " + "'" + name + "'";
       try {
           ConnectionDB c = ConnectionDB.getInstance();
           statement = c.getConnection();
           rs = statement.executeQuery(selectComputeryByName);
           List<Computer> cp=cpm.getComputerByNameMapper(rs);
           if (rs != null && rs.getStatement() != null && c != null) {
               rs.close();
               c.closeConnection();
             
            }
           return cp;
       } catch (SQLException e) {
           logger.error("Error in function getComputerByName ");
       }
       return null;

   }
   
   public List<Computer> Serach(String name, int start, int end) {
       ResultSet rs = null;
       Statement statement=null;
       String search = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
               + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name like " + "'%" +name+ "%'"
                       + "or company.name like " + "'%" +name+ "%'"
               		+ "limit "+ start+","+end;
       try {
           ConnectionDB c = ConnectionDB.getInstance();
           statement = c.getConnection();
           rs = statement.executeQuery(search);
           List<Computer> cp=cpm.SearchMapper(rs);
           if (rs != null && rs.getStatement() != null && c != null) {
               rs.close();
               c.closeConnection();
             
            }
           return cp;
       } catch (SQLException e) {
           logger.error("Error in function getComputerByName ");
       }
       return null;

   }
    
    public int CountTotalLine() {
        ResultSet rs = null;
        Statement statement=null;
        String selectAllComputer = "select count(1) from computer";
        int row_count = 0;
        ConnectionDB c = ConnectionDB.getInstance();
        statement = c.getConnection();

        try {
            rs = statement.executeQuery(selectAllComputer);
            while(rs.next())
            {
            row_count= rs.getInt("count(1)");
            }
            if (rs != null && rs.getStatement() != null && c != null) {
                rs.close();
                c.closeConnection();
              
             }
            return row_count;
        } catch (SQLException e) {
            logger.error("Error in function getCount");
        }
        return 0;
  
        
    }
    
    public int CountTotalLine(String search) {
        ResultSet rs = null;
        Statement statement=null;
        String selectAllComputer = "select count(1) from computer left join company on computer.company_id = company.id where computer.name like " + "'%" +search+ "%'";
        int row_count = 0;
        ConnectionDB c = ConnectionDB.getInstance();
        statement = c.getConnection();

        try {
            rs = statement.executeQuery(selectAllComputer);
            while(rs.next())
            {
            row_count= rs.getInt("count(1)");
            }
            if (rs != null && rs.getStatement() != null && c != null) {
                rs.close();
                c.closeConnection();
              
             }
            return row_count;
        } catch (SQLException e) {
            logger.error("Error in function getCount");
        }
        return 0;
  
        
    }


    public List<Computer> getComputerOrder(int start, int end, String reqBy,String name) {
        ResultSet rs = null;
        Statement statement=null;
        String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company "
                + "on computer.company_id = company.id"
                + " ORDER BY "+ name +" "+ reqBy + " limit "+ start+ "," +end;
     
        try {
            ConnectionDB c = ConnectionDB.getInstance();
          System.out.println(orderBy);
            statement = c.getConnection();
            rs = statement.executeQuery(orderBy);
      
           
            List<Computer> cp=cpm.OrderByMapper(rs);
            if (rs != null && rs.getStatement() != null && c != null) {
                rs.close();
                c.closeConnection();
              
             }
            return cp;
        } catch (SQLException e) {
            logger.error("Error in function order ");
        }
        return null;
    }


}
