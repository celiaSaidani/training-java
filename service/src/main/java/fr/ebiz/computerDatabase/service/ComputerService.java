package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.DTOPage;
import fr.ebiz.computerDatabase.exception.NotFoundException;
import fr.ebiz.computerDatabase.exception.UpdateException;
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
     */
    public boolean insertComputer(ComputerDTO computerDTO) {
        Computer computer;
        try {
            computer = computerMap.getComputer(computerDTO);
            computerDAO.save(computer);
            return true;

        } catch (DataAccessException e) {
            LOGGER.error("[Error Service] in function insert Computer");
            throw new UpdateException("can't insert computer");
        }

    }

    /**
     * @param computerDTO update a computerDTO
     * @return true if update ok else false
     */
    public void updateComputer(ComputerDTO computerDTO) {
        Computer computer;
        try {
            computer = computerMap.getComputer(computerDTO);
            computerDAO.save(computer);
        } catch (DataAccessException e) {
            LOGGER.error("[Error Service] in function update Computer");
            throw new UpdateException("can't update computer");
        }
    }

    /**
     * @param id of computer to delete
     * @return true if delete correct, false else
     */
    public boolean deleteComputer(Long id)  {

        try {
            computerDAO.delete(id);
            return true;
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function delete Computer");
            throw new NotFoundException("can't delete computer");

        }
    }

    /**
     * @return list of computerDTO
     */

    public List<ComputerDTO> getAll()  {
        List<Computer> computers;
        try {
            computers = computerDAO.findAll();
            return computerMap.getComputerDTOs(computers);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getAllComputer");
            throw new NotFoundException("can't get all computer");
        }
    }

    /**
     * @param start page
     * @param end   page
     * @return list of computer page
     */

    public DTOPage getAllByPage(int start, int end)  {
        PageRequest request =
                new PageRequest(start, end);
        Page page;
        try {
            page = computerDAO.findAll(request);
            return getPage(page);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getAllComputerPage");
            throw new NotFoundException("can't get all computer by limit");
        }
    }

    /**
     * @param id of computer
     * @return a computerDTO
     */

    public ComputerDTO showDetailsComputer(Long id)  {
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
            throw new NotFoundException("can't get computer Details");
        }

    }

    /**
     * @param name  of computer
     * @param start page
     * @param end   page
     * @return list of computer DTO
     */
    public DTOPage search(String name, int start, int end) {
        Page page;
        PageRequest request =
                new PageRequest(start, end);
        try {
            page = computerDAO.findComputerByNameContainingOrCompanyNameContaining(name.trim(), name.trim(), request);
            return getPage(page);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function search");
            throw new NotFoundException("can't find list of computer");
        }
    }

    /**
     * @param name of computer
     * @return list of computer DTO
     */
    public List<ComputerDTO> search(String name) {
        List<ComputerDTO> computers;
        try {
            computers = computerDAO.findComputerByNameContainingOrCompanyNameContaining(name.trim(), name.trim());
            return computers;
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function search");
            throw new NotFoundException("can't find list of computer");
        }
    }

    /**
     * @param start  page
     * @param end    page
     * @param column column name
     * @param reqBy  ASC or DESC
     * @param search name of computer
     * @return list of computerDTO
     */
    public DTOPage searchOrderBy(int start, int end, String column, String reqBy, String search){
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
            throw new NotFoundException("can't get computer orderBy");
        }

    }

    /**
     * @param start page
     * @param end   page
     * @param reqBy ASC or DESC
     * @param name  column name
     * @return list of computerDTO
     */
    public DTOPage getAllOrderBy(int start, int end, String reqBy, String name) {

        name = name.trim();
        PageRequest request = getPageable(start, end, name, reqBy);
        Page page;

        try {
            page = computerDAO.findAll(request);
            return getPage(page);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            LOGGER.error("[Error Service] in function getComputerOrder");
            throw new NotFoundException("can't get computer orderBy");
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

    /**
     *
     * @param page object return by pageRequest
     * @return DTOcomputer page
     */
    private DTOPage getPage(Page page) {
        DTOPage data = new DTOPage();
        data.setComputersDTO(computerMap.getComputerDTOs(page.getContent()));
        data.setNbrPage(page.getTotalPages());
        data.setTotalcount(page.getTotalElements());
        return data;
    }

}
