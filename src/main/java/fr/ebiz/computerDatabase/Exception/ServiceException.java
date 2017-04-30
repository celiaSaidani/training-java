package fr.ebiz.computerDatabase.Exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private static String standard="[Service Exception, communication with DAO FAIL]";

    /**
     * @param message to print
     */
    public ServiceException(String message) {
     
        super(standard+" "+message);
    }
}