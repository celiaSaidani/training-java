package fr.ebiz.computerDatabase.service;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.ComputerDAO;
import fr.ebiz.computerDatabase.validator.DateTime;

public class ComputerService {

    private ComputerMapper computerMap;
    private ComputerDAO computerDao;
    private CompanyService companyService;

    public ComputerService() {
        computerMap = new ComputerMapper();
        computerDao = new ComputerDAO();
        companyService= new CompanyService();
    }

    /**
     * updateComputer has 2 actions: insert if action equal false, update if
     * action equal false
     * @param id of computer 0 if action is an update
     * @param input is an array of attribute of a computer: name,date
     *            introduced, date disconnected,id company
     * @param action true or false
     * @return
     */
    public boolean updateComputer(int id, String input[], boolean action) {

        Computer computer;
        String name = input[0];
        LocalDateTime dateIn;
        LocalDateTime dateOut;
        String invalideDate = "la date d'arret avant la date d'entrée";
        String nameRequired="nom obligatoire";

        if(input[0].equals(""))
            throw new NullPointerException(nameRequired);

        if (!input[1].equals(""))
            dateIn = DateTime.convertDate(input[1]);
        else
            dateIn = null;
        if (!input[2].equals(""))
            dateOut = DateTime.convertDate(input[2]);
        else
            dateOut = null;

        if ((!input[1].equals("")) && (!input[2].equals("")))
            if (DateTime.dateCompare(input[1], input[2]) == false){
                System.err.println(invalideDate);
                return false;
            }

        if (action == false) {
            if (input[3] != null) {

                CompanyDTO cp = companyService.getCompanybyId(Integer.parseInt(input[3]));
                computer = new Computer(name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
            } else
                computer = new Computer(name, dateIn, dateOut, 0);

            if (computerDao.insert(computer) == 1)
                return true;

            else
                return false;
        } else {
            if (input[3] != null) {
                CompanyDTO cp  = companyService.getCompanybyId(Integer.parseInt(input[3]));
                computer = new Computer(id, name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
            } else
                computer = new Computer(id, name, dateIn, dateOut, 0);
            if (computerDao.update(computer) == 1)

                return true;
            else
                return false;

        }

    }

    /**
     * @param id of computer to delete
     * @return true if delete correct, false else
     */
    public boolean deleteCpmouter(int id) {

        int deleletOK = computerDao.delete(id);

        if (deleletOK == 1)
            return true;
        else
            return false;
    }

    /**
     * @return list of computer
     */

    public List<ComputerDTO> getAllComputer() {
        ResultSet rs = computerDao.getAllComputer();
        System.out.println("resultsut "+rs);
        return computerMap.getAllComputerMapper(rs);
    }

    /**
     * @return sublist of computer
     */

    public List<ComputerDTO> getAllComputer(int limit) {
        ResultSet rs = computerDao.getAllComputer(limit);
        return computerMap.getAllComputerMapper(rs);
    }

    /**
     * @param id of computer
     * @return a computer
     */
    public ComputerDTO showDetailsComputer(int id) {
        ResultSet rs = computerDao.getComputerById(id);
        return computerMap.getComputerByIdMapper(id,rs);
    }

    public List<ComputerDTO> getComputerByNameMapper(String name) {
        ResultSet rs = computerDao.getComputerByName(name);
        return computerMap.getComputerByNameMapper(rs);

    }

}
