package fr.ebiz.computerDatabase.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;

public class ComputerDAOMapper {
    private static String[] computerColumns = { "id", "name", "introduced", "discontinued", "company_id","companyName" };
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
    
    public List<Computer> getAllComputer(ResultSet rs) {

        List<Computer> allComputer = new ArrayList<>();

        try {
            while (rs.next()) {
                allComputer.add(getComputer(rs));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allComputer;
    }

    public List<Computer> getAllComputerMapperPage(ResultSet rs) {
        List<Computer> allComputer = new ArrayList<>();

        try {
            while (rs.next()) {
                allComputer.add(getComputer(rs));
            }
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return allComputer;
    }

    public Computer getComputerByIdMapper(int id,ResultSet rs) {

        try {
            if (rs.next()) {

                return (getComputer(rs));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Computer();

    }

    public List<Computer> getComputerByNameMapper(ResultSet rs) {

        List<Computer> listComputer = new ArrayList<>();

        try {
            while (rs.next()) {

                listComputer.add(getComputer(rs));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listComputer;
    }

    public List<Computer> SearchMapper(ResultSet rs) {
		
    	 List<Computer> listComputer = new ArrayList<>();

         try {
             while (rs.next()) {

                 listComputer.add(getComputer(rs));

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
        String companyName=rs.getString(computerColumns[5]);
        Computer comp= new Computer(idComputer, nameComputer, inDate, outDate, new Company(companyId, companyName));



        return comp;

    } catch (SQLException e) {
        e.printStackTrace();
        logger.error("Error in function getComputer");
    }
    return new Computer();
}

}
