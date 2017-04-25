package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement;

import fr.ebiz.computerDatabase.Exception.EmptyStringException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.validator.ComputerValidator;
import fr.ebiz.computerDatabase.validator.DateTime;


/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ADDComputer_VIEW = "/WEB-INF/views/addComputer.jsp";
    public static final String error_VIEW = "/WEB-INF/views/500.jsp";
    
    final static String NAME="computerName";
    final static String DATEIN="introduced";
    final static String DATEOUT="discontinued";
    final static String idCompany="company";
    final  static String time=" 00:00:00";
    CompanyService companyService= new CompanyService();
    ComputerService computerService = new ComputerService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        List<CompanyDTO> company =companyService.getAllCompany();
        request.setAttribute("company", company);
        this.getServletContext().getRequestDispatcher(ADDComputer_VIEW).forward(request, response);


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String name = request.getParameter(NAME);
        String dateIn = request.getParameter(DATEIN);
        String dateout = request.getParameter(DATEOUT);
        String company= request.getParameter(idCompany);
        

       ComputerDTO cpDto= new ComputerDTO(name,dateIn,dateout,company);
       try {
           boolean insert= computerService.InsertComputer(cpDto);

           if(insert){
               System.out.println("insertion reussie");
               response.sendRedirect(request.getContextPath()+"/DashboardServlet");
               return ;
               
           }
           else{
               System.err.println("insertion non reussie");
           }
              
        } catch (DateTimeParseException |NullPointerException e) {
           System.err.println(e.getMessage());
        }
       response.sendRedirect(request.getContextPath()+error_VIEW);

    }

}
