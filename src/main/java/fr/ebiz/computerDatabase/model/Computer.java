package fr.ebiz.computerDatabase.model;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = " computer-database-db ",name="computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "introduced")
    private LocalDateTime dateIN;
    @Column(name = "discontinued")
    private LocalDateTime dateOut;
    @ManyToOne
    private Company company;

    /**
     * default constructor.
     */
    public Computer() {
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor 1 with id of computer.
     *
     * @param id        id of computer
     * @param name      name of computer
     * @param in        date introduced
     * @param out       date discoutinued
     * @param company  company
     */
    public Computer(Long id, String name, LocalDateTime in, LocalDateTime out, Company company) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.dateIN = in;
        this.dateOut = out;
        this.company = company;
    }

    /**
     * constructor 1 with id of computer.
     *
     * @param id      id of computer
     * @param name    name of computer
     * @param in      date introduced
     * @param out     date discoutinued
     * @param //company Company object
     */
    public Computer(Long id, String name, LocalDateTime in, LocalDateTime out) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.dateIN = in;
        this.dateOut = out;

    }

    /**
     * constructor 2 without id of computer.
     *
     * @param name      name of computer
     * @param in        date introduced
     * @param out       date discoutinued
     * @param company id of company
     */
    public Computer(String name, LocalDateTime in, LocalDateTime out, Company company) {
        // TODO Auto-generated constructor stub

        this.name = name;
        this.dateIN = in;
        this.dateOut = out;
        this.company = company;
    }

    /**
     * @return id of company
     */
    public Long getId() {
        return id;
    }

    /**
     * set id of computer.
     *
     * @param id of computer
     */
    public void setId(Long id) {
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
     *
     * @param name of computer
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
     *
     * @param dateIN to set
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
     *
     * @param dateOut to set
     */
    public void setDateOut(LocalDateTime dateOut) {
        this.dateOut = dateOut;
    }

    /**
     * @return id company of computer
     */
    public Company getCompagny() {
        return company;
    }

    /**
     * @param id of computer
     * @return STring computer
     */
    public String toString(int id) {
        return "Computer [id=" + id + ", name=" + name + ", dateIN=" + dateIN + ", dateOut=" + dateOut
                + " , idCompany= " + id + "]";
    }




}
