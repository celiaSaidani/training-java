package fr.ebiz.computerDatabase.exception;

public class DAOException extends Exception {

  private static final long serialVersionUID = 1L;
  private static String standard = "[DAO EXCEPTION communication with dataBase fail]";

  /**
   * @param t throwable
   */
  public DAOException(Throwable t) {
      super(t);
  }
  /**
   * @param message
   *          to print
   */
  public DAOException(String message) {
    super(standard + " " + message);
  }
}
