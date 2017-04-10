package fr.ebiz.computerDatabase.main;
import java.sql.SQLException;
import fr.ebiz.computerDatabase.model.*;

import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.mapper.*;

public class Main {

	private static List<Company> company;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		CompanyDAO cp= new CompanyDAO();
			company= cp.getAllCompany();
		for (int i = 0; i <company.size(); i++) {
			System.out.println(company.get(i).getName());
			
		}
		
		

	}

}
