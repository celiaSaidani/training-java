package fr.ebiz.computerDatabase.dto;

import fr.ebiz.computerDatabase.model.Company;

public class CompanyDTO {
  private String idCompany;
  private String nameCompany;

  /**
   * DEFAULT constructor.
   */
  public CompanyDTO() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param company
   *          an company object
   */
  public CompanyDTO(Company company) {

    this.setIdCompany(Integer.toString(company.getId()));
    this.setNameCompany(company.getName());
  }

  public String getIdCompany() {
    return idCompany;
  }

  public void setIdCompany(String idCompany) {
    this.idCompany = idCompany;
  }

  public String getNameCompany() {
    return nameCompany;
  }

  public void setNameCompany(String nameCompany) {
    this.nameCompany = nameCompany;
  }

}
