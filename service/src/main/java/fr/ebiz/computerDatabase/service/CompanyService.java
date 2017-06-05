package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistence.CompanyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
        List<Company> companies;
        try {
            companies = companyDao.findAll();
            return companyMapper.getCompanyDTOs(companies);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getAllCompany");
            throw new ServiceException("enable to close connection");
        }
    }

    /**
     * @param start begin of page
     * @param end   of page
     * @return list of companies
     * @throws ServiceException if an error occurs
     */

    public List<CompanyDTO> getAllCompanyPage(int start, int end) throws ServiceException {
        Page page;
        try {
            PageRequest request = new PageRequest(start, end);
            page = companyDao.findAll(request);
            //DTOPage data = new DTOPage();
            return companyMapper.getCompanyDTOs(page.getContent());
            //data.setNbrPage(page.getTotalPages());
            //data.setTotalcount(page.getTotalElements());
            // return data;
        } catch (DataAccessException e) {
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
            Company company = companyDao.findOne(id);
            return companyMapper.getCompanyDTO(company);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getCompanybyId ");
            throw new ServiceException("can't get all company by id");
        }
    }

}