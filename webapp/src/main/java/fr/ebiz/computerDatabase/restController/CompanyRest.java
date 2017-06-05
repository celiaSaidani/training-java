package fr.ebiz.computerDatabase.restController;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by celia on 05/06/17.
 */
@RestController
public class CompanyRest {
    @Autowired
    CompanyService companyService;


    @RequestMapping(method = RequestMethod.GET, value = "/api/companies/{page}/{size}")
    public List<CompanyDTO> getCompanies(@PathVariable int size, @PathVariable int page) throws ServiceException {
        List<CompanyDTO> companies = new ArrayList<>();
        try {
            companies = companyService.getAllCompanyPage(page, size);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return companies;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/company/{id}")
    public CompanyDTO findCompany(@PathVariable Long id) {
        CompanyDTO company = null;
        try {
            company = companyService.getCompanybyId(id);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
        return company;
    }
}
