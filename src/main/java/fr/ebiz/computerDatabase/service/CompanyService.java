package fr.ebiz.computerDatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.Exception.DAOException;
import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistance.CompanyDAO;

public class CompanyService {
  private CompanyMapper companyMapper;
  private CompanyDAO companyDao;
  private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);


  public CompanyService() {
    // TODO Auto-generated constructor stub
    companyMapper = new CompanyMapper();
    companyDao = new CompanyDAO();

  }

  /**
   * @return list of all company of dataBase
   */
  public List<CompanyDTO> getAllCompany() throws ServiceException {
    List<Company> cp;
    try {
      cp = companyDao.getAllCompany();
      return companyMapper.getCompanyDTOs(cp);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      logger.error("[Error service] error in function getAllCompany ");
      throw new ServiceException("can't get all company");
    }
  }

  /**
   * @return list of all company of dataBase
   */
  public List<CompanyDTO> getAllCompanyPage(int start)throws ServiceException {
    List<Company> cp;
    try {
      cp = companyDao.getAllCompany(start);
      return companyMapper.getCompanyDTOs(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      logger.error("[Error service] error in function getAllCompany by limit ");
      throw new ServiceException("can't get all company by limit");
    }
  }

  /**
   * @return list of all company by id
   */
  public CompanyDTO getCompanybyId(int id) throws ServiceException {
    Company cp;
    try {
      cp = companyDao.getCompanyID(id);
      return companyMapper.getCompanyDTO(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      logger.error("[Error service] error in function getCompanybyId ");
      throw new ServiceException("can't get all company by id");
    }
  }

}
