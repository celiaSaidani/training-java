package fr.ebiz.computerDatabase.service;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.ComputerDAO;
import fr.ebiz.computerDatabase.validator.ComputerValidator;
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
    public boolean InsertComputer(ComputerDTO comp) {
        
        try {
            System.out.println(ComputerValidator.isValid(comp)? "dto valide": "No valide");
        
            ComputerValidator.isValid(comp);
            Computer computer;
            String name = comp.getNameComp();
            String companyId=comp.getIdCompany();
            LocalDateTime dateIn=null;
            LocalDateTime dateOut=null;
            
            if(comp.getDateIn()!=null){
                dateIn=DateTime.convertDate(comp.getDateIn().trim().concat(" 00:00:00"));
            }
            if(comp.getDateOut()!=null){
            dateOut=DateTime.convertDate(comp.getDateOut().trim().concat(" 00:00:00"));
            }
            
            if (comp.getIdCompany()!=null) {
                CompanyDTO cp = companyService.getCompanybyId(Integer.parseInt(companyId));
                computer = new Computer(name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
            } else
                  computer = new Computer(name, dateIn, dateOut, 0);
    
                if (computerDao.insert(computer) == 1){
                    return true;
                }
                else{
                    return false;
                }
        } catch (DateTimeParseException |NullPointerException e) {
                    System.err.println(e.getMessage());
          }
        return false;

    }
    public Boolean updateComputer(int id, ComputerDTO comp) {
        return false;
       /* else {
            if (input[3] != null) {
                CompanyDTO cp  = companyService.getCompanybyId(Integer.parseInt(input[3]));
                computer = new Computer(id, name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
            } else
                computer = new Computer(id, name, dateIn, dateOut, 0);
            if (computerDao.update(computer) == 1)

                return true;
            else
                return false;

        }*/
        
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
