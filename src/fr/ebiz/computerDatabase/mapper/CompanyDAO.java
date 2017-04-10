package fr.ebiz.computerDatabase.mapper;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import fr.ebiz.computerDatabase.persistance.*;

import fr.ebiz.computerDatabase.model.Company;

public class CompanyDAO {
	
	
	private final String SelectQuery="select * from company";
	
	
	//get compagny by id
	public Company getCompanyID(int id){
		
		
		return null;
	}
	
	//get compagny by name
	public List<Company> getCompanyName(int id){
		
		return null;
	}
	
	//get ALL company
	
	public List<Company> getAllCompany() throws SQLException{
		JDBCMySQLConnection c= JDBCMySQLConnection.getInstance();
		java.sql.Connection dbConnection = c.getConnection();
		java.sql.Statement statement = null;
		statement=dbConnection.createStatement();
	
		try {
			ResultSet rs = statement.executeQuery(SelectQuery);
			List<Company> allCompany= new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				allCompany.add(new Company(id,name));
			}
			rs.close();
			dbConnection.close();
			return allCompany;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

}
