package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
    private static Calendar timeToRefresh;
    private static Calendar justNow;
    private static Calendar hourAgo;
    private static Calendar today;
    private static Calendar yesterday;

    private static SimpleDateFormat timeTodayFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yy");

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

        yesterday = Calendar.getInstance();
        yesterday.setTime(today.getTime());
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

    }
    public static String fullRelativeToString(Date date) {
        Calendar now = Calendar.getInstance();
        if (timeToRefresh==null || timeToRefresh.before(now)) init();
        Calendar then = Calendar.getInstance();
        then.setTime(date);

        if (justNow.before(then)) {
            return "только что";
        } else if (hourAgo.before(then)) {
            int minutes = (int)((now.getTimeInMillis()-then.getTimeInMillis())/1000/60);
            return " "+minuteCases(minutes)+" назад";
        } else if (today.before(then)) {
            return "сегодня "+timeTodayFormat.format(date);
        } else if (yesterday.before(then)) {
            return "вчера "+timeTodayFormat.format(date);
        } else {
            return fullDateFormat.format(date);
        }
    }

    public static String shortRelativeToString(Date date) {
        Calendar now = Calendar.getInstance();
        if (timeToRefresh==null || timeToRefresh.before(now)) init();
        Calendar then = Calendar.getInstance();
        then.setTime(date);

        if (justNow.before(then)) {
            return "сейчас";
        } else if (hourAgo.before(then)) {
            int minutes = (int)((now.getTimeInMillis()-then.getTimeInMillis())/1000/60);
            return minutes+" мин.";
        } else if (today.before(then)) {
            return timeTodayFormat.format(date);
        } else {
            return fullDateFormat.format(date);
        }
    }

    private static String minuteCases(int minutes) {
        int dec = minutes % 10;
        if (dec ==0 || dec >=5 || (minutes >=11 && minutes <=14)) return "минут"; //0, 5 6 7 8 9 10 - 20 25-30
        if (dec == 1) return "минута"; //1, !11, 21, 31, 41...
        return "минуты"; //2-4 22-24 32-34
    }
}
