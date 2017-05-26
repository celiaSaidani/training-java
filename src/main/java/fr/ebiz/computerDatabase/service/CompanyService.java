package fr.ebiz.computerDatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.exception.DAOException;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistence.CompanyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private CompanyDAO companyDao;
    @Autowired
    private CompanyMapper companyMapper;


    /**
     * Default constructor.

     public CompanyService() {
     // TODO Auto-generated constructor stub
     companyMapper = new CompanyMapper();
     companyDao = new CompanyDAO();

     }
     */
    /**
     * @return list of company DTO
     * @throws ServiceException for errors in companyDTO
     */
    public List<CompanyDTO> getAllCompany() throws ServiceException {
        List<Company> cp;
        try {
            cp = companyDao.findAll();
            return companyMapper.getCompanyDTOs(cp);
        } catch ( RuntimeException e) {
            LOGGER.error("[Error service] error in function getAllCompany");
            throw new ServiceException("enable to close connection");
        }
    }

    /**
     * //@param start page
     * @return list companyDTO
     * @throws ServiceException for errors in companyDTO

    public List<CompanyDTO> getAllCompanyPage(int start) throws ServiceException {
        List<Company> cp;
        try {
            cp = companyDao.getAllCompany(start);
            return companyMapper.getCompanyDTOs(cp);
        } catch (DAOException e) {
            LOGGER.error("[Error service] error in function getAllCompanyPage by limit ");
            throw new ServiceException("can't get page of companies");
        }
    }

    /**
     * @param id of company
     * @return company by ID
     * @throws ServiceException for errors in companyDTO
     */
    public CompanyDTO getCompanybyId(Long id) throws ServiceException {
        try {
            Company cp = companyDao.getOne(id);
            return companyMapper.getCompanyDTO(cp);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getCompanybyId ");
            throw new ServiceException("can't get all company by id");
        }
    }

    /**
     * @param id of company
     * @return companyDTO
     * @throws ServiceException for errors in companyDTO
     */
    public CompanyDTO getCompanybyIdLocal(Long id) throws ServiceException {

        try {
            System.err.println(">>>>> IN company service"+id);
            Company cp = companyDao.getOne(id);

            return companyMapper.getCompanyDTO(cp);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error("[Error service] error in function getCompanybyIdLocal ");
            throw new ServiceException("can't get all company by id");
        }
    }


}
