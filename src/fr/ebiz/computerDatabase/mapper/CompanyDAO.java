package fr.ebiz.computerDatabase.mapper;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import fr.ebiz.computerDatabase.persistance.*;

import fr.ebiz.computerDatabase.model.Company;

public class CompanyDAO {
	
	
	private final String SelectQuery="select * from company";
	private final String SelectCompanyByID="select * from company where id=";
	
	//get compagny by id
	public Company getCompanyID(int id){
		String name = null;
		try {
			JDBCMySQLConnection c= JDBCMySQLConnection.getInstance();
			java.sql.Connection dbConnection = c.getConnection();
			java.sql.Statement statement = null;
			statement=dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(SelectCompanyByID+Integer.toString(id));
			while (rs.next()) {
				name = rs.getString("name");
				
			}
			return new Company(id, name);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	//get compagny by name
	public List<Company> getCompanyName(int id){
		//to DO
		return null;
	}
	
	//get ALL company
	
	public List<Company> getAllCompany(){
		
		try {
			JDBCMySQLConnection c= JDBCMySQLConnection.getInstance();
			java.sql.Connection dbConnection = c.getConnection();
			java.sql.Statement statement = null;
			statement=dbConnection.createStatement();
		
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
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

}
