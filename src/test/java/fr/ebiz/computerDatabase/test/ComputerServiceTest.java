package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.Exception.ServiceException;
import fr.ebiz.computerDatabase.service.ComputerService;

public class ComputerServiceTest {
    ComputerService computerService;

   /* @Test
    public void testDeleteCpmouter() {
        computerService = new ComputerService();
        try {
          assertFalse(computerService.deleteCpmouter(0));
        } catch (ServiceException e) {
          System.err.println(e.getMessage());
        }
    }*/

    @Test
    public void testGetAllComputer() {
        computerService =new ComputerService();
        try {
          assertNotNull(computerService.getAllComputer());
        } catch (ServiceException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }

    /*@Test
    public void testGetAllComputerInt() {
        computerService =new ComputerService();
        assertNotNull(computerService.getAllComputer(0,10));
    }*/

    @Test
    public void testGetComputerByNameMapper() {
        computerService =new ComputerService();
        try {
          assertNotNull(computerService.getComputerByName(" "));
        } catch (ServiceException e) {
        System.err.println(e.getMessage());
        }
    }

}
