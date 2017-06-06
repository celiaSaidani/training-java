package fr.ebiz.computerDatabase.mapper;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import fr.ebiz.computerDatabase.model.Company;
import fr.ebiz.computerDatabase.model.Computer;
import fr.ebiz.computerDatabase.validator.DateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ComputerMapper {
    private static final String TIME = " 00:00:00";

    /**
     * @param cp computer
     * @return computer DTO
     */
    public ComputerDTO getComputerDTO(Computer cp) {
        return new ComputerDTO(cp);
    }

    /**
     * @param cp computer
     * @return list of computer DTO
     */

    public List<ComputerDTO> getComputerDTOs(List<Computer> cp) {
        List<ComputerDTO> allComputer = new ArrayList<>();
        for (int i = 0; i < cp.size(); i++) {
            allComputer.add(new ComputerDTO(cp.get(i)));
        }
        return allComputer;
    }

    /**
     * @param computerDTO to map
     * @return a computer object
     */
    public Computer getComputer(ComputerDTO computerDTO) {
        Computer computer;
        LocalDateTime dateIn = null;
        LocalDateTime dateOut = null;
        String name = computerDTO.getNameComp();
        Company company = null;
        Long id = null;
        String companyId = null;


        if (computerDTO.getIdComp() != null) {
            id = Long.parseLong(computerDTO.getIdComp());
        }
        String companyName = computerDTO.getNameCompany();


        if (computerDTO.getDateIn() != null) {
            dateIn = DateTime.convertDate(computerDTO.getDateIn().trim().concat(TIME));
        }
        if (computerDTO.getDateOut() != null) {
            dateOut = DateTime.convertDate(computerDTO.getDateOut().trim().concat(TIME));
        }
        if (computerDTO.getIdCompany() != null) {
            companyId = computerDTO.getIdCompany();
            company = new Company(Long.parseLong(companyId), companyName);
        }
        if (id != null) {
            computer = new Computer(id, name, dateIn, dateOut, company);
        } else {

            computer = new Computer(name, dateIn, dateOut, company);

        }
        return computer;
    }
}
