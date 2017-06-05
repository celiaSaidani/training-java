package fr.ebiz.computerDatabase.restController;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ComputerRest {
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/computers/{page}/{size}")
    public List<ComputerDTO> getComputers(@PathVariable int page, @PathVariable int size) {
        List<ComputerDTO> computers = new ArrayList<>();
        try {
            computers = computerService.getAllByPage(page, size).getComputersDTO();
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return computers;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/computer/{id}")
    public ComputerDTO findComputer(@PathVariable Long id) {
        ComputerDTO computers = null;
        try {
            computers = computerService.showDetailsComputer(id);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return computers;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/search/{name}")
    public List<ComputerDTO> search(@PathVariable String name) {
        List<ComputerDTO> computers = new ArrayList<>();
        try {
            computers = computerService.search(name);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return computers;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/computers/insert")
    public boolean add(@Validated ComputerDTO computerDTO) {
        try {
            return computerService.insertComputer(computerDTO);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/computer/update/")
    public boolean update(@Validated ComputerDTO computerDTO) {
        try {
            return computerService.updateComputer(computerDTO);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/api/computer/delete/{id}")
    public void delete(@PathVariable Long id) {
        try {
            computerService.deleteComputer(id);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

}
