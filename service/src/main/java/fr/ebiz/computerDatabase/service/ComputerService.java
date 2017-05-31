package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistence.ComputerDAO;
import fr.ebiz.computerDatabase.validator.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Transactional
@Service
public class ComputerService {
    @Autowired
    private ComputerMapper computerMap;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private CompanyService companyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    private static final  String Time=" 00:00:00";
    @Transactional
    /**
     * @param computerDTO insert a computerDTO
     * @return true if insertion ok else false
     * @throws ServiceException for errors in computerDTO
     */
    public boolean insertComputer(ComputerDTO computerDTO) throws ServiceException {

        try {
            Computer computer;
            String name = computerDTO.getNameComp();
            String companyId = computerDTO.getIdCompany();
            LocalDateTime dateIn = null;
            LocalDateTime dateOut = null;

            if (computerDTO.getDateIn() != null) {
                dateIn = DateTime.convertDate(computerDTO.getDateIn().trim().concat(Time));
            }
            if (computerDTO.getDateOut() != null) {
                dateOut = DateTime.convertDate(computerDTO.getDateOut().trim().concat(Time));
            }

            if (computerDTO.getIdCompany() != "") {
                CompanyDTO companyDTO = companyService.getCompanybyIdLocal(Long.parseLong(companyId));
                Company company = new Company(Long.parseLong(companyId), companyDTO.getNameCompany());

                computer = new Computer(name, dateIn, dateOut, company);
            } else {
                computer = new Computer(name, dateIn, dateOut, null);
            }
            computerDAO.save(computer);

            return true;

        } catch (DataAccessException e) {
            LOGGER.error("[Error Service] in function insert Computer");
            throw new ServiceException("can't insert computer");
        }

    }
    /**
     * @param computerDTO update a computerDTO
     * @return true if update ok else false
     * @throws ServiceException for errors in computerDTO
     */
    public Boolean updateComputer(ComputerDTO computerDTO) throws ServiceException {
        try {
            Computer computer;
            String name = computerDTO.getNameComp();
            Long id= Long.parseLong(computerDTO.getIdComp());
            String companyId = computerDTO.getIdCompany();
            LocalDateTime dateIn = null;
            LocalDateTime dateOut = null;
            System.err.println(computerDTO.getIdComp());

            if (computerDTO.getDateIn() != null) {
                dateIn = DateTime.convertDate(computerDTO.getDateIn().trim().concat(Time));
            }
            if (computerDTO.getDateOut() != null) {
                dateOut = DateTime.convertDate(computerDTO.getDateOut().trim().concat(Time));
            }

            if (computerDTO.getIdCompany() != "") {
                CompanyDTO companyDTO = companyService.getCompanybyIdLocal(Long.parseLong(companyId));
                Company company = new Company(Long.parseLong(companyId), companyDTO.getNameCompany());
                computer = new Computer(id,name, dateIn, dateOut, company);
            } else {
                computer = new Computer(id,name, dateIn, dateOut, null);
            }
            computerDAO.save(computer);
            return true;

        } catch (DataAccessException e) {
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

    public List<ComputerDTO> getAll() throws ServiceException {
        List<Computer> allComp;
        try {
            allComp = computerDAO.findAll();
            return computerMap.getComputerDTOs(allComp);
        } catch (DataAccessException e) {
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
     */

    public ComputerDTOPage getAllByPage(int start, int end) throws ServiceException {
        PageRequest request =
                new PageRequest(start, end);
        ComputerDTOPage data = new ComputerDTOPage();
        Page allComp;
        try {
            allComp = computerDAO.findAll(request);
            data.setComputersDTO(computerMap.getComputerDTOs(allComp.getContent()));
            data.setNbrPage(allComp.getTotalPages());
            data.setTotalcount(allComp.getTotalElements());
            return data;
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getAllComputerPage");
            throw new ServiceException("can't get all computer by limit");
        }
    }

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
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function showDetailsComputer");
            throw new ServiceException("can't get computer Details");
        }

    }

    /**
     * @param name  of computer
     * @param start page
     * @param end   page
     * @return list of computer DTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */
    @Transactional
    public ComputerDTOPage search(String name, int start, int end) throws ServiceException {
        Page allComp;
        ComputerDTOPage data = new ComputerDTOPage();
        PageRequest request =
                new PageRequest(start, end);
        try {
            allComp = computerDAO.findComputerByNameContainingOrCompanyNameContaining(name, name, request);
            data.setComputersDTO(computerMap.getComputerDTOs(allComp.getContent()));
            data.setNbrPage(allComp.getTotalPages());
            data.setTotalcount(allComp.getTotalElements());
            return data;
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function search");
            throw new ServiceException("can't find list of computer");
        }
    }

    /**
     * @param start    page
     * @param end      page
     * @param column column name
     * @param reqBy    ASC or DESC
     * @param search   name of computer
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */

    public ComputerDTOPage searchOrderBy(int start, int end, String column, String reqBy, String search)
            throws ServiceException {
        ComputerDTOPage data = new ComputerDTOPage();
        Page page;
        column = column.trim();
        search = search.trim();

        if (reqBy.equals("up")) {
            reqBy = "ASC";
        } else {
            reqBy = "DESC";
        }
        PageRequest request = getPageable(start, end, column, reqBy);
        try {
            page = computerDAO.findComputerByNameContainingOrCompanyNameContaining(search, search, request);
            data.setComputersDTO(computerMap.getComputerDTOs(page.getContent()));
            data.setNbrPage(page.getTotalPages());
            data.setTotalcount(page.getTotalElements());
            return data;

        } catch (DataAccessException e) {
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
     */
    public ComputerDTOPage getAllOrderBy(int start, int end, String reqBy, String name) throws ServiceException {
        ComputerDTOPage data = new ComputerDTOPage();
        Page page;
        name = name.trim();
        if (reqBy.equals("up")) {
            reqBy = "ASC";

        } else {
            reqBy = "DESC";
        }
        PageRequest request = getPageable(start, end, name, reqBy);
        try {
            page = computerDAO.findAll(request);
            data.setComputersDTO(computerMap.getComputerDTOs(page.getContent()));
            data.setNbrPage(page.getTotalPages());
            data.setTotalcount(page.getTotalElements());
            return data;

        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerOrder");
            throw new ServiceException("can't get computer orderBy");
        }
    }

    /**
     *
     * @param page current page
     * @param size size of page
     * @param sortBy column of sort
     * @param direction ASC or DESC
     * @return PageRequest
     */
    private PageRequest getPageable(int page, int size, String sortBy, String direction) {
        sortBy = sortBy == null ? "id" : sortBy;
        Sort.Direction directionType = direction == null || !direction.equalsIgnoreCase("desc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new PageRequest(
                page, size, new Sort(directionType, sortBy)
        );
    }


}
