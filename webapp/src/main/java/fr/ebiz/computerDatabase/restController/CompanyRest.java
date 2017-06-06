package fr.ebiz.computerDatabase.restController;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.exception.NotFoundException;
import fr.ebiz.computerDatabase.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by celia on 05/06/17.
 */
@RestController
public class CompanyRest {
    @Autowired
    CompanyService companyService;


    @RequestMapping(method = RequestMethod.GET, value = "/api/companies/{page}/{size}")
    public List<CompanyDTO> getCompanies(@PathVariable int size, @PathVariable int page) {

        return companyService.getAllCompanyPage(page, size);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/companies/{id}")
    public CompanyDTO findCompany(@PathVariable Long id) {
        return companyService.getCompanybyId(id);
    }

    @ExceptionHandler(NotFoundException.class)
    public void handleCustomException(NotFoundException ex) {
        System.err.println(ex.getMessage());
    }
}