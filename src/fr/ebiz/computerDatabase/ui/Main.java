package fr.ebiz.computerDatabase.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.utils.DateTime;

public class Main {

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
		String resp;
		int cpt=0;
		do {
			System.out.println("Entrez Q pour quitter,cliquez entrer pour continuer");
			resp=input.nextLine();

			List<Company> company = CompanyService.getAllCompany(cpt);
			if(company.isEmpty())
				break;
			else{

			for (Company cp : company) {
				System.out.println(cp.getId() + "\t" + cp.getName());
			}
			cpt=cpt+10;
			System.out.println("NEXT>>");
			
			}
		
		} while (!resp.equals("Q"));
		
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
		if (ComputerService.updateComputer(0, inputText, false).equals(yes))
			System.out.println("insertion reussie");
		else
			System.err.println("insertion non effectué " + ComputerService.updateComputer(0, inputText, true));

	}

	/**
	 * update a computer
	 */

	private static void updateComputerMenu() {

		int choice = 0;
		showListComputerMenu();
		System.out.println("selectionner l'identifant d'un ordinateur à modifier");
		detailsComputerMenu();
		System.out.println("tapez l'identifiant de l'ordinateur");
		choice = input.nextInt();
		Computer computer = ComputerService.showDetailsComputer(choice);
		String[] cp = { Integer.toString(computer.getId()), computer.getName(),
				DateTime.DateToString(computer.getDateIN()), DateTime.DateToString(computer.getDateOut()),
				Integer.toString(computer.getCompagnyId()) };

		String[] inputA = { "nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie" };
		int nbrAtt = 4;
		String inputText[] = new String[nbrAtt];
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
				inputText[i] = cp[i + 1];

			}
		}
		int id = Integer.parseInt(cp[0]);
		String modif = ComputerService.updateComputer(id, inputText, true);

		if (modif.equals(yes))
			System.out.println("modification reussie");
		else
			System.err.println("modification non effectué " + modif);

	}

	/**
	 * delete a computer
	 */
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
}
