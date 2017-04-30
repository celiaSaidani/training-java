package fr.ebiz.computerDatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.model.Company;

public class CompanyMapper {

  public CompanyDTO getCompanyDTO(Company cp) {
    return new CompanyDTO(cp);
  }

  public List<CompanyDTO> getCompanyDTOs(List<Company> cp) {
    List<CompanyDTO> allCompany = new ArrayList<>();
    for (int i = 0; i < cp.size(); i++) {
      allCompany.add(new CompanyDTO(cp.get(i)));
    }
    return allCompany;
  }

}
