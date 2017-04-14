package fr.ebiz.computerDatabase.service;

import java.time.LocalDateTime;
import java.util.List;

import fr.ebiz.computerDatabase.mapper.CompanyMapper;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.validator.DateTime;

public class ComputerService {

	private ComputerMapper computerMap;
	private CompanyMapper comanyMapper;
	public ComputerService() {
		computerMap= new ComputerMapper();
		comanyMapper= new CompanyMapper();
	}
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
	public String updateComputer(int id, String input[], boolean action) {

		Computer computer;
		String name = input[0];
		LocalDateTime dateIn;
		LocalDateTime dateOut;
		String invalideDate = "la date d'arret avant la date d'entrée";
		String yes = "yes";
		String no = "l'identifiant de la companie n'existe pas";

		if(input[1]!=null)
			dateIn=DateTime.convertDate(input[1]);
		else
			dateIn=null;
		if(input[2]!=null)
			dateOut=DateTime.convertDate(input[2]);
		else
			dateOut=null;

		if ((input[1] != null) && (input[2] != null))
			if (DateTime.dateCompare(input[1], input[2]) == false)
				return invalideDate;

		if (action == false) {
			if (input[3] != null) {
				Company cp = comanyMapper.getCompanyIDMapper(Integer.parseInt(input[3]));
				computer = new Computer(name, dateIn, dateOut, cp.getId());
			} else
				computer = new Computer(name, dateIn, dateOut, 0);

			if (computerMap.insertMapper(computer) == 1)
				return yes;

			else
				return no;
		} else {
			if (input[3] != null) {
				Company cp = comanyMapper.getCompanyIDMapper(Integer.parseInt(input[3]));
				computer = new Computer(id, name, dateIn, dateOut, cp.getId());
			} else
				computer = new Computer(id, name, dateIn, dateOut, 0);
			if (computerMap.updateMapper(computer) == 1)

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
	public  boolean deleteCpmouter(int id) {

		int deleletOK = computerMap.deleteMapper(id);

		if (deleletOK == 1)
			return true;
		else
			return false;
	}

	/**
	 *
	 * @return list of computer
	 */

	public List<Computer> getAllComputer() {
		return computerMap.getAllComputerMapper();
	}

	/**
	 *
	 * @return sublist  of computer
	 */

	public  List<Computer> getAllComputer(int limit) {
		return computerMap.getAllComputerMapper(limit);
	}



	/**
	 *
	 * @param id
	 *            of computer
	 * @return a computer
	 */
	public Computer showDetailsComputer(int id) {
		return computerMap.getComputerByIdMapper(id);
	}

	public List<Computer> getComputerByNameMapper(String name) {

		return computerMap.getComputerByNameMapper(name);

	}

}
