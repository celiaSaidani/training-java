package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.DTOPage;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.mapper.ComputerMapper;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.persistence.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ComputerService {
    @Autowired
    private ComputerMapper computerMap;
    @Autowired
    private ComputerDAO computerDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);


    /**
     * @param computerDTO insert a computerDTO
     * @return true if insertion ok else false
     * @throws ServiceException for errors in computerDTO
     */
    public boolean insertComputer(ComputerDTO computerDTO) throws ServiceException {
        Computer computer;
        try {
            computer = computerMap.getComputer(computerDTO);
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
        Computer computer;
        try {
            computer = computerMap.getComputer(computerDTO);
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
        } catch (DataAccessException e) {
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
        List<Computer> all;
        try {
            all= computerDAO.findAll();
            return computerMap.getComputerDTOs(all);
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

    public DTOPage getAllByPage(int start, int end) throws ServiceException {
        PageRequest request =
                new PageRequest(start, end);
        Page page;
        try {
            page = computerDAO.findAll(request);
            return getPage(page);
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
            cp = computerDAO.findOne(id);
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
    public DTOPage search(String name, int start, int end) throws ServiceException {
        Page page;
        PageRequest request =
                new PageRequest(start, end);
        try {
            page = computerDAO.findComputerByNameContainingOrCompanyNameContaining(name, name, request);
            return getPage(page);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function search");
            throw new ServiceException("can't find list of computer");
        }
    }

    /**
     * @param start  page
     * @param end    page
     * @param column column name
     * @param reqBy  ASC or DESC
     * @param search name of computer
     * @return list of computerDTO
     * @throws ServiceException when we catch DAOException from computerDAO
     */
    public DTOPage searchOrderBy(int start, int end, String column, String reqBy, String search)
            throws ServiceException {
        Page page;
        column = column.trim();
        search = search.trim();
        PageRequest request = getPageable(start, end, column, reqBy);
        try {
            page = computerDAO.findComputerByNameContainingOrCompanyNameContaining(search, search, request);
            return getPage(page);

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
    public DTOPage getAllOrderBy(int start, int end, String reqBy, String name) throws ServiceException {

        name = name.trim();
        PageRequest request = getPageable(start, end, name, reqBy);
        Page page;

        try {
            page = computerDAO.findAll(request);
            return getPage(page);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerOrder");
            throw new ServiceException("can't get computer orderBy");
        }
    }

    /**
     * @param page      current page
     * @param size      size of page
     * @param sortBy    column of sort
     * @param direction ASC or DESC
     * @return PageRequest
     */
    private PageRequest getPageable(int page, int size, String sortBy, String direction) {
        if (direction.equals("up")) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sortBy = sortBy == null ? "id" : sortBy;
        Sort.Direction directionType = direction == null || direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new PageRequest(page, size, new Sort(directionType, sortBy));
    }

    private DTOPage getPage(Page page) {
        DTOPage data = new DTOPage();
        data.setComputersDTO(computerMap.getComputerDTOs(page.getContent()));
        data.setNbrPage(page.getTotalPages());
        data.setTotalcount(page.getTotalElements());
        return data;
    }

}
