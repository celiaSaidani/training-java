package fr.ebiz.computerDatabase.model;

import java.time.LocalDateTime;

public class Computer {
    private int id;
    private String name;
    private LocalDateTime dateIN;
    private LocalDateTime dateOut;
    private int idCompany;

    /**
     * default constructor
     */
    public Computer() {
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor 1 with id of computer
     * @param id
     * @param name
     * @param in
     * @param out
     * @param idCompany
     */
    public Computer(int id, String name, LocalDateTime in, LocalDateTime out, int idCompany) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.dateIN = in;
        this.dateOut = out;
        this.idCompany = idCompany;
    }

    /**
     * constructor 2 without id of computer
     * @param name
     * @param in
     * @param out
     * @param idCompany
     */
    public Computer(String name, LocalDateTime in, LocalDateTime out, int idCompany) {
        // TODO Auto-generated constructor stub

        this.name = name;
        this.dateIN = in;
        this.dateOut = out;
        this.idCompany = idCompany;
    }

    /**
     * @return id of company
     */
    public int getId() {
        return id;
    }

    /**
     * set id of computer
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name of computer
     */
    public String getName() {
        return name;
    }

    /**
     * set name of computer
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return introduced date of computer
     */
    public LocalDateTime getDateIN() {
        return dateIN;
    }

    /**
     * set date introduced of computer
     * @param dateIN
     */
    public void setDateIN(LocalDateTime dateIN) {
        this.dateIN = dateIN;
    }

    /**
     * @return date discontinued of computer
     */
    public LocalDateTime getDateOut() {
        return dateOut;
    }

    /**
     * set date discontinued
     * @param dateOut
     */
    public void setDateOut(LocalDateTime dateOut) {
        this.dateOut = dateOut;
    }

    /**
     * @return id company of computer
     */
    public int getCompagnyId() {
        return idCompany;
    }

    public String toString(int id) {
        return "Computer [id=" + id + ", name=" + name + ", dateIN=" + dateIN + ", dateOut=" + dateOut
                + " , idCompany= " + id + "]";
    }

}
