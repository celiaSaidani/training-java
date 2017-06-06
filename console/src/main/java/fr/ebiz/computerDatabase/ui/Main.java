package fr.ebiz.computerDatabase.ui;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.DTOPage;
import fr.ebiz.computerDatabase.exception.NotFoundException;
import fr.ebiz.computerDatabase.exception.UpdateException;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.validator.ComputerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;
    private static final String COMPANY_URI = "http://localhost:8080/api/companies";
    private static final String COMPUTER_URI = "http://localhost:8080/api/computers";
    private Client client = ClientBuilder.newClient();

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
        try {
            System.out.println("inserez un identifiant d'un ordinateur");
            choice = input.nextInt();
            ComputerDTO computerDTO = client.target(COMPUTER_URI)
                    .path(String.valueOf(choice))
                    .request(MediaType.APPLICATION_JSON)
                    .get(ComputerDTO.class);
            System.out.println(computerDTO);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * menu to show all Company in database.
     */

    private void showListCompanyMenu() {
        List<CompanyDTO> company;
        try {
            company = client.target(COMPANY_URI)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<CompanyDTO>>() {
                    });
            if (company.isEmpty()) {
                System.out.println("aucune companie trouvée");
            } else {

                for (CompanyDTO cp : company) {
                    System.out.println(cp.getIdCompany() + "\t" + cp.getNameCompany());
                }
            }
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
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
            DTOPage data = null;
            //computerService.getAllComputerPage(cpt, 100);
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

        } while (!resp.equals("Q"));

    }

    /**
     * menu for adding computer.
     */
    private void addComputerMenu() {
        int nbrAtt = 4;
        String[] inputText = new String[nbrAtt];
        String[] inputA = {"nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie"};
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
            ComputerDTO computerDTO = new ComputerDTO(inputText[0], inputText[1], inputText[2], inputText[3]);
            if (ComputerValidator.isValid(computerDTO)) {
                computerService.insertComputer(computerDTO);
            }

        } catch (NullPointerException | DateTimeParseException | UpdateException e) {
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

        ComputerDTO computer;
        try {
            computer = null;
            detailsComputerMenuById(choice);
            computerService.showDetailsComputer(new Long(choice));
            String[] cp = {computer.getIdComp(), computer.getNameComp(), computer.getDateIn(),
                    computer.getDateOut(), computer.getIdCompany()};

            String[] inputA = {"nom", "date d'entrée", "date d'arrêt", "identifiant de la compagnie"};
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
        } catch (UpdateException e) {
            System.err.println(e.getMessage());

        }
    }

    /**
     * delete a computer.
     */
    private void deleteComputerMenu() {
        int choice = 0;
        System.out.println("inserez un identifiant a supprimer");
        choice = input.nextInt();
        client.target(COMPUTER_URI)
                .path(String.valueOf(choice))
                .request(MediaType.APPLICATION_JSON).delete();
        System.out.println("l'ordinateur " + choice + " a été bien supprimer");
    }


    /**
     * print text on the console.
     *
     * @param i message output
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
     * @param id of computer
     */
    private void detailsComputerMenuById(int id) {

        ComputerDTO comp;
        comp = null;
        try {
            computerService.showDetailsComputer(new Long(id));
        } catch (UpdateException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Main methode.
     *
     * @param args null
     * @throws SQLException
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cli.xml");
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
