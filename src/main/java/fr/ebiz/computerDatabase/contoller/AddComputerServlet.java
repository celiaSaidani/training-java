package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;
import fr.ebiz.computerDatabase.validator.DateTime;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ADDComputer_VIEW = "/WEB-INF/views/addComputer.jsp";
    final static String NAME="computerName";
    final static String DATEIN="introduced";
    final static String DATEOUT="discontinued";
    final static String idCompany="Company";
    CompanyService companyService= new CompanyService();
    ComputerService computerService = new ComputerService();
    //final

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

        String input[]={name,dateIn,dateout,company};
        LocalDateTime date1=null,date2 = null;

        if(!dateIn.equals("")){
            date1=DateTime.convertDate(dateIn);
            if(date1==null)
                return;
        }
        if(!dateout.equals("")){
            date2=DateTime.convertDate(dateout);
            if(date2==null)
                return;
        }
        try {
            boolean insert= computerService.updateComputer(0, input, false);

            if(insert){
                System.out.println("insertion reussie");
            }
            else
                System.err.println("insertion non reussie");

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }


    }

}
