package fr.ebiz.computerDatabase.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import fr.ebiz.computerDatabase.dto.ComputerDTO;

public class ComputerValidator {

  /**
   * check if computer DTO is valid.
   * @param cp
   *          computer DTO
   * @return true if valid else false
   */
  public static boolean isValid(ComputerDTO cp) {

    String invalideDateIN = "Date introduced inavlide";
    String invalideDateOUT = "Date discontinued inavlide";
    String dateNotBefore = "date out before date in";
    String nameRequired = "name requeried";
    LocalDateTime dateIn, dateOut;
    String date1 = cp.getDateIn();
    String date2 = cp.getDateOut();

    if (cp.getNameComp().equals("")) {

      throw new NullPointerException(nameRequired);
    }
    if (!date1.equals("")) {
      dateIn = DateTime.convertDate(date1.trim().concat(" 00:00:00"));
      if (dateIn == null) {
        throw new DateTimeParseException(invalideDateIN, date1, 0);
      }
    }

    if (!date2.equals("")) {
      dateOut = DateTime.convertDate(date2.trim().concat(" 00:00:00"));
      if (dateOut == null) {
        throw new DateTimeParseException(invalideDateOUT, date2, 0);

      }
    }
    if ((!date1.equals("")) && (!date2.equals(""))) {
      if (!DateTime.dateCompare(date1.trim().concat(" 00:00:00"),
          date2.trim().concat(" 00:00:00"))) {
        throw new DateTimeParseException(dateNotBefore, date1, 0);

      }
    }

    return true;

  }
}
