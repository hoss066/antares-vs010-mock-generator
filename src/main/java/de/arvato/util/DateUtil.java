package de.arvato.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

public class DateUtil {
    public static final String yyyyMMdd_FORMAT = "yyyyMMdd";

    public static String formatDate(Date date, String format) {
        return formatDate(date, format, null);
    }

    public static String formatDate(Date date, String format, TimeZone timezone) {
        if (date == null)
            return "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (timezone != null)
            formatter.setTimeZone(timezone);
        return formatter.format(date);
    }

    public static String getRandomDateString(GregorianCalendar gc, Random r) {
        Date date = Date.from(
                LocalDate.of(gc.get(1) + 2 + r.nextInt(4), 1 + r.nextInt(11), 1 + r.nextInt(28))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
        String formattedDate = formatDate(date, "yyyyMMdd");
        return formattedDate;
    }
}
