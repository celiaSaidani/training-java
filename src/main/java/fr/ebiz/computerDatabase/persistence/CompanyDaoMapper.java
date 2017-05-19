package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ebiz on 19/05/17.
 */
public class CompanyDaoMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet rs, int rowNum)  {
        Company company = new Company();
        try {
            company.setId(rs.getInt("id"));
            company.setName(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }
}
