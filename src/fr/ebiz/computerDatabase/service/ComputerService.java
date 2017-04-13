package fr.ebiz.computerDatabase.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import fr.ebiz.computerDatabase.mapper.CompanyDAO;
import fr.ebiz.computerDatabase.mapper.ComputerDAO;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;

public class ComputerService {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String updateComputer(int id,String input[],boolean action) {

		Computer computer;
		String name = input[0];
		LocalDateTime dateIn;
		LocalDateTime dateOut;
		String invalide = "la date d'arret avant la date d'entrée";
		String yes = "yes";
		String no = "l'identifiant de la companie n'existe pas"; // verify date

		if (input[1] == null) {
			dateIn = null;
		} else {
			dateIn = convertDate(input[1]);
			if (dateIn == null)
				return input[1];
		}
		if (input[2] == null) {
			dateOut = null;
		} else {

			dateOut = convertDate(input[2]);
			if (dateOut == null)
				return input[2];
		}
		if ((input[1] != null) && (input[2] != null))
			if (dateCompare(input[1], input[2]) == false)
				return invalide;

		Company cp = CompanyDAO.getCompanyID(Integer.parseInt(input[3]));

		
		
		if(action==true){
			computer = new Computer(name, dateIn, dateOut, cp.getId());
			if (ComputerDAO.insert(computer) == 1)
				return yes;

			else
				return no;
		}
		else{
			computer = new Computer(id,name, dateIn, dateOut, cp.getId());
			if (ComputerDAO.update(computer)==1);
			
			return yes;

			
		}

	}

	public static boolean deleteCpmouter(int id) {

		int deleletOK = ComputerDAO.delete(id);

		if (deleletOK == 1)
			return true;
		else
			return false;
	}

	public static boolean updateComputer() {
		return false;
	}

	public static List<Computer> getAllComputer() {
		return ComputerDAO.getAllComputer();
	}

	public static Computer showDetailsComputer(int id) {
		return ComputerDAO.getComputerById(id);
	}

	private static LocalDateTime convertDate(String date) {
		try {

			LocalDateTime formatDateTime = LocalDateTime.parse(date, formatter);
			return formatDateTime;
		} catch (DateTimeParseException e) {
			return null;
		}

	}

	public static String DateToString(LocalDateTime date) {
			if(date==null)
				return null;
		
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        String dateString = date.format(formatter);

	       return dateString;

	}
	private static boolean dateCompare(String date1, String date2) {
		try {

			LocalDateTime formatDateTime = LocalDateTime.parse(date1, formatter);
			LocalDateTime formatDateTime2 = LocalDateTime.parse(date2, formatter);
			return formatDateTime.isBefore(formatDateTime2);
		} catch (DateTimeParseException e) {
			return false;
		}

	}

}
