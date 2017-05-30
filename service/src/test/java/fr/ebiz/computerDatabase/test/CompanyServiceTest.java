package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.exception.ServiceException;
import fr.ebiz.computerDatabase.service.CompanyService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class CompanyServiceTest {
  @Autowired
  CompanyService company;

  /**
   * @Test GetAllcompany
   */
  @Test
  public void testGetAllCompany() {

    try {
      assertNotNull(company.getAllCompany());
    } catch (ServiceException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @Test testGetAllCompanyInt

  @Test
  public void testGetAllCompanyInt() {
    try {
      assertNotNull(company.getAllCompanyPage(0));
    } catch (ServiceException e) {
      System.err.println(e.getMessage());
    }

  }*/

}
