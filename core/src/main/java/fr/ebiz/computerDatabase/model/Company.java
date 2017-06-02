package fr.ebiz.computerDatabase.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = " computer-database-db ", name = "company")
public class Company {
    /**
     * default constructor.
     */
    public Company() {
        // TODO Auto-generated constructor stub
    }

    /**
     * override to String method.
     *
     * @return company string
     */
    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    /**
     * Constructor with 2 parameter.
     *
     * @param id   id of comapny
     * @param name name of company
     */
    public Company(Long id, String name) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of company
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id of company
     * @set id of company
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name of company
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of company
     */
    public void setName(String name) {
        this.name = name;
    }

}
