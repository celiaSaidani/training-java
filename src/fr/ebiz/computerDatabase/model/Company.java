package fr.ebiz.computerDatabase.model;

public class Company {

	public Company() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	private int id;
	private String name;


	public Company(int id,String name) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
