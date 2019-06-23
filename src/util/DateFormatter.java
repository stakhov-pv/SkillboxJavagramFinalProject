package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
    private static Calendar timeToRefresh;
    private static Calendar justNow;
    private static Calendar hourAgo;
    private static Calendar today;

    private static SimpleDateFormat timeTodayFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy в HH:mm");

    private static void init() {
        timeToRefresh = Calendar.getInstance();
        timeToRefresh.add(Calendar.MINUTE,2);

        justNow = Calendar.getInstance();
        justNow.add(Calendar.MINUTE, -2);

        hourAgo = Calendar.getInstance();
        hourAgo.add(Calendar.MINUTE,-59);

        today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);
        today.set(Calendar.MILLISECOND,0);

    }
    public static String relativeToString(Date date) {
        Calendar now = Calendar.getInstance();
        if (timeToRefresh==null || timeToRefresh.before(now)) init();
        Calendar then = Calendar.getInstance();
        then.setTime(date);

        if (justNow.before(then)) {
            return "Только что";
        } else if (hourAgo.before(then)) {
            return ((now.getTimeInMillis()-then.getTimeInMillis())/1000/60)+" минут назад";
        } else if (today.before(then)) {
            return "Сегодня в "+timeTodayFormat.format(date);
        } else {
            return fullDateFormat.format(date);
        }
    }
}
