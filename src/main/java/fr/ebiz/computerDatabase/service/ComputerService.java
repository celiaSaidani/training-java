package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.exception.DAOException;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistence.ComputerDAO;
import fr.ebiz.computerDatabase.validator.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ComputerService {
    @Autowired
    private ComputerMapper computerMap;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private CompanyService companyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    //private Utils cm = Utils.getInstance();

    /**
     * Constructor.

     public ComputerService() {
     computerMap = new ComputerMapper();
     computerDAO = new ComputerDAO();
     companyService = new CompanyService();
     }

     */

    /**
     * @param comp insert a computerDTO
     * @return true if insertion ok else false
     * @throws ServiceException for errors in computerDTO
     */
    public boolean insertComputer(ComputerDTO comp) throws ServiceException {

        try {
            //ComputerValidator.isValid(comp);
            System.err.println(">>>>> IN SERVICE");
            Computer computer;
            String name = comp.getNameComp();
            String companyId = comp.getIdCompany();
            LocalDateTime dateIn = null;
            LocalDateTime dateOut = null;

            if (comp.getDateIn() != null) {
                dateIn = DateTime.convertDate(comp.getDateIn().trim().concat(" 00:00:00"));
            }
            if (comp.getDateOut() != null) {
                dateOut = DateTime.convertDate(comp.getDateOut().trim().concat(" 00:00:00"));
            }

            if (comp.getIdCompany() != null) {
                CompanyDTO cp = companyService.getCompanybyIdLocal(Long.parseLong(companyId));

                computer = new Computer(name, dateIn, dateOut, Long.parseLong(cp.getIdCompany()));
            } else {
                Long companyid = 0L;
                computer = new Computer(name, dateIn, dateOut, companyid);
            }
            computerDAO.saveAndFlush(computer);

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function insert Computer");
            throw new ServiceException("can't insert computer");
        }

    }

    /**
     * @param comp update a computerDTO
     * @return true if update ok else false
     * @throws ServiceException for errors in computerDTO
     */
    public Boolean updateComputer(ComputerDTO comp) throws ServiceException {
        try {

            // ComputerValidator.isValid(comp);
            Computer computer;
            Long id = Long.parseLong(comp.getIdComp());
            String name = comp.getNameComp();
            String companyId = comp.getIdCompany();
            LocalDateTime dateIn = null;
            LocalDateTime dateOut = null;

            if (comp.getDateIn() != null) {
                dateIn = DateTime.convertDate(comp.getDateIn().trim().concat(" 00:00:00"));
            }
            if (comp.getDateOut() != null) {
                dateOut = DateTime.convertDate(comp.getDateOut().trim().concat(" 00:00:00"));
            }

            if (!comp.getIdCompany().equals("")) {
                System.err.println(">>>>> IN Service");
                CompanyDTO cp = companyService.getCompanybyIdLocal(Long.parseLong(companyId));
                computer = new Computer(id, name, dateIn, dateOut, Long.parseLong(cp.getIdCompany()));
            } else {
                Long companyid = 0L;
                computer = new Computer(id, name, dateIn, dateOut, companyid);
            }
            computerDAO.save(computer);
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function update Computer");
            throw new ServiceException("can't update computer");
        }
    }

    /**
     * @param id of computer to delete
     * @return true if delete correct, false else
     * @throws ServiceException when we catch DAOException from computerDAO
     */
    public boolean deleteComputer(Long id) throws ServiceException {

        try {
            computerDAO.delete(id);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function delete Computer");
            throw new ServiceException("can't delete computer");

        }
    }

    /**
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */

    public List<ComputerDTO> getAllComputer() throws ServiceException {
        List<Computer> allComp;
        try {
            allComp = computerDAO.findAll();
            return computerMap.getComputerDTOs(allComp);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getAllComputer");
            throw new ServiceException("can't get all computer");
        }
    }

    /**
     * @param start page
     * @param end   page
     * @return list of computer page
     * @throws ServiceException when we catch DAOException from computerDAO

    @Transactional
    public ComputerDTOPage getAllComputerPage(int start, int end) throws ServiceException {
        ComputerDTOPage data = new ComputerDTOPage();
        List<Computer> allComp;
        try {
            allComp = computerDAO.getAllComputerPage(start, end);
            data.setComputersDTO(computerMap.getComputerDTOs(allComp));
            data.setCount(computerDAO.countTotalLine());
            return data;
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getAllComputerPage");
            throw new ServiceException("can't get all computer by limit");
        }
    }*/

    /**
     * @param id of computer
     * @return a computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */
    public ComputerDTO showDetailsComputer(Long id) throws ServiceException {
        Computer cp;
        try {
            cp = computerDAO.getOne(id);
            ComputerDTO cpDto = computerMap.getComputerDTO(cp);
            if (cpDto.getDateIn() != null) {

                String newDate = cpDto.getDateIn().substring(0, 10);
                cpDto.setDateIn(newDate);
            }
            if (cpDto.getDateOut() != null) {
                String newDate = cpDto.getDateOut().substring(0, 10);
                cpDto.setDateOut(newDate);
            }
            return cpDto;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function showDetailsComputer");
            throw new ServiceException("can't get computer Details");
        }

    }

    /**
     * @param name computer name
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */
    public List<ComputerDTO> getComputerByName(String name) throws ServiceException {
        List<Computer> cp;
        try {
            cp = computerDAO.findComputersByName(name);
            return computerMap.getComputerDTOs(cp);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerByName");
            throw new ServiceException("can't get list of computer by name");
        }

    }

    /**
     * @param name  of computer
     * @param start page
     * @param end   page
     * @return list of computer DTO
     * @throws ServiceException when we catch DAOException from computerDAO

    @Transactional
    public ComputerDTOPage search(String name, int start, int end) throws ServiceException {
        List<Computer> cp;
        ComputerDTOPage data = new ComputerDTOPage();
        try {
            cp = computerDAO.search(name, start, end);
            data.setComputersDTO(computerMap.getComputerDTOs(cp));
            data.setCount(computerDAO.countTotalLine(name));
            return data;
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            // LOGGER.error("[Error Service] in function search");
            throw new ServiceException("can't find list of computer");
        }
    }

    /**
     * @return number line of computer in dataBase
     * @throws ServiceException when we catch DAOException from computerDAO

    public int getCount() throws ServiceException {
        try {
            return computerDAO.countTotalLine();
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getCount");
            throw new ServiceException("can't count computer");
        }

    }*/

    /**
     * @param search computer name to search
     * @return number line of computer in dataBase
     * @throws ServiceException when we catch DAOException from computerDAO

    public int getCount(String search) throws ServiceException {
        try {
            return computerDAO.countTotalLine(search);
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getCount ");
            throw new ServiceException("can't count computer for on search");
        }

    }

    /**
     * @param start    page
     * @param end      page
     * @param reqorder column name
     * @param reqBy    ASC or DESC
     * @param search   name of computer
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO

    @Transactional
    public ComputerDTOPage searchOrderBy(int start, int end, String reqorder, String reqBy, String search)
            throws ServiceException {
        ComputerDTOPage data = new ComputerDTOPage();
        reqorder = reqorder.trim();
        search = search.trim();

        if (reqBy.equals("up")) {
            reqBy = "ASC";
        } else {
            reqBy = "DESC";
        }
        List<Computer> lcp;
        try {
            lcp = computerDAO.getComputerOrderBy(start, end, reqorder, reqBy, search);
            data.setComputersDTO(computerMap.getComputerDTOs(lcp));
            data.setCount(computerDAO.countTotalLine(search));
            return data;

        } catch (DAOException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerOrder");
            throw new ServiceException("can't get computer orderBy");
        }

    }

    /**
     * @param start page
     * @param end   page
     * @param reqBy ASC or DESC
     * @param name  column name
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO

    @Transactional
    public ComputerDTOPage getComputerOrder(int start, int end, String reqBy, String name) throws ServiceException {
        ComputerDTOPage data = new ComputerDTOPage();
        name = name.trim();
        if (reqBy.equals("up")) {
            reqBy = "ASC";
        } else {
            reqBy = "DESC";
        }
        List<Computer> lcp;
        try {
            lcp = computerDAO.getComputerOrderBy(start, end, reqBy, name);
            data.setComputersDTO(computerMap.getComputerDTOs(lcp));
            data.setCount(computerDAO.countTotalLine());
            return data;

        } catch (DAOException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerOrder");
            throw new ServiceException("can't get computer orderBy");
        }
    }*/

}
