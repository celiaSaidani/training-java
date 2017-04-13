package fr.ebiz.computerDatabase.service;

import java.util.List;

import fr.ebiz.computerDatabase.mapper.CompanyDAO;
import fr.ebiz.computerDatabase.model.Company;



public class CompanyService {
	
	
	public static List<Company> getAllCompany() {
		return CompanyDAO.getAllCompany();
	}
	

}
