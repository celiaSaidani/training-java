package fr.ebiz.computerDatabase.main;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import fr.ebiz.computerDatabase.model.*;
import fr.ebiz.computerDatabase.service.ComputerService;

import java.util.List;
import java.util.Scanner;

import fr.ebiz.computerDatabase.mapper.*;

public class Main {

	
	private static List<Company> company;
	private static List<Computer> compter;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static Scanner input = new Scanner(System.in);
	
	
	private static int menu() {
		int selection;
	     
		System.out.println("****Menu principale****");
		 /***************************************************/

       System.out.println("choisir une opperation à effectuer");
       System.out.println("-------------------------\n");
       System.out.println("1 - Lister tous les ordinateurs");
       System.out.println("2 - Lister toutes les compagnies");
       System.out.println("3 - Afficher les details d'un ordinateur");
       System.out.println("4 - Ajouter un ordinateur");
       System.out.println("5 - Modifier un ordinateur");
       System.out.println("6 - Supprimer un ordinateur");
       System.out.println("7-  Quitter");
	
       selection = input.nextInt();
       return selection;    
	}
	
	public static void main(String[] args) throws SQLException {
		int choice=0;
		do {
			switch (choice) {
			case 1:
				break;
			case 2:
				break;
			case 3: System.out.println(ComputerService.showDetailsComputer(3).toString());
				break;
			case 4: 
				break;
			case 5:
				break;
			case 6:
				break;

			default:
				break;
			}
			choice= menu();
		} while (choice!=7);
	

		 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
		// TODO Auto-generated method stub
		/*CompanyDAO cp= new CompanyDAO();
			company= cp.getAllCompany();
		for (int i = 0; i <company.size(); i++) {
			System.out.println(company.get(i).getName());
			
		}
		System.out.println(cp.getCompanyID(3).getName());
		System.out.println(cp.getCompanyName("Apple Inc.").get(0).getId());
		
		
		ComputerDAO cp= new ComputerDAO();
		System.out.println(cp.getComputerById(3).getCompagnyId());
		compter=cp.getAllComputer();
		for (int i = 0; i <compter.size(); i++) {
			System.out.println(compter.get(i).getName());
			
		}
		System.out.println(cp.getComputerByName("Apple IIe").size());
		if(cp.insert(new Computer(2222, "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00", formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter), 2))==1){
			System.out.println("insertion sucss");
		}
		
		if(cp.delete(2222)==1){
			System.out.println("delete ok");
		}
		
		cp.Update((new Computer(2222, "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00", formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter), 3)));*/
		
	

}
