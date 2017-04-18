package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.service.CompanyService;





public class CompanyServiceTest {
    CompanyService company;

    @Test
    public void testGetAllCompany() {
        company= new CompanyService();
        assertNotNull(company.getAllCompany());
    }

    @Test
    public void testGetAllCompanyInt() {
        company= new CompanyService();
        assertNotNull(company.getAllCompany(0));

    }

}
