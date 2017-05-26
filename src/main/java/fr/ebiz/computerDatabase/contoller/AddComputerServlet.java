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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
@RequestMapping("/AddComputerServlet")
public class AddComputerServlet {
    private static final String ADDCOMPUTERVIEW = "addComputer";
    private static final String ERRORVIEW = "500";
    private static final String COMPANY = "company";
    private static final String DASHBOARD_VIEW = "redirect://localhost:8080/DashboardServlet";
    @Autowired
    private Validator computerValidator;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    protected String get(ModelMap model) throws ServiceException {
        List<CompanyDTO> company;

        company = companyService.getAllCompany();
        model.addAttribute(COMPANY, company);
        return ADDCOMPUTERVIEW;


    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@Validated ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) throws ServiceException {

        if (bindingResult.hasErrors()) {

            System.err.println(bindingResult.toString());
            return get(model);
        } else {

            System.err.println(">>>>> IN Controller"+ computerService.insertComputer(computerDTO));
        }
        return DASHBOARD_VIEW;

    }

    /**
     *
     * @param binder to bind validator
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

    /**
     *
     * @param ex serviceException
     * @return 500
     */
    @ExceptionHandler(ServiceException.class)
    public String handleCustomException(ServiceException ex) {
        System.err.println(ex.getMessage());
        return ERRORVIEW;

    }
}