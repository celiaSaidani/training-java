package fr.ebiz.computerDatabase.Exception;

public class EmptyStringException extends Exception{
    static String nameRequired="name requeried";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
   public EmptyStringException() {
       super(nameRequired);
}
   public String getMessage()
   {
       return nameRequired;
   }
    
}
