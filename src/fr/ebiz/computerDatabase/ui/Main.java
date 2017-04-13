package fr.ebiz.computerDatabase.ui;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.computerDatabase.mapper.CompanyDAO;
import fr.ebiz.computerDatabase.mapper.ComputerDAO;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;

public class Main {

	private static List<Company> company;
	private static List<Computer> compter;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static Scanner input = new Scanner(System.in);

	/**
	 * Main menu
	 * 
	 * @return the selection of the user
	 */
	private static int mainMenu() {
		int selection;
		System.out.println("****                 ****");
		System.out.println("**** Menu principale *****");
		System.out.println("****                 ****");
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

	/**
	 * menu to show the details of a computer selected by id
	 */
	private static void detailsComputerMenu() {
		int choice = 0;
		List<Computer> computer = ComputerService.getAllComputer();
		String response = "cet ordinateur n'existe pas veuillez bien regarder la liste";
		do {
			for (Computer cp : computer) {
				System.out.println(cp.getId() + "\t" + cp.getName());
			}
			System.out.println("entrez 0 pour quitter");
			choice = input.nextInt();
			Computer comp = ComputerService.showDetailsComputer(choice);

			if (comp.getId() == 0) {
				System.out.println(response);
			} else {
				System.out.println(comp);
			}

		} while (choice == 0);
	}

	/**
	 * menu to show all Company in database
	 */

	private static void showListCompanyMenu() {
		List<Company> company = CompanyService.getAllCompany();

		for (Company cp : company) {
			System.out.println(cp.getId() + "\t" + cp.getName());
		}
	}

	/**
	 * menu to show all computer in database
	 */
	private static void showListComputerMenu() {

		List<Computer> computer = ComputerService.getAllComputer();
		for (Computer cp : computer) {
			System.out.println(cp.getId() + "\t" + cp.getName());
		}
	}

	private static void addComputerMenu() {
		int nbrAtt = 4;
		String inputText[] = new String[nbrAtt];
		String[] inputA = { "nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie" };
		String response;
		String yes = "yes";

		inputText[0] = input.nextLine();

		for (int i = 0; i < inputA.length; i++) {

			if (i == 2 || i == 1)
				System.out.println("entrez " + inputA[i] + " de l'ordinateur format[YYYY-MM-DD HH:mm:ss]");
			else
				System.out.println("entrez " + inputA[i] + " de l'ordinateur");
			if (i == 3) {
				System.out.println("voulez vous affichez toute les compagnies existantes? O/N");
				response = input.nextLine();
				if (response.equals("O") || response.equals("o")) {
					showListCompanyMenu();
				} else {
					System.out.println("entrez l'identifiant de la compagnie");
				}
			}

			inputText[i] = input.nextLine();
			if (inputText[i].equals("")) {
				inputText[i] = null;
			}
		}
		if (ComputerService.updateComputer(0,inputText,true).equals(yes))
			System.out.println("insertion reussie");
		else
			System.err.println("insertion non effectué " + ComputerService.updateComputer(0,inputText,true));

	}

	private static void updateComputerMenu() {
		
		int choice=0;
		showListComputerMenu();
		System.out.println("selectionner l'identifant d'un ordinateur à modifier");
		detailsComputerMenu();
		System.out.println("tapez l'identifiant de l'ordinateur");
		choice = input.nextInt();
		Computer computer = ComputerService.showDetailsComputer(choice);
		String[] cp={computer.getName(),ComputerService.DateToString(computer.getDateIN()),ComputerService.DateToString(computer.getDateOut()),Integer.toString(computer.getCompagnyId())};
		
		String[] inputA={"nom","date d'entrée","date d'arrêt","identifiant de la compagnie"};
		int nbrAtt=4;
		String inputText[]=new String[nbrAtt];
		String response;
		String yes="yes";
		
		inputText[0]=input.nextLine();
	
			for (int i = 0; i < inputA.length; i++) {
				
				if(i==2||i==1)
					System.out.println("entrez "+ inputA[i]+" de l'ordinateur format[YYYY-MM-DD HH:mm:ss]");
				else
					System.out.println("entrez "+ inputA[i]+" de l'ordinateur");
				if(i==3){
					System.out.println("voulez vous affichez toute les compagnies existantes? O/N");
					response=input.nextLine();
					if(response.equals("O")||response.equals("o")){
						showListCompanyMenu();
					}
					else{
						System.out.println("entrez l'identifiant de la compagnie");
					}
				}
				
				inputText[i]=input.nextLine();
				if(inputText[i].equals("")){
					inputText[i]=cp[i];
				}
			}
			if(ComputerService.updateComputer(Integer.getInteger(cp[0]),inputText,false).equals(yes))
				System.out.println("modification reussie");
			else
				System.err.println("modification non effectué "+ComputerService.updateComputer(Integer.getInteger(cp[0]),inputText,false));
			
	}

	private static void deleteComputerMenu() {
		int choice = 0;
		showListComputerMenu();

		boolean delete;
		do {
			System.out.println("selectinnez un identifiant d'ordinateur parmis cette liste, entrer 0 pour quitter");
			choice = input.nextInt();
			delete = ComputerService.deleteCpmouter(choice);
			if (delete) {
				System.out.println("l'ordinateur " + choice + " a été bien supprimer");
			} else {
				System.out.println("erreur suppresion l'ordinateur que vous voulez supprimer n'existe pas");
			}

		} while (choice != 0);
	}

	public static void main(String[] args) throws SQLException {
		int choice = 0;
		do {
			switch (choice) {
			case 1:
				showListComputerMenu();
				break;
			case 2:
				showListCompanyMenu();
				break;
			case 3:
				detailsComputerMenu();
				break;
			case 4:
				addComputerMenu();
				break;
			case 5:
				updateComputerMenu();
				break;
			case 6:
				deleteComputerMenu();
				break;

			default:
				break;
			}
			choice = mainMenu();
		} while (choice != 7);

	}
	// TODO Auto-generated method stub
	/*
	 * CompanyDAO cp= new CompanyDAO(); company= cp.getAllCompany(); for (int i
	 * = 0; i <company.size(); i++) {
	 * System.out.println(company.get(i).getName());
	 * 
	 * } System.out.println(cp.getCompanyID(3).getName());
	 * System.out.println(cp.getCompanyName("Apple Inc.").get(0).getId());
	 * 
	 * 
	 * ComputerDAO cp= new ComputerDAO();
	 * System.out.println(cp.getComputerById(3).getCompagnyId());
	 * compter=cp.getAllComputer(); for (int i = 0; i <compter.size(); i++) {
	 * System.out.println(compter.get(i).getName());
	 * 
	 * } System.out.println(cp.getComputerByName("Apple IIe").size());
	 * if(cp.insert(new Computer(2222,
	 * "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00",
	 * formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter),
	 * 2))==1){ System.out.println("insertion sucss"); }
	 * 
	 * if(cp.delete(2222)==1){ System.out.println("delete ok"); }
	 * 
	 * cp.Update((new Computer(2222,
	 * "Macintosh II",LocalDateTime.parse("2017-05-05 01:00:00",
	 * formatter),LocalDateTime.parse("2017-05-05 01:00:00", formatter), 3)));
	 */

}
