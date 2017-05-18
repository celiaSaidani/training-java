package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;

public class ComputerServiceTest {
  ComputerService computerService;
  /**
   * @Test
   */
  @Test
  public void testGetAllComputer() {
    computerService = new ComputerService();
    try {
      assertNotNull(computerService.getAllComputer());
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
 /**
  *  @Test
  */
  @Test
  public void testGetComputerByNameMapper() {
    computerService = new ComputerService();
    try {
      assertNotNull(computerService.getComputerByName(" "));
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }
  }

}
