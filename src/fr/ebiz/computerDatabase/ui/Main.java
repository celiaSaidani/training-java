package fr.ebiz.computerDatabase.ui;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.validator.DateTime;

public class Main {

	static Scanner input = new Scanner(System.in);
	private ComputerService computerService;
	private CompanyService companyService;

	public Main() {
		computerService = new ComputerService();
		companyService = new CompanyService();
	}
	/**
	 * Main menu
	 *
	 * @return the selection of the user
	 */
	private static int mainMenu() {
		int selection;
		System.out.println("************************");
		System.out.println("*****Menu principale*****");
		System.out.println("*************************");
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
	private void detailsComputerMenu() {
		int choice = 0;
		List<Computer> computer = computerService.getAllComputer();
		final String response = "cet ordinateur n'existe pas veuillez bien regarder la liste";
		do {
			for (Computer cp : computer) {
				System.out.println(cp.getId() + "\t" + cp.getName());
			}
			System.out.println("entrez 0 pour quitter,ou l'identifiant de l'ordinateur");
			choice = input.nextInt();
			Computer comp =computerService.showDetailsComputer(choice);

			if (comp.getId() == 0) {
				System.out.println(response);
			} else {
				System.out.println(comp.toString(comp.getCompagnyId()));
			}

		} while (choice == 0);
	}

	/**
	 * menu to show all Company in database
	 */

	private  void showListCompanyMenu() {
		String resp;
		int cpt = 0;
		do {
			System.out.println("Entrez Q pour quitter,cliquez entrer pour continuer");
			resp = input.nextLine();

			List<Company> company = companyService.getAllCompany(cpt);
			if (company.isEmpty())
				break;
			else {

				for (Company cp : company) {
					System.out.println(cp.getId() + "\t" + cp.getName());
				}
				cpt = cpt + 10;
				System.out.println("NEXT>>");

			}

		} while (!resp.equals("Q"));

	}

	/**
	 * menu to show all computer in database
	 */
	private  void showListComputerMenu() {
		String resp = null;
		int cpt = 0;
		do {
			System.out.println("Entrez Q pour quitter,cliquez entrer pour continuer");
			List<Computer> computer =computerService.getAllComputer(cpt);

			resp = input.nextLine();
			if (computer.isEmpty())
				break;
			else {
				for (Computer cp : computer) {
					System.out.println(cp.getId() + "\t" + cp.getName());
				}
				cpt = cpt + 100;
				System.out.println("NEXT>>");

			}
		} while (!resp.equals("Q"));

	}

	private  void addComputerMenu() {
		int nbrAtt = 4;
		String inputText[] = new String[nbrAtt];
		String[] inputA = { "nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie" };
		String response;
		String yes = "yes";

		inputText[0] = input.nextLine();

		for (int i = 0; i < inputA.length; i++) {

			printText(i);
			if (i == 3) {
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
			} else if ((i == 1) || (i == 2)) {
				LocalDateTime date = DateTime.convertDate(inputText[i]);
				if (date == null)
					return;
			}

		}
		String modif = computerService.updateComputer(0, inputText, false);
		if (modif.equals(yes))
			System.out.println("insertion reussie");
		else
			System.err.println("insertion non effectué " + modif);

	}

	/**
	 * update a computer
	 */

	private  void updateComputerMenu() {

		int choice = 0;
		showListComputerMenu();
		System.out.println("tapez l'identifiant de l'ordinateur");
		choice = input.nextInt();
		detailsComputerMenuById(choice);
		Computer computer = computerService.showDetailsComputer(choice);

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
			printText(i);

			if (i == 3) {
				response = input.nextLine();
				if (response.equals("O") || response.equals("o")) {
					showListCompanyMenu();
				} else {
					System.out.println("entrez l'identifiant de la compagnie [chiffre]");
				}
			}

			inputText[i] = input.nextLine();
			if (inputText[i].equals("")) {
				inputText[i] = cp[i + 1];
			} else if ((i == 1) || (i == 2)) {
				LocalDateTime date = DateTime.convertDate(inputText[i]);
				if (date == null)
					return;
			}
		}
		int id = Integer.parseInt(cp[0]);
		String modif = computerService.updateComputer(id, inputText, true);

		if (modif.equals(yes))
			System.out.println("modification reussie");
		else
			System.err.println("modification non effectué " + modif);

	}

	/**
	 * delete a computer
	 */
	private  void deleteComputerMenu() {
		int choice = 0;
		showListComputerMenu();

		boolean delete;
		do {
			System.out.println("selectinnez un identifiant d'ordinateur parmis cette liste, entrer 0 pour quitter");
			choice = input.nextInt();
			if(choice==0) break;
			delete = computerService.deleteCpmouter(choice);
			if (delete) {
				System.out.println("l'ordinateur " + choice + " a été bien supprimer");
			} else {
				System.out.println("erreur suppresion l'ordinateur que vous voulez supprimer n'existe pas");
			}

		} while (choice != 0);
	}

	private static void printText(int i) {

		switch (i) {
		case 0:
			System.out.println("entrez le nom de l'ordinateur");
			break;
		case 1:
			System.out.println("entrez date d'entrée de l'ordinateur format[YYYY-MM-DD HH:mm:ss]");
			break;
		case 2:
			System.out.println("entrez date d'arrêt de l'ordinateur format[YYYY-MM-DD HH:mm:ss]");
			break;
		case 3:
			System.out.println("voulez vous affichez toute les compagnies existantes? O/N");
			break;

		}

	}

	private  void detailsComputerMenuById(int id) {

		Computer comp =computerService.showDetailsComputer(id);
		System.out.println(comp.toString(comp.getCompagnyId()));
	}

	public static void main(String[] args) throws SQLException {
		Main vue= new Main();
		int choice = 0;
		do {
			switch (choice) {
			case 1:
				vue.showListComputerMenu();
				break;
			case 2:
				vue.showListCompanyMenu();
				break;
			case 3:
				vue.detailsComputerMenu();
				break;
			case 4:
				vue.addComputerMenu();
				break;
			case 5:
				vue.updateComputerMenu();
				break;
			case 6:
				vue.deleteComputerMenu();
				break;

			default:
				break;
			}
			choice = mainMenu();
		} while (choice != 7);

	}
}
