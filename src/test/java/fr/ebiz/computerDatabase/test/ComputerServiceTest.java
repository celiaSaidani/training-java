package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class ComputerServiceTest {
  @Autowired
  ComputerService computerService;
  /**
   * @Test
   */
  @Test
  public void testGetAllComputer() {

    try {
      assertNotNull(computerService.getAllComputer());
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
 /**
  *  @Test

  @Test
  public void testGetComputerByNameMapper() {
    try {
      assertNotNull(computerService.getComputerByName(" "));
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }
  }
*/
}
