package fr.ebiz.computerDatabase.restController;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.DTOPage;
import fr.ebiz.computerDatabase.exception.NotFoundException;
import fr.ebiz.computerDatabase.exception.UpdateException;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.util.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/computers")
@CrossOrigin
public class ComputerRest {
    @Autowired
    private ComputerService computerService;
    @Autowired
    private PageRequest pageRequest;

    private static final String ORDER = "order";
    private static final String BY = "by";
    private static final String SORT = "sort";
    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String SEARCH = "search";

    @RequestMapping(method = RequestMethod.GET)
    public DTOPage getComputers(@RequestParam(value = ORDER, defaultValue = "computer.name") String reqOrder,
                                @RequestParam(value = BY, required = false) String reqBy,
                                @RequestParam(value = SORT, defaultValue = "false") boolean reqSort,
                                @RequestParam(value = SIZE, defaultValue = "10") int size,
                                @RequestParam(value = PAGE, defaultValue = "1") int page,
                                @RequestParam(value = SEARCH, required = false) String search) {

        return pageRequest.getPage(reqOrder, reqBy, size, page, search);

    }

    /**
     * @param id of computer to find
     * @return a computerDTO
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ComputerDTO findComputer(@PathVariable Long id) {
        return computerService.showDetailsComputer(id);
    }

    /**
     * @param computerDTO to add
     * @return a response entity 200 for ok!
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody @Validated ComputerDTO computerDTO) {
        computerService.insertComputer(computerDTO);
        return new ResponseEntity(computerDTO, HttpStatus.CREATED);

    }

    /**
     * @param computerDTO to update
     * @return a response entity 200 for ok!
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody @Validated ComputerDTO computerDTO) {
        computerService.updateComputer(computerDTO);
        return new ResponseEntity(computerDTO, HttpStatus.CREATED);
    }

    /**
     * @param id of computer to delete
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable Long id) {
        computerService.deleteComputer(id);
    }

    /**
     * @param ex for an updateException
     */
    @ExceptionHandler(UpdateException.class)
    public void handleCustomException(UpdateException ex) {
        System.err.println(ex.getMessage());
    }

    /**
     * @param ex for an notFoundException
     */
    @ExceptionHandler(NotFoundException.class)
    public void handleCustomException(NotFoundException ex) {
        System.err.println(ex.getMessage());
    }

}
