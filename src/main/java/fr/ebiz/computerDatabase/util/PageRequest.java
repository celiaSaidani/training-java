package fr.ebiz.computerDatabase.util;

import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ebiz on 24/05/17.
 */
@Service
public class PageRequest {
    @Autowired
    private ComputerService computerService;

    /**
     * @param reqOrder asc or desc
     * @param reqBy    column name
     * @param size     size page
     * @param page     number page
     * @param search   search word
     * @return page
     * @throws ServiceException if bad resqt to Service
     */
    public ComputerDTOPage getPage(String reqOrder, String reqBy, int size, int page, String search) throws ServiceException {
        ComputerDTOPage data;

        if (search != null) {
            if (reqOrder != null & reqBy != null) {
               // data = computerService.searchOrderBy((page - 1) * size, size, reqOrder, reqBy, search.trim());

            } else {
                //data = computerService.search(search.trim(), (page - 1) * size, size);
            }

        } else {
            if ((reqOrder != null) && (reqBy != null)) {
                //data = computerService.getComputerOrder((page - 1) * size, size, reqBy, reqOrder);
            } else {
               // data = computerService.getAllComputerPage((page - 1) * size, size);
            }
        }
        return null;
    }
}