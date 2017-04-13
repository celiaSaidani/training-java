package fr.ebiz.computerDatabase.service;

import java.time.LocalDateTime;
import java.util.List;

import fr.ebiz.computerDatabase.mapper.CompanyDAO;
import fr.ebiz.computerDatabase.mapper.ComputerDAO;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.utils.DateTime;

public class ComputerService {

	/**
	 * updateComputer has 2 actions: insert if action equal false, update if
	 * action equal false
	 * 
	 * @param id
	 *            of computer 0 if action is an update
	 * @param input
	 *            is an array of attribute of a computer: name,date introduced,
	 *            date disconnected,id company
	 * @param action
	 *            true or false
	 * @return
	 */
	public static String updateComputer(int id, String input[], boolean action) {

		Computer computer;
		String name = input[0];
		LocalDateTime dateIn;
		LocalDateTime dateOut;
		String invalideDate = "la date d'arret avant la date d'entrée";
		String yes = "yes";
		String no = "l'identifiant de la companie n'existe pas";

		if (input[1] == null) {
			dateIn = null;
		} else {
			dateIn = DateTime.convertDate(input[1]);
			if (dateIn == null)
				return input[1];
		}
		if (input[2] == null) {
			dateOut = null;
		} else {

			dateOut = DateTime.convertDate(input[2]);
			if (dateOut == null)
				return input[2];
		}
		if ((input[1] != null) && (input[2] != null))
			if (DateTime.dateCompare(input[1], input[2]) == false)
				return invalideDate;

		if (action == false) {
			if (input[3] != null) {
				Company cp = CompanyDAO.getCompanyID(Integer.parseInt(input[3]));
				computer = new Computer(name, dateIn, dateOut, cp.getId());
			} else
				computer = new Computer(name, dateIn, dateOut, 0);

			if (ComputerDAO.insert(computer) == 1)
				return yes;

			else
				return no;
		} else {
			if (input[3] != null) {
				Company cp = CompanyDAO.getCompanyID(Integer.parseInt(input[3]));
				computer = new Computer(id, name, dateIn, dateOut, cp.getId());
			} else
				computer = new Computer(id, name, dateIn, dateOut, 0);
			if (ComputerDAO.update(computer) == 1)

				return yes;
			else
				return no;

		}

	}

	/**
	 * 
	 * @param id
	 *            of computer to delete
	 * @return true if delete correct, false else
	 */
	public static boolean deleteCpmouter(int id) {

		int deleletOK = ComputerDAO.delete(id);

		if (deleletOK == 1)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @return list of computer
	 */

	public static List<Computer> getAllComputer() {
		return ComputerDAO.getAllComputer();
	}

	/**
	 * 
	 * @param id
	 *            of computer
	 * @return a computer
	 */
	public static Computer showDetailsComputer(int id) {
		return ComputerDAO.getComputerById(id);
	}

}
