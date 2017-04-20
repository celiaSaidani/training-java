package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ebiz.computerDatabase.service.ComputerService;

public class ComputerServiceTest {
    ComputerService computerService;
    String no = "l'identifiant de la companie n'existe pas";
    // @Test
    /*public void testUpdateComputer() {
        computerService = new ComputerService();
        String input[]= new String[5];
        assertEquals(no,computerService.updateComputer(0,input, true));
    }*/

    @Test
    public void testDeleteCpmouter() {
        computerService = new ComputerService();
        assertFalse(computerService.deleteCpmouter(0));
    }

    @Test
    public void testGetAllComputer() {
        computerService =new ComputerService();
        assertNotNull(computerService.getAllComputer());
    }

    @Test
    public void testGetAllComputerInt() {
        computerService =new ComputerService();
        assertNotNull(computerService.getAllComputer(0));
    }

    @Test
    public void testGetComputerByNameMapper() {
        computerService =new ComputerService();
        assertNotNull(computerService.getComputerByNameMapper(" "));
    }

}
