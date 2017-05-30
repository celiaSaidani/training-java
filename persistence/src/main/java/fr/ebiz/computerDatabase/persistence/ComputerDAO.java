package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerDAO extends JpaRepository<Computer, Long> {

    /**
     *
     * @param computerName name of computer
     * @param companyName name of company
     * @param pageRequest page request
     * @return a page
     */
    Page findComputerByNameContainingOrCompanyNameContaining(String computerName, String companyName, Pageable pageRequest);
}
