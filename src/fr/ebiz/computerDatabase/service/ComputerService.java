package fr.ebiz.computerDatabase.service;

import java.time.LocalDateTime;

import fr.ebiz.computerDatabase.mapper.ComputerDAO;
import fr.ebiz.computerDatabase.model.Computer;

public class ComputerService {

	/**
	 * 
	 * @param id
	 * @param name
	 * @param dateIn
	 * @param dateOut
	 * @param idCompany
	 * @return i
	 */
	public static boolean addComputer(int id,String name,String dateIn,String dateOut,int idCompany) {
	
		return false;
		
	}
	public static boolean deleteCpmouter(int id) {
		int deleletOK=ComputerDAO.delete(id);
		
		if(deleletOK==1)
			return true;
		else
			return false;
	}
	
	public static boolean updateComputer() {
		return false;
	}
	
	public static boolean getAllComputer() {
		return false;
	}
	
	public static Computer showDetailsComputer(int id){
		return ComputerDAO.getComputerById(id);
	}

}
