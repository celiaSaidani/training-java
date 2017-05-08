package fr.ebiz.computerDatabase.model;

import java.time.LocalDateTime;

public class Computer {
  private int id;
  private String name;
  private LocalDateTime dateIN;
  private LocalDateTime dateOut;
  private int idCompany;
  private Company Company;

  /**
   * default constructor.
   */
  public Computer() {
    // TODO Auto-generated constructor stub
  }

  /**
   * constructor 1 with id of computer.
   * @param id
   *          id of computer
   * @param name
   *          name of computer
   * @param in
   *          date introduced
   * @param out
   *          date discoutinued
   * @param idCompany
   *          id company
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
   * constructor 1 with id of computer.
   * @param id
   *          id of computer
   * @param name
   *          name of computer
   * @param in
   *          date introduced
   * @param out
   *          date discoutinued
   * @param company
   *          Company object
   */
  public Computer(int id, String name, LocalDateTime in, LocalDateTime out, Company company) {
    // TODO Auto-generated constructor stub
    this.id = id;
    this.name = name;
    this.dateIN = in;
    this.dateOut = out;
    this.setCompany(company);
  }

  /**
   * constructor 2 without id of computer.
   * @param name
   *          name of computer
   * @param in
   *          date introduced
   * @param out
   *          date discoutinued
   * @param idCompany
   *          id of company
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
   * set id of computer.
   * @param id
   *          of computer
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
   * set name of computer.
   * @param name
   *          of computer
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
   * set date introduced of computer.
   * @param dateIN
   *          to set
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
   * set date discontinued.
   * @param dateOut
   *          to set
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
  /**
   * @param id of computer
   * @return STring computer
   */
  public String toString(int id) {
    return "Computer [id=" + id + ", name=" + name + ", dateIN=" + dateIN + ", dateOut=" + dateOut
        + " , idCompany= " + id + "]";
  }

  public Company getCompany() {
    return Company;
  }

  public void setCompany(Company company) {
    Company = company;
  }

}
