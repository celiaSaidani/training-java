package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
@RequestMapping("/AddComputerServlet")
public class AddComputerServlet {
    static final String ADDCOMPUTERVIEW = "addComputer";
    static final String ERRORVIEW = "500";
    static final String NAME = "nameComp";
    static final String DATEIN = "dateIn";
    static final String DATEOUT = "dateOut";
    static final String COMPANY = "company";
    @Autowired
    private Validator computerValidator;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    protected String get(ModelMap model) {
        List<CompanyDTO> company;
        try {
            company = companyService.getAllCompany();
            model.addAttribute(COMPANY, company);
            return ADDCOMPUTERVIEW;
        } catch (ServiceException e) {
            System.err.println(e.getMessage());

        }
        return ERRORVIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@Validated ComputerDTO computerDTO, BindingResult bindingResult) {
        System.err.println(">>"+computerDTO.getIdCompany());
        System.err.println(">>"+computerDTO.getNameCompany());
        try {
            if (bindingResult.hasErrors()) {
                System.err.println(  bindingResult.toString());
                return "500";
            } else {
                computerService.insertComputer(computerDTO);
                System.out.println("insertion reussie");
            }
            return "redirect://localhost:8080/DashboardServlet";
        } catch (ServiceException e) {
            return "500";

        }
    }
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

}