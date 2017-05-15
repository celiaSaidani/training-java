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

    /**
     * default constructor.
     */
    public ComputerDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param comp computer object
     * @param companyName Company name
     */
    public ComputerDTO(Computer comp, String companyName) {
        if (comp.getId() != 0) {
            this.idComp = Integer.toString(comp.getId());
        }
        this.nameComp = comp.getName();
        this.dateIn = DateTime.dateToString(comp.getDateIN());
        this.dateOut = DateTime.dateToString(comp.getDateOut());
        this.idCompany = Integer.toString(comp.getCompagnyId());
        this.nameCompany = companyName;
    }

    /**
     * @param comp computer object
     */
    public ComputerDTO(Computer comp) {
        if (comp.getId() != 0) {
            this.idComp = Integer.toString(comp.getId());
        }
        this.nameComp = comp.getName();
        this.dateIn = DateTime.dateToString(comp.getDateIN());
        this.dateOut = DateTime.dateToString(comp.getDateOut());
        System.out.println(comp.getCompagnyId());
        if (comp.getCompany().getId() != 0) {
            this.idCompany = Integer.toString(comp.getCompany().getId());
        }
        this.nameCompany = comp.getCompany().getName();
    }

    /**
     * @param name name of computer
     * @param introduced introduced date
     * @param discontinued discontinued date
     * @param idComp id of computer
     */
    public ComputerDTO(String name, String introduced, String discontinued, String idComp) {

        this.nameComp = name;
        this.dateIn = introduced;
        this.dateOut = discontinued;
        this.idCompany = idComp;
    }

    /**
     * @param id id of computer
     * @param name name of computer
     * @param introduced introduced date
     * @param discontinued discontinued date
     * @param idComp id of company
     */
    public ComputerDTO(String id, String name, String introduced, String discontinued, String idComp) {
        this.idComp = id;
        this.nameComp = name;
        this.dateIn = introduced;
        this.dateOut = discontinued;
        this.idCompany = idComp;
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

    /**
     * @param id id of computer
     * @return a string computer
     */
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
