package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTOPage;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class DashboardServlet.
 */

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ComputerService computerService;
    public static final String DASHBOARD_VIEW = "/WEB-INF/views/dashboard.jsp";
    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        int size = 10;
        int page = 1;
        String search = "";
        int count = 0;
        String order = "computer.name", by = "down";
        String reqOrder, reqBy, reqSort;
        List<ComputerDTO> computer = null;
        boolean sort = false;

        if ((reqOrder = request.getParameter("order")) != null) {
            order = reqOrder;
        }

        if ((reqBy = request.getParameter("by")) != null) {
            by = reqBy;

        }
        if ((reqSort = request.getParameter("sort")) != null) {
            try {
                sort = Boolean.parseBoolean(reqSort);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

        if (request.getParameter("size") != null) {
            size = Integer.parseInt((request.getParameter("size")));
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt((request.getParameter("page")));
        }
        if (request.getParameter("search") != null) {

            try {

                count = computerService.getCount(request.getParameter("search").trim());
                if (count > 0) {
                    try {
                        if (reqOrder != null & reqBy != null) {
                            computer = computerService.searchOrderBy((page - 1) * size, size, reqOrder, reqBy,
                                    request.getParameter("search").trim());

                        } else {
                            computer = computerService.search(request.getParameter("search").trim(),
                                    (page - 1) * size, size);
                        }

                    } catch (ServiceException e) {
                        System.err.println(e.getMessage());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                    search = request.getParameter("search");
                }
            } catch (ServiceException e) {
                System.err.println(e.getMessage());
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
                System.err.println(e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            }

        }
        request.setAttribute("computerdb", computer);
        request.setAttribute("computer", count);
        request.setAttribute("size", size);
        request.setAttribute("page", page);
        request.setAttribute("count", count);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("sort", sort);
        request.setAttribute("by", by);

        this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }

    /**
     * {@inheritDoc}
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        int i = 0;

        if (request.getParameter("selection") != null) {
            String selected = request.getParameter("selection");
            String[] ids = selected.split(",");
            if (ids.length != 0) {
                while (i < ids.length) {

                    try {
                        computerService.deleteComputer(Integer.parseInt(ids[i]));
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    } catch (ServiceException e) {
                        System.err.println(e.getMessage());
                    }

                    i++;
                }
            }
        }
        doGet(request, response);
    }

}