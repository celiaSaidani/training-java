package fr.ebiz.computerDatabase.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fr.ebiz.computerDatabase.model.Company;

public class CompanyDAOMapper {
    private final String companyName = "name";
    private final String companyId = "id";


    public Company getCompanyID(int id,ResultSet rs) {
        String name = null;
        try {
            if (rs.next()) {
                name = rs.getString("name");
            }
            
            return new Company(id,name);
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
    
    public List<Company> getAllCompanyPage(ResultSet rs ) {

        List<Company> allCompany = new ArrayList<>();

        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                Company comp = new Company(idCompany, name);
                allCompany.add(comp);
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
    public List<Company> getAllCompany(ResultSet rs) {

        List<Company> allCompany = new ArrayList<>();
        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                Company company= new Company(idCompany,name);
                allCompany.add(company);
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

    
    
}
