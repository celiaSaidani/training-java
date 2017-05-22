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
@RequestMapping("/EditComputerServlet")
public class EditComputerServlet {
    public final String ID = "computerId";
    public final String NAME = "computerName";
    public final String DATEIN = "introduced";
    public final String DATEOUT = "discontinued";
    private final String COMPUTERDB = "computerdb";
    private final String COMPANY = "company";
    public static final String EDIT_VIEW = "editComputer";
    public static final String ERROR_VIEW = "500.jsp";
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    protected String get(@RequestParam("idComputer") String idComputer, ModelMap model) {
        ComputerDTO compDTO;
        try {
            compDTO = computerService.showDetailsComputer(Integer.parseInt(idComputer));
            model.addAttribute(COMPUTERDB, compDTO);
            List<CompanyDTO> company = companyService.getAllCompany();
            model.addAttribute(COMPANY, company);
            return EDIT_VIEW;

        } catch (NumberFormatException | ServiceException e) {
            System.err.println(e.getMessage());
        }
        return ERROR_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(
            @RequestParam(ID) String id, @RequestParam(NAME) String name,
            @RequestParam(DATEIN) String dateIn, @RequestParam(DATEOUT) String dateout,
            @RequestParam(COMPANY) String company) {

        ComputerDTO cpDto = new ComputerDTO(id, name, dateIn, dateout, company);
        try {
            boolean update = computerService.updateComputer(cpDto);

            if (update) {
                System.out.println("modification reussie");
                return "redirect://localhost:8080/DashboardServlet";
            } else {
                System.err.println("modification non reussie");
            }
        } catch (ServiceException e) {
            return "500";
        }
return  "500";
    }

}