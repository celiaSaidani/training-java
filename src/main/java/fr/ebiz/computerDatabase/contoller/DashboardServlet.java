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
    private ComputerService computerService= new ComputerService();

    public static final String DASHBOARD_VIEW = "/WEB-INF/views/dashboard.jsp";
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        List<ComputerDTO> computer =computerService.getAllComputer();
        String nbrComputer=Integer.toString(computer.size());
        request.setAttribute("computerdb", computer);
        request.setAttribute("computer",nbrComputer );
        this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }
}