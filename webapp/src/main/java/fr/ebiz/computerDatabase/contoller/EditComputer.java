package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.exception.UpdateException;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/editcomputer")
public class EditComputer {
    private static final String ID = "idComp";
    private static final String COMPUTERDB = "computerdb";
    private static final String COMPANY = "company";
    private static final String EDIT_VIEW = "editComputer";
    private static final String ERROR_VIEW = "500";
    private static final String BAD_VIEW = "400";
    private static final String DASHBOARD_VIEW = "redirect://localhost:8080/dashboard";
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    protected String get(@RequestParam(ID) String idComputer, ModelMap model) {
        ComputerDTO compDTO;

        compDTO = computerService.showDetailsComputer(Long.parseLong(idComputer));
        model.addAttribute(COMPUTERDB, compDTO);
        List<CompanyDTO> company = companyService.getAllCompany();
        model.addAttribute(COMPANY, company);
        return EDIT_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@Validated ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.toString());
            return ERROR_VIEW;
        } else {
            computerService.updateComputer(computerDTO);
            return DASHBOARD_VIEW;
        }
    }


    /**
     * @param ex UpdateException
     * @return 500
     */

    @ExceptionHandler(UpdateException.class)
    public String handleCustomException(UpdateException ex) {
        System.err.println(ex.getMessage());
        return BAD_VIEW;

    }

    /**
     * @param ex NumberFormatException
     * @return 500
     */

    @ExceptionHandler(NumberFormatException.class)
    public String numberFormatException(NumberFormatException ex) {
        System.err.println(ex.getMessage());
        return ERROR_VIEW;

    }

}