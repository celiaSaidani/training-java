package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.time.format.DateTimeParseException;

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
    public static final String error_VIEW = "/WEB-INF/views/500.jsp";

    public final ComputerService computerService = new ComputerService();
    public final String ID="computerId";
    public final String NAME="computerName";
    public final String DATEIN="computerId";
    public final String DATEOUT="computerId";
    public final String IDCOMPANY="computerId";
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
    	 String id = request.getParameter(ID);
    	 String name = request.getParameter(ID);
         String dateIn = request.getParameter(DATEIN);
         String dateout = request.getParameter(DATEOUT);
         String company= request.getParameter(IDCOMPANY);
         

        ComputerDTO cpDto= new ComputerDTO(id,name,dateIn,dateout,company);
        try {
            boolean update= computerService.updateComputer(cpDto);

            if(update){
                System.out.println("modification reussie");
                response.sendRedirect(request.getContextPath()+"/DashboardServlet");
                return ;        
            }
            else{
                System.err.println("modification non reussie");
            }
               
         } catch (DateTimeParseException |NullPointerException e) {
            System.err.println(e.getMessage());
         }
        //response.sendRedirect(request.getContextPath()+error_VIEW);

     }

    

}
