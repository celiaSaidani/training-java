package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ebiz on 19/05/17.
 */
public class ComputerDaoMapper implements RowMapper<Computer> {
    private static String[] computerColumns = {"id", "name", "introduced", "discontinued", "company_id",
            "companyName"};
    LocalDateTime inDate = null;
    LocalDateTime outDate = null;
    int idComputer;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    public Computer mapRow(ResultSet rs, int rowNum) {
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
            String companyName = rs.getString(computerColumns[5]);
            Computer comp = new Computer(idComputer, nameComputer, inDate, outDate,
                    new Company(companyId, companyName));
            return comp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Computer();
    }
}
