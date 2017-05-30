package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistence.CompanyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private CompanyDAO companyDao;
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * @return list of company DTO
     * @throws ServiceException for errors in companyDTO
     */
    public List<CompanyDTO> getAllCompany() throws ServiceException {
        List<Company> cp;
        try {
            cp = companyDao.findAll();
            return companyMapper.getCompanyDTOs(cp);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getAllCompany");
            throw new ServiceException("enable to close connection");
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
            Company cp = companyDao.getOne(id);

            return companyMapper.getCompanyDTO(cp);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error("[Error service] error in function getCompanybyIdLocal ");
            throw new ServiceException("can't get all company by id");
        }
    }


}
