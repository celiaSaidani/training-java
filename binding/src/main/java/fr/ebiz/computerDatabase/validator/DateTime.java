package fr.ebiz.computerDatabase.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime {
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * @param date
   *          String date
   * @return LocalDateTime
   */
  public static LocalDateTime convertDate(String date) {
    try {

      LocalDateTime formatDateTime = LocalDateTime.parse(date, formatter);
      return formatDateTime;
    } catch (DateTimeParseException e) {
      return null;
    }

  }

  /**
   * @param date
   *          String date
   * @return string of localDateTime
   */
  public static String dateToString(LocalDateTime date) {
    if (date == null) {
      return null;
    }

    String dateString = date.format(formatter);

    return dateString;

  }

  /**
   * @param date1
   *          first date
   * @param date2
   *          seconde date
   * @return true if date1 before date2 else false
   */

  public static boolean dateCompare(String date1, String date2) {
    try {

      LocalDateTime formatDateTime = LocalDateTime.parse(date1, formatter);
      LocalDateTime formatDateTime2 = LocalDateTime.parse(date2, formatter);
      return formatDateTime.isBefore(formatDateTime2);
    } catch (DateTimeParseException e) {
      return false;
    }

  }

}
