package fr.ebiz.computerDatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.Exception.DAOException;
import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistance.CompanyDAO;
import fr.ebiz.computerDatabase.persistance.ConnectionDB;
import fr.ebiz.computerDatabase.persistance.Transaction;

public class CompanyService {
  private CompanyMapper companyMapper;
  private CompanyDAO companyDao;
  private Connection connection = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

  /**
   * Default constructor.
   */
  public CompanyService() {
    // TODO Auto-generated constructor stub
    companyMapper = new CompanyMapper();
    companyDao = new CompanyDAO();

  }

  /**
   * @return list of company DTO
   * @throws ServiceException
   *           for errors in companyDTO
   */
  public List<CompanyDTO> getAllCompany() throws ServiceException {
    List<Company> cp;
    try {
      connection = ConnectionDB.getInstance().getConnection();
      Transaction.set(connection);
      cp = companyDao.getAllCompany();
      return companyMapper.getCompanyDTOs(cp);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error service] error in function getAllCompany ");
      throw new ServiceException("can't get all company");
    } finally {
      try {
        if ((connection != null)) {
          Transaction.remove();
          connection.close();
        }

      } catch (SQLException e) {
        throw new ServiceException("enable to close connection");
      }
    }
  }

  /**
   * @param start
   *          page
   * @return list companyDTO
   * @throws ServiceException
   *           for errors in companyDTO
   */
  public List<CompanyDTO> getAllCompanyPage(int start) throws ServiceException {
    List<Company> cp;
    try {
      connection = ConnectionDB.getInstance().getConnection();
      Transaction.set(connection);
      cp = companyDao.getAllCompany(start);
      return companyMapper.getCompanyDTOs(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error service] error in function getAllCompany by limit ");
      throw new ServiceException("can't get all company by limit");
    } finally {
      try {
        if ((connection != null)) {
          Transaction.remove();
          connection.close();
        }

      } catch (SQLException e) {
        throw new ServiceException("enable to close connection");
      }
    }
  }

  /**
   * @param id
   *          of company
   * @return company by ID
   * @throws ServiceException
   *           for errors in companyDTO
   */
  public CompanyDTO getCompanybyId(int id) throws ServiceException {
    Company cp;
    try {
      connection = ConnectionDB.getInstance().getConnection();
      Transaction.set(connection);
      cp = companyDao.getCompanyID(id);
      return companyMapper.getCompanyDTO(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error service] error in function getCompanybyId ");
      throw new ServiceException("can't get all company by id");
    } finally {
      try {
        if ((connection != null)) {
          Transaction.remove();
          connection.close();
        }

      } catch (SQLException e) {
        throw new ServiceException("enable to close connection");
      }
    }
  }

  /**
   * @param id
   *          of company
   * @return companyDTO
   * @throws ServiceException
   *           for errors in companyDTO
   */
  public CompanyDTO getCompanybyIdLocal(int id) throws ServiceException {
    Company cp;
    try {
      connection = Transaction.getConnetion();
      cp = companyDao.getCompanyID(id);
      return companyMapper.getCompanyDTO(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error service] error in function getCompanybyIdLocal ");
      throw new ServiceException("can't get all company by id");

    }
  }

}
