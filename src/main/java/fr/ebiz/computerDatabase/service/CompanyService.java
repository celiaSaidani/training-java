package fr.ebiz.computerDatabase.service;


import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistance.CompanyDAO;

public class CompanyService {
    private CompanyMapper companyMapper;
    private CompanyDAO companyDao;

    public CompanyService() {
        // TODO Auto-generated constructor stub
        companyMapper = new CompanyMapper();
        companyDao= new CompanyDAO();

    }

    /**
     * @return list of all company of dataBase
     */
    public List<CompanyDTO> getAllCompany() {
       List<Company> cp= companyDao.getAllCompany();
        return companyMapper.getCompanyDTOs(cp);
    }

    /**
     * @return list of all company of dataBase
     */
    public List<CompanyDTO> getAllCompanyPage(int start) {
        List<Company> cp=companyDao.getAllCompany(start);
        return companyMapper.getCompanyDTOs(cp);
    }

    /**
     * @return list of all company by id
     */
    public CompanyDTO getCompanybyId(int id) {
       Company cp= companyDao.getCompanyID(id);
        return companyMapper.getCompanyDTO(cp);
    }

}
