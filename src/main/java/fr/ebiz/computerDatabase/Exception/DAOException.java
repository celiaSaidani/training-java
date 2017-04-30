package fr.ebiz.computerDatabase.Exception;

public class DAOException extends Exception {

  private static final long serialVersionUID = 1L;
  private static String standard = "[DAO EXCEPTION communication with dataBase fail]";

  /**
   * @param message
   *          to print
   */
  public DAOException(String message) {
    super(standard + " " + message);
  }
}