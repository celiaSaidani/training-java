package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.dto.CompanyDTO;
import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.service.CompanyService;
import fr.ebiz.computerDatabase.service.ComputerService;

/**
 * . Servlet implementation class AddComputerServlet.
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String ADDCOMPUTERVIEW = "/WEB-INF/views/addComputer.jsp";
  public static final String ERRORVIEW = "/WEB-INF/views/500.jsp";

  static final String NAME = "computerName";
  static final String DATEIN = "introduced";
  static final String DATEOUT = "discontinued";
  static final String IDCOMPANY = "company";
  static final String TIME = " 00:00:00";
  private static final String COMPANYSTR = "company";

  CompanyService companyService = new CompanyService();
  ComputerService computerService = new ComputerService();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddComputerServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    List<CompanyDTO> company;
    try {
      company = companyService.getAllCompany();
      request.setAttribute(COMPANYSTR, company);
      this.getServletContext().getRequestDispatcher(ADDCOMPUTERVIEW).forward(request, response);
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String name = request.getParameter(NAME);
    String dateIn = request.getParameter(DATEIN);
    String dateout = request.getParameter(DATEOUT);
    String company = request.getParameter(IDCOMPANY);

    ComputerDTO cpDto = new ComputerDTO(name, dateIn, dateout, company);
    try {
      computerService.insertComputer(cpDto);
      System.out.println("insertion reussie");
      response.sendRedirect(request.getContextPath() + "/DashboardServlet");
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
      response.sendRedirect(request.getContextPath() + ERRORVIEW);
    }

  }
}
