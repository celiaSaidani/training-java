package fr.ebiz.computerDatabase.mapper;


import java.util.ArrayList;
import java.util.List;




import fr.ebiz.computerDatabase.dto.ComputerDTO;

import fr.ebiz.computerDatabase.model.Computer;


public class ComputerMapper {
    

    public ComputerDTO getComputerDTO(Computer cp){
        return new ComputerDTO(cp);
    }
    
    public List<ComputerDTO> getComputerDTOs(List<Computer> cp){
        List<ComputerDTO> allComputer = new ArrayList<>();
        System.out.println(cp.size());
        for(int i=0;i<cp.size();i++){
            allComputer.add(new ComputerDTO(cp.get(i)));
        }
        return allComputer;
    }
}
