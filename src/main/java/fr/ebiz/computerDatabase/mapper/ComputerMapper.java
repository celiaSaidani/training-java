package fr.ebiz.computerDatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.model.Computer;
import org.springframework.stereotype.Component;

@Component
public class ComputerMapper {
  /**
   * @param cp
   *          computer
   * @return computer DTO
   */
  public ComputerDTO getComputerDTO(Computer cp) {
    return new ComputerDTO(cp);
  }

  /**
   * @param cp
   *          computer
   * @return list of computer DTO
   */

  public List<ComputerDTO> getComputerDTOs(List<Computer> cp) {
    List<ComputerDTO> allComputer = new ArrayList<>();
    for (int i = 0; i < cp.size(); i++) {
      allComputer.add(new ComputerDTO(cp.get(i)));
    }
    return allComputer;
  }
}
