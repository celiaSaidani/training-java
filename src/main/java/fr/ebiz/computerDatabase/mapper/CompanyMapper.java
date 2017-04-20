package fr.ebiz.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.model.Company;

public class CompanyMapper {
    private final String companyName = "name";
    private final String companyId = "id";



    public List<CompanyDTO> getAllCompanyMapper(ResultSet rs) {

        List<CompanyDTO> allCompany = new ArrayList<>();
        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                Company company= new Company(idCompany,name);
                allCompany.add(new CompanyDTO(company));
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

    public List<CompanyDTO> getAllCompanyMapperPage(ResultSet rs ) {

        List<CompanyDTO> allCompany = new ArrayList<>();

        try {
            while (rs.next()) {
                int idCompany = rs.getInt(companyId);
                String name = rs.getString(companyName);
                Company comp = new Company(idCompany, name);
                allCompany.add(new CompanyDTO(comp));
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

    public CompanyDTO getCompanyIDMapper(int id,ResultSet rs) {
        String name = null;
        try {
            if (rs.next()) {
                name = rs.getString("name");
            }
            Company comp= new Company(id,name);
            return new CompanyDTO(comp);
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
        return new CompanyDTO();
    }
}
