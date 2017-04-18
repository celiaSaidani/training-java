package fr.ebiz.computerDatabase.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import fr.ebiz.computerDatabase.validator.DateTime;

public class DateTest {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String date2="2017-05-05 00:00:00";
    String date3="2017-05-06 00:00:00";

    LocalDateTime localDate= LocalDateTime.parse(date2,formatter);

    @Test
    public void testConvertDate() {

        String date1="ccc";
        assertNull(DateTime.convertDate(date1));
        assertEquals(localDate,DateTime.convertDate(date2));
    }

    @Test
    public void testDateToString() {
        LocalDateTime date=null;
        assertNull(DateTime.DateToString(date));
        assertEquals(date2, DateTime.DateToString(localDate));

    }

    @Test
    public void testDateCompare() {
        assertTrue( DateTime.dateCompare(date2, date3));
        assertFalse(DateTime.dateCompare(date3, date2));
    }


}
