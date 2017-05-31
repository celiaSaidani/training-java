package fr.ebiz.computerDatabase.dto;

import java.util.List;

public class ComputerDTOPage {

  private Long totalcount;
  private int nbrPage;
  private List<ComputerDTO> computersDTO;

  /**
   * @return the count
   */
  public Long getTotalcount() {
    return totalcount;
  }

  /**
   * @param totalcount
   *          the count to set
   */
  public void setTotalcount(Long totalcount) {
    this.totalcount = totalcount;
  }

  /**
   * @return the computersDTO
   */
  public List<ComputerDTO> getComputersDTO() {
    return computersDTO;
  }

  /**
   * @param computersDTO
   *          the computersDTO to set
   */
  public void setComputersDTO(List<ComputerDTO> computersDTO) {
    this.computersDTO = computersDTO;
  }

  public int getNbrPage() {
    return nbrPage;
  }

  public void setNbrPage(int nbrPage) {
    this.nbrPage = nbrPage;
  }
}