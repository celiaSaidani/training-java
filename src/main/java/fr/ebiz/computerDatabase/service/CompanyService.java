package fr.ebiz.computerDatabase.service;

import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;

public class CompanyService {
    private CompanyMapper companyMapper;

    public CompanyService() {
        // TODO Auto-generated constructor stub
        companyMapper = new CompanyMapper();
    }

    /**
     * @return list of all company of dataBase
     */
    public List<CompanyDTO> getAllCompany() {
        return companyMapper.getAllCompanyMapper();
    }

    /**
     * @return list of all company of dataBase
     */
    public List<CompanyDTO> getAllCompany(int start) {
        return companyMapper.getAllCompanyMapper(start);
    }

}
