package fr.ebiz.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistance.JDBCMySQLConnection;

public class CompanyDAO {

	private final static String companyName = "name";
	private final static String companyId = "id";
	private static Statement statement = null;
	private static JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	/**
	 * 
	 * @param id
	 * @return company
	 */
	public static Company getCompanyID(int id) {
		String selectCompanyByID = "select * from company where id=" + Integer.toString(id);
		String name = null;
		try {

			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectCompanyByID);
			if (rs.next()) {
				name = rs.getString("name");
			}
			rs.close();
			c.closeConnection();

			return new Company(id, name);

		} catch (Exception e) {
			logger.error("Error in function getCompanyID");
		}
		return new Company();
	}

	/**
	 * 
	 * @param name
	 * @return list of company
	 */
	public static List<Company> getCompanyName(String name) {
		String selectCompanyByName = "select * from company where name= " + "'" + name + "'";
		List<Company> allCompany = new ArrayList<>();
		try {
			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectCompanyByName);

			while (rs.next()) {
				int idCompany = rs.getInt(companyId);
				allCompany.add(new Company(idCompany, name));
			}
			rs.close();
			c.closeConnection();
			return allCompany;

		} catch (SQLException e) {
			logger.error("Error in function getCompanyName");
		}
		return allCompany;
	}

	/**
	 * 
	 * @return all company of data base
	 */

	public static List<Company> getAllCompany() {
		String selectAllCompany = "select * from company ;";
		List<Company> allCompany = new ArrayList<>();
		try {
			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectAllCompany);

			while (rs.next()) {
				int idCompany = rs.getInt(companyId);
				String name = rs.getString(companyName);
				allCompany.add(new Company(idCompany, name));
			}
			rs.close();
			c.closeConnection();
			return allCompany;

		} catch (SQLException e) {
			logger.error("Error in function getAllCompany");
		}
		return allCompany;
	}
	
	/**
	 * 
	 * @return all company of data base
	 */

	public static List<Company> getAllCompany(int start) {
		String selectAllCompany = "select * from company limit 10 offset "+start;
		List<Company> allCompany = new ArrayList<>();
		try {
			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectAllCompany);

			while (rs.next()) {
				int idCompany = rs.getInt(companyId);
				String name = rs.getString(companyName);
				allCompany.add(new Company(idCompany, name));
			}
			rs.close();
			c.closeConnection();
			return allCompany;

		} catch (SQLException e) {
			logger.error("Error in function getAllCompany");
		}
		return allCompany;
	}


}
