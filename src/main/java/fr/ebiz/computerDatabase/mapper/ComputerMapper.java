package fr.ebiz.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.ComputerDAO;

public class ComputerMapper {
    private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id" };
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private ComputerDAO computerDao;

    public ComputerMapper() {
        computerDao = new ComputerDAO();
    }

    public int insertMapper(Computer comp) {

        return computerDao.insert(comp);
    }

    public int deleteMapper(int id) {
        return computerDao.delete(id);
    }

    public int updateMapper(Computer comp) {
        return computerDao.update(comp);
    }

    public List<Computer> getAllComputerMapper() {

        List<Computer> allComputer = new ArrayList<>();
        ResultSet rs = computerDao.getAllComputer();

        try {
            while (rs.next()) {
                allComputer.add(getComputer(rs));
            }
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allComputer;
    }

    public List<Computer> getAllComputerMapper(int start) {
        List<Computer> allComputer = new ArrayList<>();
        ResultSet rs = computerDao.getAllComputer(start);
        try {
            while (rs.next()) {
                allComputer.add(getComputer(rs));
            }
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return allComputer;
    }

    public Computer getComputerByIdMapper(int id) {
        ResultSet rs = computerDao.getComputerById(id);
        try {
            if (rs.next()) {

                return (getComputer(rs));
            }
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Computer();

    }

    public List<Computer> getComputerByNameMapper(String name) {
        ResultSet rs = computerDao.getComputerByName(name);
        List<Computer> listComputer = new ArrayList<>();

        try {
            while (rs.next()) {
                listComputer.add(getComputer(rs));

            }
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listComputer;
    }

    /**
     * @param rs
     * @return a computer
     */
    private Computer getComputer(ResultSet rs) {
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
