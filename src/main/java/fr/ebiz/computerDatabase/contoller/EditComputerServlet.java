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
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String EDIT_VIEW = "/WEB-INF/views/editComputer.jsp";
  public static final String error_VIEW = "/WEB-INF/views/500.jsp";

  public final ComputerService computerService = new ComputerService();
  CompanyService companyService = new CompanyService();
  public final String ID = "computerId";
  public final String NAME = "computerName";
  public final String DATEIN = "introduced";
  public final String DATEOUT = "discontinued";
  public final String IDCOMPANY = "company";

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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    ComputerDTO compDTO;
    try {
      compDTO = computerService
          .showDetailsComputer(Integer.parseInt(request.getParameter("idComputer")));
      request.setAttribute("computerdb", compDTO);
      List<CompanyDTO> company = companyService.getAllCompany();
      request.setAttribute("company", company);
      this.getServletContext().getRequestDispatcher(EDIT_VIEW).forward(request, response);
    } catch (NumberFormatException | ServiceException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String id = request.getParameter(ID);
    String name = request.getParameter(NAME);
    String dateIn = request.getParameter(DATEIN);
    String dateout = request.getParameter(DATEOUT);
    String company = request.getParameter(IDCOMPANY);

    ComputerDTO cpDto = new ComputerDTO(id, name, dateIn, dateout, company);
    try {
      boolean update = computerService.updateComputer(cpDto);

      if (update) {
        System.out.println("modification reussie");
        response.sendRedirect(request.getContextPath() + "/DashboardServlet");
        return;
      } else {
        System.err.println("modification non reussie");
      }

    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }
    response.sendRedirect(request.getContextPath() + error_VIEW);

  }

}
