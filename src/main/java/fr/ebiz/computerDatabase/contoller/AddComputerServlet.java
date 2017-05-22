package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/AddComputerServlet")
public class AddComputerServlet {
    static final String ADDCOMPUTERVIEW = "addComputer";
    static final String ERRORVIEW = "500";
    static final String NAME = "computerName";
    static final String DATEIN = "introduced";
    static final String DATEOUT = "discontinued";
    static final String IDCOMPANY = "company";
    private static final String COMPANYSTR = "company";
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    protected String get(ModelMap model) {
        List<CompanyDTO> company;
        try {
            company = companyService.getAllCompany();
            model.addAttribute(COMPANYSTR, company);
            return ADDCOMPUTERVIEW;
        } catch (ServiceException e) {
            System.err.println(e.getMessage());

        }
        return ERRORVIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@RequestParam(NAME) String name,
                          @RequestParam(DATEIN) String dateIn, @RequestParam(DATEOUT) String dateout,
                          @RequestParam(IDCOMPANY) String company) {
        ComputerDTO cpDto = new ComputerDTO(name, dateIn, dateout, company);
        try {
            computerService.insertComputer(cpDto);
            System.out.println("insertion reussie");
            return "redirect://localhost:8080/DashboardServlet";
        } catch (ServiceException e) {
            return "500";

        }
    }
}