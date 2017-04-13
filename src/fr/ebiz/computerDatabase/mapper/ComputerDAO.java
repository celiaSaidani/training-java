package fr.ebiz.computerDatabase.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.JDBCMySQLConnection;

public class ComputerDAO {

	private static Statement statement = null;
	private static PreparedStatement ps;
	private static JDBCMySQLConnection c = JDBCMySQLConnection.getInstance();
	private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id" };
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	/**
	 * 
	 * @param computer
	 * @return 1 if the insert successful 0 else
	 */
	public static int insert(Computer computer) {

		String insertComputer = "insert into computer(name,introduced,discontinued,company_id) values(?,?,?,?)";

		try {

			ps = c.getConnectionP().prepareStatement(insertComputer);
			ps.setString(1, computer.getName());
			ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
			ps.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
			if (computer.getCompagnyId() == 0)
				ps.setNull(4, java.sql.Types.INTEGER);
			else
				ps.setInt(4, computer.getCompagnyId());
			if (ps.executeUpdate() == 1) {
				c.closeConnection();
				return 1;
			} else
				return 0;

		} catch (SQLException e) {
				logger.error("Error in function insert");
			return 0;
		}

	}

	/**
	 * 
	 * @param id
	 *            of computer that we want delete
	 * @return 1 if the delete successful 0 else
	 */
	public static int delete(int id) {
		String deleteComputer = "delete from computer where id= " + id;
		int delete = 0;

		try {
			statement = c.getConnection();
			delete = statement.executeUpdate(deleteComputer);
			c.closeConnection();
			if (delete == 1)
				return 1;
			else
				return 0;

		} catch (SQLException e) {
			logger.error("Error in function delete in");
		}
		return 0;
	}

	/**
	 * 
	 * @param computer
	 * @return 1 if the update successful 0 else
	 */
	public static int update(Computer computer) {

		System.err.println(computer.getId());
		System.err.println(computer.getName());
		System.err.println(computer.getDateIN());
		System.err.println(computer.getDateOut());
		System.err.println(computer.getCompagnyId());
		String updateComputer = "update computer set " + computerColumns[1] + "=? ," + computerColumns[2] + "=? ,"
				+ computerColumns[3] + "=? ," + computerColumns[4] + "= ? where " + computerColumns[0] + "= ?";
		try {
			ps = c.getConnectionP().prepareStatement(updateComputer);
			ps.setString(1, computer.getName());
			ps.setString(2, computer.getDateIN() == null ? null : computer.getDateIN().format(formatter));
			ps.setString(3, computer.getDateOut() == null ? null : computer.getDateOut().format(formatter));
			if (computer.getCompagnyId() == 0)
				ps.setNull(4, java.sql.Types.INTEGER);
			else
				ps.setInt(4, computer.getCompagnyId());
			ps.setInt(5, computer.getId());
			if (ps.executeUpdate() == 1) {
				c.closeConnection();
				return 1;

			} else {
				c.closeConnection();
				return 0;
			}

		} catch (SQLException e) {
			logger.error("Error in function update");
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
			logger.error("Error in function getAllComputer");
		}
		return allComputer;

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
			logger.error("Error in function getCompanyById");
		}
		return new Computer();
	}

	/**
	 * 
	 * @param name
	 * @return list of computer that have same name
	 */
	public static List<Computer> getComputerByName(String name) {
		String selectComputeryByName = "select * from computer where name= " + "'" + name + "'";
		List<Computer> listComputer = new ArrayList<>();

		try {
			statement = c.getConnection();
			ResultSet rs = statement.executeQuery(selectComputeryByName);

			while (rs.next()) {
				listComputer.add(getComputer(rs));

			}
			return listComputer;

		} catch (SQLException e) {
			logger.error("Error in function getComputerByName ");
		}
		return listComputer;
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
			
			logger.error("Error in function getComputer");
		}
		return new Computer();
	}

}
