package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.service.CompanyService;





public class CompanyServiceTest {
    CompanyService company;

    @Test
    public void testGetAllCompany() {
        company= new CompanyService();
        try {
          assertNotNull(company.getAllCompany());
        } catch (ServiceException e) {
          System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetAllCompanyInt() {
        company= new CompanyService();
        try {
          assertNotNull(company.getAllCompanyPage(0));
        } catch (ServiceException e) {
         System.err.println(e.getMessage());
        }

    }

}
