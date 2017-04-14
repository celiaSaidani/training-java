package fr.ebiz.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.persistance.CompanyDAO;

public class CompanyMapper {
    private final String companyName = "name";
    private final String companyId = "id";
    private CompanyDAO companyDAO;

    public CompanyMapper() {
        // TODO Auto-generated constructor stub
        companyDAO = new CompanyDAO();
    }

    public List<Company> getAllCompanyMapper() {

        ResultSet rs = companyDAO.getAllCompany();
        List<Company> allCompany = new ArrayList<>();
        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                allCompany.add(new Company(idCompany, name));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allCompany;

    }

    public List<Company> getAllCompanyMapper(int start) {
        ResultSet rs = companyDAO.getAllCompany(start);

        List<Company> allCompany = new ArrayList<>();

        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                allCompany.add(new Company(idCompany, name));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allCompany;
    }

    public Company getCompanyIDMapper(int id) {
        ResultSet rs = companyDAO.getCompanyID(id);
        String name = null;
        try {
            if (rs.next()) {
                name = rs.getString("name");
            }
            return new Company(id, name);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (rs != null && rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Company();
    }
}
