package fr.ebiz.computerDatabase.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import fr.ebiz.computerDatabase.mapper.CompanyDAO;

public class Computer {
	private int id;
	private String name;
	private LocalDateTime dateIN;
	private LocalDateTime dateOut;
	private int idCompany;
	
	public Computer(int id,String name,LocalDateTime in,LocalDateTime out,int idCompany) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
		this.dateIN=in;
		this.dateOut=out;
		this.idCompany=idCompany;
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

	public LocalDateTime getDateIN() {
		return dateIN;
	}

	public void setDateIN(LocalDateTime dateIN) {
		this.dateIN = dateIN;
	}

	public LocalDateTime getDateOut() {
		return dateOut;
	}

	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}

	public	int getCompagnyId() {
		return idCompany;
	}


	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", dateIN=" + dateIN + ", dateOut=" + dateOut +" ,"
				+ CompanyDAO.getCompanyID(idCompany).toString()+"]";
	}

}
