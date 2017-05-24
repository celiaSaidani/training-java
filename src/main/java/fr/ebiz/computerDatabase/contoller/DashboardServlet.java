package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/DashboardServlet")
public class DashboardServlet {
    private final String SELECTION = "selection";
    private final String ORDER = "order";
    private final String BY = "by";
    private final String SORT = "sort";
    private final String SIZE = "size";
    private final String PAGE = "page";
    private final String SEARCH = "search";
    private final String COUNT = "count";
    private final String COMPUTER = "computerdb";
    private static final String DASHBOARD_VIEW = "dashboard";
    static final String ERRORVIEW = "500";

    @Autowired
    private ComputerService computerService;
    @Autowired
    private PageRequest pageRequest;


    @RequestMapping(method = RequestMethod.GET)
    protected String get(@RequestParam(value = ORDER, defaultValue = "computer.name") String reqOrder,
                         @RequestParam(value = BY, required = false) String reqBy,
                         @RequestParam(value = SORT, defaultValue = "false") boolean reqSort,
                         @RequestParam(value = SIZE, defaultValue = "10") int size,
                         @RequestParam(value = PAGE, defaultValue = "1") int page,
                         @RequestParam(value = SEARCH, required = false) String search,
                         ModelMap model) throws ServiceException {
        int count = 0;
        List<ComputerDTO> computer = null;
        ComputerDTOPage pageReqst = null;

        pageReqst = pageRequest.getPage(reqOrder, reqBy, size, page, search);
        count = pageReqst.getCount();
        computer = pageReqst.getComputersDTO();

        model.addAttribute(COMPUTER, computer);
        model.addAttribute(SIZE, size);
        model.addAttribute(PAGE, page);
        model.addAttribute(COUNT, String.valueOf(count));
        model.addAttribute(SEARCH, search);
        model.addAttribute(ORDER, reqOrder);
        model.addAttribute(SORT, reqSort);
        model.addAttribute(BY, reqBy);
        return DASHBOARD_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@RequestParam(SELECTION) String[] selected) throws ServiceException {
        int i = 0;
        while (i < selected.length) {
            computerService.deleteComputer(Integer.parseInt(selected[i]));
            i++;
        }
        return "redirect://localhost:8080/DashboardServlet";
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


    /**
     *
     * @param ex NumberFormatException
     * @return 500
     */
    @ExceptionHandler(NumberFormatException.class)
    public String numberFormatException(ServiceException ex) {
        System.err.println(ex.getMessage());
        return "500";

    }


}