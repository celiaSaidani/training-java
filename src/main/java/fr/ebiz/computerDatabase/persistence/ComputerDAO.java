package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.exception.DAOException;
import fr.ebiz.computerDatabase.model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ComputerDAO  extends JpaRepository<Computer,Long>{

    public static String[] computerColumns = {"id", "name", "introduced", "discontinued", "company_id",
            "companyName"};
    public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
  //  @Autowired
   // private JdbcTemplate jdbcTemplate;

    Page findComputerByNameContainingOrCompany_NameContaining(String ComputerName, String companyName, Pageable pageRequest);

    /**
     * @param computer valid computer object
     * @throws DAOException for sql Exceptions

    public void insert(Computer computer) throws DAOException {

        try {
            jdbcTemplate.update(insertComputer, computer.getName(),
                    computer.getDateIN(), computer.getDateOut(),
                    computer.getCompagnyId());
            LOGGER.info("insert computer successful");

        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDao] in function insert");
            throw new DAOException("[DAO EXCEPTION] enable to insert computer in data base, id company not found");
        }
    }

    /**
     * @param //id of computer that we want delete
     * @throws DAOException for sql Exceptions

    public void delete(int id) throws DAOException {
        String deleteComputer = "delete from computer where id= ? ";

        try {
            this.jdbcTemplate.update(deleteComputer, id);
            LOGGER.info("delete computer successful");
        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDao] in function delete");
            throw new DAOException("[DAO EXCEPTION] Impossible to delete computer delete from database");
        }
    }

    /**
     * @param computer valid computer object to update
     * @return 1 if the update successful 0 else
     * @throws DAOException for sql Exceptions

    public int update(Computer computer) throws DAOException {
        String updateComputer = "update computer set " + computerColumns[1] + "=? ," + computerColumns[2] + "=? ,"
                + computerColumns[3] + "=? ," + computerColumns[4] + "= ? where " + computerColumns[0] + "= ?";
        try {
            LOGGER.error("is " + String.valueOf(computer.getId()));
            this.jdbcTemplate.update(updateComputer, computer.getName(),
                    computer.getDateIN(), computer.getDateOut(),
                    computer.getCompagnyId(), computer.getId());
            LOGGER.info("update successful");
            return 1;

        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDao] in function update");
            throw new DAOException("[DAO EXCEPTION] Impossible to update computer delete from database");
        }
    }

    /**
     * @return list of computer
     * @throws DAOException for sql exceptions

    public List<Computer> getAllComputer() throws DAOException {
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id";
        try {
            return jdbcTemplate.query(selectAllComputer, new ComputerDaoMapper());
        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDao] in function getAllComputer");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
        }
    }

    /**
     * @param start start page
     * @param end   end page
     * @return list off all computer
     * @throws DAOException for sql exceptions


    public List<Computer> getAllComputerPage(int start, int end) throws DAOException {
        String selectAllComputer = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on " +
                "computer.company_id = company.id limit ?, ?";

        List<Computer> allComp;
        try {

            allComp = jdbcTemplate.query(selectAllComputer, new Object[]{start, end}, new ComputerDaoMapper());
            return allComp;
        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDao] in function getAllComputer(int start, int end)");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer from dataBase");
        }
    }

    /**
     * @param id id of computer
     * @return computer that have their id equal to id in parameter
     * @throws DAOException for sql error

    public Computer getComputerById(int id) throws DAOException {
        String selectComputerByid = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id "
                + " where computer.id= ? ";
        try {
            return jdbcTemplate.queryForObject(selectComputerByid, new Object[]{id}, new ComputerDaoMapper());
        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDAO] in function getCompanyById");
            throw new DAOException("[DAO EXCEPTION] error in function getComputerById");
        }

    }

    /**
     * @param name of computer
     * @return list of computer that have same name
     * @throws DAOException for sql error

    public List<Computer> getComputerByName(String name) throws DAOException {
        String selectComputeryByName = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name = ?";
        try {
            return jdbcTemplate.query(selectComputeryByName, new Object[]{name}, new ComputerDaoMapper());
        } catch (DataAccessException e) {
            LOGGER.error("[Error ComputerDAO] in function getComputerByName ");
            throw new DAOException("[DAO EXCEPTION] Impossible to get All computer by Name from dataBase");
        }
    }

    /**
     * @param name  of computer
     * @param start page
     * @param end   page
     * @return list of computer
     * @throws DAOException for sql exceptions

    public List<Computer> search(String name, int start, int end) throws DAOException {
        String search = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company on computer.company_id = company.id where computer.name like ? "
                + "or company.name like ? limit ? , ?";
        try {
            return jdbcTemplate.query(search, new Object[]{"%" + name + "%", "%" + name + "%", start, end}, new ComputerDaoMapper());
        } catch (DataAccessException e) {
            LOGGER.error("[Error DAO] in function getComputerByName ");
            throw new DAOException("[DAO EXCEPTION] Impossible to find computer in dataBase");
        }

    }

    /**
     * @return number of line
     * @throws DAOException for sql exceptions

    public int countTotalLine() throws DAOException {
        String selectAllComputer = "select count(1) from computer";
        int rowCount = 0;

        try {
            rowCount = jdbcTemplate.queryForObject(selectAllComputer, Integer.class);
            return rowCount;
        } catch (DataAccessException e) {
            e.printStackTrace();
            LOGGER.error("[Error DAO] in function getCountLine");
            throw new DAOException("Impossible to count computer in dataBase");
        }

    }

    /**
     * @param search name of computer
     * @return number of line in database
     * @throws DAOException for sql errors

    public int countTotalLine(String search) throws DAOException {
        String selectAllComputer = "select count(1) from computer left join company on computer.company_id = company.id " +
                "where computer.name like ? OR company.name like ?";
        int count = 0;

        try {
            count = jdbcTemplate.queryForObject(selectAllComputer, new Object[]{"%" + search + "%", "%" + search + "%"}, Integer.class);
            return count;
        } catch (DataAccessException e) {
            LOGGER.error("[Error DAO] in function CountTotalLine");
            throw new DAOException("Impossible to count computer for this search in dataBase");
        }

    }

    /**
     * @param start page
     * @param end   page
     * @param reqBy ASC or DESC
     * @param name  of computer
     * @return list of computer
     * @throws DAOException of sql exceptions

    public List<Computer> getComputerOrderBy(int start, int end, String reqBy, String name) throws DAOException {
        String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company "
                + "on computer.company_id = company.id ORDER BY " + name + " " + reqBy + " limit ? , ? ";
        try {
            return jdbcTemplate.query(orderBy, new Object[]{start, end}, new ComputerDaoMapper());
        } catch (DataAccessException e) {
            LOGGER.error("[Error DAO] in function getComputerOrderBy");
            throw new DAOException("Impossible to find computer for this order in dataBase");
        }
    }

    /**
     * @param start    page
     * @param end      page
     * @param reqBy    ASC or DESC
     * @param search   of computer
     * @param reqorder name of coulumn
     * @return list of computer
     * @throws DAOException of sql exceptions

    public List<Computer> getComputerOrderBy(int start, int end, String reqorder, String reqBy, String search)
            throws DAOException {
        String orderBy = "select computer.id, computer.name, computer.introduced, computer.discontinued ,"
                + "company.id as company_id, company.name as companyName from computer left join company "
                + "on computer.company_id = company.id where computer.name like '%" + search
                + "%' or company.name like '%" + search + "%' " + "ORDER BY " + reqorder + " "
                + reqBy + " limit ?,?";
        try {
            return jdbcTemplate.query(orderBy, new Object[]{start, end}, new ComputerDaoMapper());

        } catch (DataAccessException e) {
            LOGGER.error("[Error DAO] in function getComputerOrderBy");
            throw new DAOException("Impossible to find computer for this order in dataBase");
        }
    }*/

/**
 * @param name of computer
 * @return list of computer that have same name
 * @throws DAOException for sql error
 * */
/*
@Query("select computer.id, computer.name, computer.introduced, computer.discontinued ,company.id as company_id, " +
        "company.name as companyName from computer left join company" +
        " on computer.company_id = company.id where computer.name = : name " )
    List<Computer> getComputerByName( @Param("name")String name) throws DAOException ;
*/
}