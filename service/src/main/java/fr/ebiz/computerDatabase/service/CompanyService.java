package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.exception.NotFoundException;
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
     */

    public List<CompanyDTO> getAllCompany()  {
        List<Company> companies;
        try {
            companies = companyDao.findAll();
            return companyMapper.getCompanyDTOs(companies);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getAllCompany");
            throw new NotFoundException("unable to find allCompany");
        }
    }

    /**
     * @param start begin of page
     * @param end   of page
     * @return list of companies

     */

    public List<CompanyDTO> getAllCompanyPage(int start, int end) {
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
            throw new NotFoundException("unable to find allCompany by page");
        }
    }


    /**
     * @param id of company
     * @return company by ID
     */
    public CompanyDTO getCompanybyId(Long id)  {
        try {
            Company company = companyDao.findOne(id);
            return companyMapper.getCompanyDTO(company);
        } catch (RuntimeException e) {
            LOGGER.error("[Error service] error in function getCompanybyId ");
            throw new NotFoundException("can't get all company by id");
        }
    }

}