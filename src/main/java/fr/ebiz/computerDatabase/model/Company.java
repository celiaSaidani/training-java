package fr.ebiz.computerDatabase.model;

public class Company {
    /**
     * default constructor
     */
    public Company() {
        // TODO Auto-generated constructor stub
    }

    /**
     * override to String method
     */
    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    private int id;
    private String name;

    /**
     * Constructor with 2 parameter
     * @param id
     * @param name
     */
    public Company(int id, String name) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of company
     */
    public int getId() {
        return id;
    }

    /**
     * @set id of company
     */
    public void setId(int id) {
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
