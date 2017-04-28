package fr.ebiz.computerDatabase.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
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
            //System.out.println(ComputerValidator.isValid(comp)? "dto valide": "No valide");
        
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
    public Boolean updateComputer( ComputerDTO comp) {
    	try {
    		ComputerValidator.isValid(comp);
    		Computer computer;
    		 int id=Integer.parseInt(comp.getIdComp());
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
             
             if (!comp.getIdCompany().equals("")) {
                 CompanyDTO cp = companyService.getCompanybyId(Integer.parseInt(companyId));
                 computer = new Computer(id,name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
             } else
                   computer = new Computer(id,name, dateIn, dateOut, 0);
     
                 if (computerDao.update(computer) == 1){
                     return true;
                 }
                 else{
                     return false;
                 }
		} catch (DateTimeParseException |NullPointerException e) {
            System.err.println(e.getMessage());
  }
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
        List<Computer> allComp = computerDao.getAllComputer();
        return computerMap.getComputerDTOs(allComp);
    }

    /**
     * @return sublist of computer
     */

    public List<ComputerDTO> getAllComputerPage(int start,int end) {
        List<Computer> allComp = computerDao.getAllComputerPage(start,end);
        return computerMap.getComputerDTOs(allComp);
    }
    public List<ComputerDTO> getComputerOrder(int start, int end, String reqBy,String name) {
        System.out.println(reqBy);
        name=name.trim();
        if(reqBy.equals("up")){
            reqBy="ASC";
        }
        else{
            reqBy="DECS";
        }
        List<Computer> lcp= computerDao.getComputerOrder(start,end,reqBy,name);
         return computerMap.getComputerDTOs(lcp) ;
     }

    /**
     * @param id of computer
     * @return a computer
     */
    public ComputerDTO showDetailsComputer(int id) {
        Computer cp = computerDao.getComputerById(id);
        ComputerDTO cpDto= computerMap.getComputerDTO(cp);
        if(cpDto.getDateIn()!=null){
           
            String newDate=cpDto.getDateIn().substring(0,10);
            cpDto.setDateIn(newDate);
        }
        if(cpDto.getDateOut()!=null){
            String newDate=cpDto.getDateOut().substring(0,10);
            cpDto.setDateOut(newDate);
        }
        
        return cpDto;
    }

    public List<ComputerDTO> getComputerByNameMapper(String name) {
        List<Computer> cp = computerDao.getComputerByName(name);
        return computerMap.getComputerDTOs(cp);

    }
    public List<ComputerDTO> Search(String name, int start, int end){
    	List<Computer> cp=computerDao.Serach(name,start,end);
    	return computerMap.getComputerDTOs(cp);
    }
    public int getCount() {
       return computerDao.CountTotalLine();

    }
    public int getCount(String search) {
        return computerDao.CountTotalLine(search);

     }

   

}
