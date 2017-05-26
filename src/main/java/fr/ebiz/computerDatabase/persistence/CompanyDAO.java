package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CompanyDAO extends JpaRepository<Company, Long> {


    /*private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param id of company
     * @return company
     * @throws DAOException for sql error

    public Company getCompanyID(int id) throws DAOException {
        String selectCompanyByID = "select * from company where id= ? ";
        try {
            return jdbcTemplate.queryForObject(selectCompanyByID, new Object[]{id}, new CompanyDaoMapper());
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function getCompanyID");
            throw new DAOException("[DAO EXCEPTION] enable to find company by id in dataBase");
        }
    }

    /**
     * @return all company of data base
     * @throws DAOException for sql error

    public List<Company> getAllCompany() throws DAOException {
        String selectAllCompany = "select * from company";
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


    public List<Company> getAllCompany(int start) throws DAOException {
        String selectAllCompany = "select * from company limit 10 offset ? ";
        try {
            return jdbcTemplate.query(selectAllCompany, new Object[]{start}, new CompanyDaoMapper());
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function getAllCompany by limit");
            throw new DAOException("[DAO EXCEPTION] Impossible to get all company  by limit from dataBase");
        }

    }

    /**
     * @param id of computer to delete
     * @throws DAOException for sql exceptions

    public void delete(int id) throws DAOException {
        String deleteCompany = " delete from company where company.id = ?";
        try {
            jdbcTemplate.update(deleteCompany, id);
        } catch (DataAccessException e) {
            LOG.error("[Error DAO] in function delete company");
            throw new DAOException("[DAO EXCEPTION] Impossible to delete this company from dataBase");
        }


    }*/
}
