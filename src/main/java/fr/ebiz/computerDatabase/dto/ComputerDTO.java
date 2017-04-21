package fr.ebiz.computerDatabase.dto;

import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.validator.DateTime;

public class ComputerDTO {
    private String idComp;
    private String nameComp;
    private String dateIn;
    private String dateOut;
    private String idCompany;
    private String nameCompany;
    public ComputerDTO() {
        // TODO Auto-generated constructor stub
    }
    public ComputerDTO(Computer comp,String companyName) {

        this.idComp=Integer.toString(comp.getId());
        this.nameComp=comp.getName();
        this.dateIn=DateTime.DateToString(comp.getDateIN());
        this.dateOut=DateTime.DateToString(comp.getDateOut());
        this.idCompany=Integer.toString(comp.getCompagnyId());
        this.nameCompany=companyName;
    }

    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public String getNameComp() {
        return nameComp;
    }

    public void setNameComp(String nameComp) {
        this.nameComp = nameComp;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String toString(String id) {
        return "Computer [id=" + idComp + ", name=" + nameComp + ", dateIN=" + dateIn + ", dateOut=" + dateOut
                + " , idCompany= " + id + "]";
    }

    public String getNameCompany() {
        return nameCompany;
    }
    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

}