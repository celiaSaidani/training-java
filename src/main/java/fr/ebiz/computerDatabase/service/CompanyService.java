package fr.ebiz.computerDatabase.service;

import java.sql.ResultSet;
import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.mapper.CompanyMapper;
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
        ResultSet rs= companyDao.getAllCompany();
        return companyMapper.getAllCompanyMapper(rs);
    }

    /**
     * @return list of all company of dataBase
     */
    public List<CompanyDTO> getAllCompany(int start) {
        ResultSet rs=companyDao.getAllCompany(start);
        return companyMapper.getAllCompanyMapperPage(rs);
    }

    /**
     * @return list of all company by id
     */
    public CompanyDTO getCompanybyId(int id) {
        ResultSet rs= companyDao.getCompanyID(id);
        return companyMapper.getCompanyIDMapper(id,rs);
    }

}
