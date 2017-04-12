package fr.ebiz.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.JDBCMySQLConnection;

public class ComputerDAO {

	private static Statement statement = null;
	private static JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();
	private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id" };
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	/**
	 * 
	 * @param computer
	 * @return 1 if the insert successful 0 else
	 */
	public static int insert(Computer computer) {
		String insertComputer = "insert into computer values('" + computer.getId() + "','" + computer.getName() + "','"
				+ computer.getDateIN() + "','" + computer.getDateOut() + "','" + computer.getCompagnyId() + "')";

		try {
			statement = c.getConnection();
			statement.executeUpdate(insertComputer);
			c.closeConnection();
			return 1;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param id
	 *            of computer that we want delete
	 * @return 1 if the delete successful 0 else
	 */
	public static int delete(int id) {
		String deleteComputer = "delete from computer where id= " + id;

		try {
			statement = c.getConnection();
			statement.executeUpdate(deleteComputer);
			c.closeConnection();
			return 1;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param computer
	 * @return 1 if the update successful 0 else
	 */
	public static int Update(Computer computer) {
		String updateComputer = "update computer set " + computerColumns[1] + "='" + computer.getName() + "',"
				+ computerColumns[2] + "='" + computer.getDateIN() + "'," + computerColumns[3] + "='"
				+ computer.getDateOut() + "'," + computerColumns[4] + "='" + computer.getCompagnyId() + "'" + "where "
				+ computerColumns[0] + "='" + computer.getId() + "'";
		try {
			statement = c.getConnection();
			statement.executeUpdate(updateComputer);
			c.closeConnection();
			return 1;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @return list off all computer
	 */
	public static List<Computer> getAllComputer() {

		String selectAllComputer = "select * from computer";
		statement = c.getConnection();
		ResultSet rs;
		List<Computer> allComputer = new ArrayList<>();
		try {
			rs = statement.executeQuery(selectAllComputer);

			while (rs.next()) {
				allComputer.add(getComputer(rs));
			}
			rs.close();
			c.closeConnection();
			return allComputer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @param id
	 * @return computer that have thier id equal to id in paramater
	 */
	public static Computer getComputerById(int id) {
		String selectComputerByid = "select * from computer where id=" + Integer.toString(id);
		try {

			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectComputerByid);
			if (rs.next()) {

				return (getComputer(rs));
			}
			rs.close();
			c.closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @return list of computer that have same name
	 */
	public static  List<Computer> getComputerByName(String name) {
		String selectCompanyByName = "select * from computer where name= " + "'" + name + "'";

		try {
			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectCompanyByName);
			List<Computer> listComputer = new ArrayList<>();

			while (rs.next()) {
				listComputer.add(getComputer(rs));

			}
			return listComputer;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param rs
	 * @return a computer
	 */
	private static Computer getComputer(ResultSet rs) {
		LocalDateTime inDate = null;
		LocalDateTime outDate = null;
		int idComputer;
		try {
			idComputer = rs.getInt(computerColumns[0]);
			String nameComputer = rs.getString(computerColumns[1]);
			String introduced = rs.getString(computerColumns[2]);

			if (introduced != null) {

				inDate = LocalDateTime.parse(introduced, formatter);
			}
			String discontinued = rs.getString(computerColumns[3]);
			if (discontinued != null) {

				outDate = LocalDateTime.parse(discontinued, formatter);
			}
			int companyId = rs.getInt(computerColumns[4]);

			return (new Computer(idComputer, nameComputer, inDate, outDate, companyId));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
