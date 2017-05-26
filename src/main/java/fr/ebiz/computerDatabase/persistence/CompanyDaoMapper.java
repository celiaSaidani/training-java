package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ebiz on 19/05/17.
 */
public class CompanyDaoMapper implements RowMapper<Company> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    /**
     * @param rs     resultSet
     * @param rowNum int
     * @return a company
     */
    public Company mapRow(ResultSet rs, int rowNum) {
        Company company = new Company();
        try {
            company.setId(rs.getLong("id"));
            company.setName(rs.getString("name"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return company;
    }
}
