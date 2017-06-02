package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ComputerRest {
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/computers")
    public List<ComputerDTO> getComputers(@PathVariable int size, @PathVariable int page) throws ServiceException {
        try {
            List<ComputerDTO> all = computerService.getAllByPage(page, size).getComputersDTO();
            return all;
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }

    return null;
    }
}