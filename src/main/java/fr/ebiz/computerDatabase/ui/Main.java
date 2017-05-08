package fr.ebiz.computerDatabase.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;

public class Main {

  static Scanner input = new Scanner(System.in);
  private ComputerService computerService;
  private CompanyService companyService;

  /**
   * Default constructor.
   */
  public Main() {
    computerService = new ComputerService();
    companyService = new CompanyService();
  }

  /**
   * Main menu.
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
   * menu to show the details of a computer selected by id.
   */
  private void detailsComputerMenu() {
    int choice = 0;
    List<ComputerDTO> computer;
    try {
      computer = computerService.getAllComputer();
      final String response = "cet ordinateur n'existe pas veuillez bien regarder la liste";
      do {
        for (ComputerDTO cp : computer) {
          System.out.println(cp.getIdComp() + "\t" + cp.getNameComp());
        }
        System.out.println("entrez 0 pour quitter,ou l'identifiant de l'ordinateur");
        choice = input.nextInt();
        ComputerDTO comp = computerService.showDetailsComputer(choice);

        if (comp.getIdComp() == "0") {
          System.out.println(response);
        } else {
          System.out.println(comp.toString(comp.getIdCompany()));
        }

      } while (choice == 0);
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * menu to show all Company in database.
   */

  private void showListCompanyMenu() {
    String resp;
    int cpt = 0;
    do {
      System.out.println("Entrez Q pour quitter,cliquez entrer pour continuer");
      resp = input.nextLine();

      List<CompanyDTO> company;
      try {
        company = companyService.getAllCompanyPage(cpt);
        if (company.isEmpty()) {
          break;
        } else {

          for (CompanyDTO cp : company) {
            System.out.println(cp.getIdCompany() + "\t" + cp.getNameCompany());
          }
          cpt = cpt + 10;
          System.out.println("NEXT>>");

        }

      } catch (ServiceException e) {
        System.err.println(e.getMessage());
      }

    } while (!resp.equals("Q"));

  }

  /**
   * menu to show all computer in database.
   */
  private void showListComputerMenu() {
    String resp = null;
    int cpt = 0;
    List<ComputerDTO> computer;
    do {
      System.out.println("Entrez Q pour quitter,cliquez entrer pour continuer");
      try {
        ComputerDTOPage data = computerService.getAllComputerPage(cpt, 100);
        computer = data.getComputersDTO();
        resp = input.nextLine();
        if (computer.isEmpty()) {
          break;
        } else {
          for (ComputerDTO cp : computer) {
            System.out.println(cp.getIdComp() + "\t" + cp.getNameComp());
          }
          cpt = cpt + 100;
          System.err.println(cpt);
          System.out.println("NEXT>>");

        }
      } catch (ServiceException e) {
        System.err.println(e.getMessage());
      }

    } while (!resp.equals("Q"));

  }

  /**
   * menu for adding computer.
   */
  private void addComputerMenu() {
    int nbrAtt = 4;
    String[] inputText = new String[nbrAtt];
    String[] inputA = {"nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie" };
    String response;

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

    }
    try {
      computerService
          .insertComputer(new ComputerDTO(inputText[0], inputText[1], inputText[2], inputText[3]));
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * update a computer.
   */

  private void updateComputerMenu() {

    int choice = 0;
    showListComputerMenu();
    System.out.println("tapez l'identifiant de l'ordinateur");
    choice = input.nextInt();
    detailsComputerMenuById(choice);
    ComputerDTO computer;
    try {
      computer = computerService.showDetailsComputer(choice);
      String[] cp = {computer.getIdComp(), computer.getNameComp(), computer.getDateIn(),
          computer.getDateOut(), computer.getIdCompany() };

      String[] inputA = {"nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie" };
      int nbrAtt = 4;
      String[] inputText = new String[nbrAtt];
      String response;

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
        if (!inputText.equals("")) {
          cp[i] = inputText[i];
        }

      }
      computerService.updateComputer(new ComputerDTO(cp[0], cp[1], cp[2], cp[3]));
    } catch (ServiceException e) {
      System.err.println(e.getMessage());

    }
  }

  /**
   * delete a computer.
   */
  private void deleteComputerMenu() {
    int choice = 0;
    showListComputerMenu();

    boolean delete;
    do {
      System.out.println(
          "selectinnez un identifiant d'ordinateur parmis cette liste, entrer 0 pour quitter");
      choice = input.nextInt();
      if (choice == 0) {
        break;
      }
      delete = true;
      // computerService.deleteCpmouter(choice);
      if (delete) {
        System.out.println("l'ordinateur " + choice + " a été bien supprimer");
      } else {
        System.out.println("erreur suppresion l'ordinateur que vous voulez supprimer n'existe pas");
      }

    } while (choice != 0);
  }

  /**
   * print text on the console.
   * @param i
   *          message output
   */
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

  /**
   * @param id
   *          of computer
   */
  private void detailsComputerMenuById(int id) {

    ComputerDTO comp;
    try {
      comp = computerService.showDetailsComputer(id);
      System.out.println(comp.getIdCompany());
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * Main methode.
   * @param args
   *          null
   * @throws SQLException
   */
  public static void main(String[] args) {
    Main vue = new Main();
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
        System.out.println("choisissez un numéro entre 1 et 7");
      }
      choice = mainMenu();
    } while (choice != 7);

  }
}
