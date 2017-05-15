package fr.ebiz.computerDatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.computerDatabase.Exception.DAOException;
import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistance.ComputerDAO;
import fr.ebiz.computerDatabase.persistance.ConnectionManager;

import fr.ebiz.computerDatabase.validator.ComputerValidator;
import fr.ebiz.computerDatabase.validator.DateTime;

public class ComputerService {

  private ComputerMapper computerMap;
  private ComputerDAO computerDao;
  private CompanyService companyService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
  private ConnectionManager cm = ConnectionManager.getInstance();
  

  /**
   * Constructor.
   */
  public ComputerService() {
    computerMap = new ComputerMapper();
    computerDao = new ComputerDAO();
    companyService = new CompanyService();
  }

  /**
   * @param comp
   *          insert a computerDTO
   * @return true if insertion ok else false
   * @throws ServiceException
   *           for errors in computerDTO
   */
  public boolean insertComputer(ComputerDTO comp) throws ServiceException {

    try {
      ComputerValidator.isValid(comp);
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
        CompanyDTO cp = companyService.getCompanybyIdLocal(Integer.parseInt(companyId));
        computer = new Computer(name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
      } else {
        computer = new Computer(name, dateIn, dateOut, 0);
      }
      computerDao.insert(computer);
      return true;

    } catch (DateTimeParseException | NullPointerException | DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function insert Computer");
      throw new ServiceException("can't insert computer");
    }

  }

  /**
   * @param comp
   *          update a computerDTO
   * @return true if update ok else false
   * @throws ServiceException
   *           for errors in computerDTO
   */
  public Boolean updateComputer(ComputerDTO comp) throws ServiceException {
    try {
     
      ComputerValidator.isValid(comp);
      Computer computer;
      int id = Integer.parseInt(comp.getIdComp());
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
        CompanyDTO cp = companyService.getCompanybyIdLocal(Integer.parseInt(companyId));
        computer = new Computer(id, name, dateIn, dateOut, Integer.parseInt(cp.getIdCompany()));
      } else {
        computer = new Computer(id, name, dateIn, dateOut, 0);
      }
      computerDao.update(computer);
      return true;

    } catch (DateTimeParseException | NullPointerException | DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function update Computer");
      throw new ServiceException("can't update computer");
    } 
  }

  /**
   * @param id
   *          of computer to delete
   * @return true if delete correct, false else
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public boolean deleteCpmouter(int id) throws ServiceException {

    try {
      computerDao.delete(id);
      return true;
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function delete Computer");
      throw new ServiceException("can't delete computer");
    } 
  }

  /**
   * @return list of computerDTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */

  public List<ComputerDTO> getAllComputer() throws ServiceException {
    List<Computer> allComp;
    try {
      allComp = computerDao.getAllComputer();
      return computerMap.getComputerDTOs(allComp);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getAllComputer");
      throw new ServiceException("can't get all computer");
    } 
  }

  /**
   * @param start
   *          page
   * @param end
   *          page
   * @return list of computer page
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */

  public ComputerDTOPage getAllComputerPage(int start, int end) throws ServiceException {
    ComputerDTOPage data = new ComputerDTOPage();
    List<Computer> allComp;
    try {
      cm.startTransaction();
      allComp = computerDao.getAllComputerPage(start, end);
      data.setComputersDTO(computerMap.getComputerDTOs(allComp));
      data.setCount(computerDao.countTotalLine());
      cm.commit();
      return data;
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      cm.rollback();
      LOGGER.error("[Error Service] in function getAllComputerPage");
      throw new ServiceException("can't get all computer by limit");
    }
    finally {
      
            cm.closeConnection();
       
    }
  }

  /**
   * @param id
   *          of computer
   * @return a computerDTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public ComputerDTO showDetailsComputer(int id) throws ServiceException {
    Computer cp;
    try {
      cp = computerDao.getComputerById(id);
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
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function showDetailsComputer");
      throw new ServiceException("can't get computer Details");
    }

  }

  /**
   * @param name
   *          computer name
   * @return list of computerDTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public List<ComputerDTO> getComputerByName(String name) throws ServiceException {
    List<Computer> cp;
    try {
      cp = computerDao.getComputerByName(name);
      return computerMap.getComputerDTOs(cp);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getComputerByName");
      throw new ServiceException("can't get list of computer by name");
    } 

  }

  /**
   * @param name
   *          of computer
   * @param start
   *          page
   * @param end
   *          page
   * @return list of computer DTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public List<ComputerDTO> search(String name, int start, int end) throws ServiceException {
    List<Computer> cp;
    try {
      cp = computerDao.search(name, start, end);
      return computerMap.getComputerDTOs(cp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function search");
      throw new ServiceException("can't find list of computer");
    } 
  }

  /**
   * @return number line of computer in dataBase
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public int getCount() throws ServiceException {
    try {
      return computerDao.countTotalLine();
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getCount");
      throw new ServiceException("can't count computer");
    }

  }

  /**
   * @param search
   *          computer name to search
   * @return number line of computer in dataBase
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public int getCount(String search) throws ServiceException {
    try {
      return computerDao.countTotalLine(search);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getCount ");
      throw new ServiceException("can't count computer for on search");
    } 


  }

  /**
   * @param start
   *          page
   * @param end
   *          page
   * @param reqorder
   *          column name
   * @param reqBy
   *          ASC or DESC
   * @param search
   *          name of computer
   * @return list of computerDTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */
  public List<ComputerDTO> searchOrderBy(int start, int end, String reqorder, String reqBy, String search)
      throws ServiceException {

    reqorder = reqorder.trim();
    search = search.trim();

    if (reqBy.equals("up")) {
      reqBy = "ASC";
    } else {
      reqBy = "DESC";
    }
    List<Computer> lcp;
    try {
      lcp = computerDao.getComputerOrderBy(start, end, reqorder, reqBy, search);
      return computerMap.getComputerDTOs(lcp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getComputerOrder");
      throw new ServiceException("can't get computer orderBy");
    }

  }

  /**
   * @param start
   *          page
   * @param end
   *          page
   * @param reqBy
   *          ASC or DESC
   * @param name
   *          column name
   * @return list of computerDTO
   * @throws ServiceException
   *           when we catch DAOException from computerDAO
   */

  public List<ComputerDTO> getComputerOrder(int start, int end, String reqBy, String name)
      throws ServiceException {

    name = name.trim();
    if (reqBy.equals("up")) {
      reqBy = "ASC";
    } else {
      reqBy = "DESC";
    }
    List<Computer> lcp;
    try {
      lcp = computerDao.getComputerOrderBy(start, end, reqBy, name);
      return computerMap.getComputerDTOs(lcp);

    } catch (DAOException e) {
      System.err.println(e.getMessage());
      LOGGER.error("[Error Service] in function getComputerOrder");
      throw new ServiceException("can't get computer orderBy");
    } 
  }

}
