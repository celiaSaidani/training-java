package fr.ebiz.computerDatabase.contoller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class DeleteComputerServlet.
 */
@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputerServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * {@inheritDoc}
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    // response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  /**
   * {@inheritDoc}
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String[] selected = request.getParameterValues("idComputer");
    System.out.println(selected.length);
  }

}
