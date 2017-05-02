package fr.ebiz.computerDatabase.dto;

import java.util.List;

public class ComputerDTOPage {
    
    private int count;
    private List<ComputerDTO> computersDTO;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    /**
     * @return the computersDTO
     */
    public List<ComputerDTO> getComputersDTO() {
        return computersDTO;
    }
    /**
     * @param computersDTO the computersDTO to set
     */
    public void setComputersDTO(List<ComputerDTO> computersDTO) {
        this.computersDTO = computersDTO;
    }
    

    
}
