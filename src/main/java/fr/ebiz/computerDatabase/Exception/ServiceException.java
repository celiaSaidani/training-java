package fr.ebiz.computerDatabase.Exception;

public class ServiceException extends Exception {
  private static final long serialVersionUID = 1L;
  private static final String STANDARD = "[Service Exception, communication with DAO FAIL]";

  /**
   * @param message
   *          to print
   */
  public ServiceException(String message) {

    super(STANDARD + " " + message);
  }
}