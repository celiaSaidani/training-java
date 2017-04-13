package fr.ebiz.computerDatabase.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime convertDate(String date) {
		try {

			LocalDateTime formatDateTime = LocalDateTime.parse(date, formatter);
			return formatDateTime;
		} catch (DateTimeParseException e) {
			return null;
		}

	}

	public static String DateToString(LocalDateTime date) {
		if (date == null)
			return null;

		String dateString = date.format(formatter);

		return dateString;

	}

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
