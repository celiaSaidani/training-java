package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComputerService computerService = new ComputerService();

    public static final String DASHBOARD_VIEW = "/WEB-INF/views/dashboard.jsp";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        int size = 10;
        int page = 1;
        String search = "";
        int count = 0;
        String order = "name", by = "up";
        String reqOrder, reqBy;
        List<ComputerDTO> computer = null;

        if ((reqOrder = request.getParameter("order")) != null) {
            order = reqOrder;
        }

        if ((reqBy = request.getParameter("by")) != null) {
          System.err.println(reqBy);
            if (reqBy.equalsIgnoreCase("up")) {
                by = "down";
            } else if (reqBy.equalsIgnoreCase("down")) {
                by = "up";
            }

        }

        if (request.getParameter("size") != null) {
            size = Integer.parseInt((request.getParameter("size")));
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt((request.getParameter("page")));
        }
        if (request.getParameter("search") != null) {
            count = computerService.getCount(request.getParameter("search").trim());

            if (count > 0) {
                computer = computerService.Search(request.getParameter("search").trim(), (page - 1) * size, size);
                search = request.getParameter("search");
            }
        } else {
            count = computerService.getCount();
            if (count > 0) {
                if ((reqOrder != null) && (reqBy != null)) {
                    computer = computerService.getComputerOrder((page - 1) * size, size, reqBy, reqOrder);
                } else
                    computer = computerService.getAllComputerPage((page - 1) * size, size);
            }
        }
        request.setAttribute("computerdb", computer);
        request.setAttribute("computer", count);
        request.setAttribute("size", size);
        request.setAttribute("page", page);
        request.setAttribute("count", count);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("by", by);

        this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        int i = 0;

        if (request.getParameter("selection") != null) {
            String selected = request.getParameter("selection");
            String ids[] = selected.split(",");
            if (ids.length != 0) {
                while (i < ids.length) {

                    boolean delete = computerService.deleteCpmouter(Integer.parseInt(ids[i]));
                    if (delete) {
                        System.out.println("Delete reussie");
                    } else {
                        System.out.println("Delete non reussie");
                    }
                    i++;
                }
            }
        }

        doGet(request, response);
    }

}