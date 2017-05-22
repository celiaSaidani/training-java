package fr.ebiz.computerDatabase.contoller;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public static final String DASHBOARD_VIEW = "dashboard";

    @Autowired
    private ComputerService computerService;


    @RequestMapping(method = RequestMethod.GET)
    protected String get(@RequestParam(value = ORDER, defaultValue = "computer.name") String reqOrder,
                         @RequestParam(value = BY, required = false) String reqBy,
                         @RequestParam(value = SORT, defaultValue = "false") boolean reqSort,
                         @RequestParam(value = SIZE, defaultValue = "10") int size,
                         @RequestParam(value = PAGE, defaultValue = "1") int page,
                         @RequestParam(value = SEARCH, required = false) String search,
                         ModelMap model) {
        int count = 0;
        List<ComputerDTO> computer = null;

        if (search != null) {
            ComputerDTOPage dataSearch;

            try {
                if (reqOrder != null & reqBy != null) {
                    dataSearch = computerService.searchOrderBy((page - 1) * size, size, reqOrder, reqBy, search.trim());
                    count = dataSearch.getCount();
                    computer = dataSearch.getComputersDTO();
                } else {
                    dataSearch = computerService.search(search.trim(), (page - 1) * size, size);
                    computer = dataSearch.getComputersDTO();
                    count = dataSearch.getCount();
                }
            } catch (ServiceException e) {
                return "500";
            }

        } else {
            try {
                ComputerDTOPage data = computerService.getAllComputerPage((page - 1) * size, size);
                count = data.getCount();
                if (count > 0) {
                    if ((reqOrder != null) && (reqBy != null)) {
                        computer = computerService.getComputerOrder((page - 1) * size, size, reqBy, reqOrder);
                    } else {
                        computer = data.getComputersDTO();
                    }
                }
            } catch (ServiceException e) {
                return "500";
            }

        }
        model.addAttribute(COMPUTER, computer);
        model.addAttribute(SIZE, size);
        model.addAttribute(PAGE, page);
        model.addAttribute(COUNT, count);
        model.addAttribute(SEARCH, search);
        model.addAttribute(ORDER, reqOrder);
        model.addAttribute(SORT, reqSort);
        model.addAttribute(BY, reqBy);
        return DASHBOARD_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@RequestParam(SELECTION) String[] selected) {
        int i = 0;
        while (i < selected.length) {
            try {
                computerService.deleteComputer(Integer.parseInt(selected[i]));
            } catch (NumberFormatException e) {
                return "500";
            } catch (ServiceException e) {
                return "500";
            }
            i++;
        }
        return "redirect://localhost:8080/DashboardServlet";
    }

}