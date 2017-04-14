package fr.ebiz.computerDatabase.service;

import java.util.List;

import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.model.Company;

public class CompanyService {
	private CompanyMapper companyMapper;
	public CompanyService() {
		// TODO Auto-generated constructor stub
		companyMapper= new CompanyMapper();
	}
	/**
	 *
	 * @return list of all company of dataBase
	 */
	public  List<Company> getAllCompany() {
		return companyMapper.getAllCompanyMapper();
	}
	/**
	 *
	 * @return list of all company of dataBase
	 */
	public  List<Company> getAllCompany(int start) {
		return companyMapper.getAllCompanyMapper(start);
	}

}
