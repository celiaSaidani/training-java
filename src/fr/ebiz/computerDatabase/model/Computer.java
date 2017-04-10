package fr.ebiz.computerDatabase.model;

import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate dateIN;
	private LocalDate dateOut;
	private Company compagny;
	
	public Computer(int id,String name,LocalDate in,LocalDate out,Company cp) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
		this.dateIN=in;
		this.dateOut=out;
		this.compagny=cp;
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

	public LocalDate getDateIN() {
		return dateIN;
	}

	public void setDateIN(LocalDate dateIN) {
		this.dateIN = dateIN;
	}

	public LocalDate getDateOut() {
		return dateOut;
	}

	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}

	public Company getCompagny() {
		return compagny;
	}
	
}
