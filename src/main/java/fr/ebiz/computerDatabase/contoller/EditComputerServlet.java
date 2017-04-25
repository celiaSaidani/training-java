package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String EDIT_VIEW = "/WEB-INF/views/editComputer.jsp";
    public final ComputerService computerService = new ComputerService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println(request.getParameter("idComputer"));
        ComputerDTO compDTO= computerService.showDetailsComputer(Integer.parseInt(request.getParameter("idComputer")));
        request.setAttribute("computerdb", compDTO);
        this.getServletContext().getRequestDispatcher(EDIT_VIEW).forward(request, response);
        System.out.println(compDTO.getNameCompany());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
