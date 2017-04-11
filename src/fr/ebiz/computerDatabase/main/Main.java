package fr.ebiz.computerDatabase.main;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import fr.ebiz.computerDatabase.model.*;
import java.util.List;

import fr.ebiz.computerDatabase.mapper.*;

public class Main {

	private static List<Company> company;
	private static List<Computer> compter;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		/*CompanyDAO cp= new CompanyDAO();
			company= cp.getAllCompany();
		for (int i = 0; i <company.size(); i++) {
			System.out.println(company.get(i).getName());
			
		}
		System.out.println(cp.getCompanyID(3).getName());
		System.out.println(cp.getCompanyName("Apple Inc.").get(0).getId());
		*/
		
		ComputerDAO cp= new ComputerDAO();
		System.out.println(cp.getComputerById(3).getCompagnyId());
		compter=cp.getAllComputer();
		for (int i = 0; i <compter.size(); i++) {
			System.out.println(compter.get(i).getName());
			
		}
		System.out.println(cp.getComputerByName("Apple IIe").size());
		/*if(cp.insert(new Computer(2222, "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00", formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter), 2))==1){
			System.out.println("insertion sucss");
		}
		
		/*if(cp.delete(2222)==1){
			System.out.println("delete ok");
		}*/
		
		cp.Update((new Computer(2222, "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00", formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter), 3)));
		
	}

}
