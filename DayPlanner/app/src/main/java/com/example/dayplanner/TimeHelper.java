package com.example.dayplanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {
    private static final int MILLIS_TO_MINUTES = 1000 * 60;
    private static final int MILLIS_TO_HOURS = MILLIS_TO_MINUTES * 60;

    private static final String SEPARATOR_ESTIMATED_TIME = ",";
    private static final String TAG = "TimeHelper";
    private static final int SPACE_PER_REMAINING_TIME = 10;

    private static final int TEST_SECONDS = 1000 * 5;

    String time;

    /**
     * utility class - no instance
     */
    TimeHelper() {
    }

    public String convTime(String oldTime)
    {
        String time = "13:30";
        Date date = null;
        String formatted = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm aa");
        try {
            date = dateFormat.parse(time);
            formatted = dateFormat2.format(date);



        } catch (ParseException e) {

        }
        return formatted;
    }

    public String getCurrentDate()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public String getCurrentTime()
    {
        String currentTime = Calendar.getInstance().getTime().toString().split(" ")[3].replace(" ", "");
        currentTime = currentTime.split(":")[0] + ":" + currentTime.split(":")[1];

        return currentTime;
    }

    public Date formatDate(String mydate, String mytime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");
        Date date = dateFormat.parse(mydate + "T" + mytime);
        return date;
    }

    public long getDateDifference(Date d1, Date d2)
    {
        long diff = 0;
        //            diff = th.formatDate("09/06/2021", currentTime).getTime() - Calendar.getInstance().getTime().getTime();
        return (d1.getTime() - d2.getTime());
    }



//    public static long remainTime(String d) {
//        try {
//            Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//                    .parse(d);
//            long remaining = date.getTime() - System.currentTimeMillis();
//            Log.d("remain", remaining + " ");
//            return TEST_SECONDS;
//            //  return remaining;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return TEST_SECONDS;
//        }
//    }
//
//    public static String toRemainingTime(Date now, String timeData,
//                                         String formatOnlyMinutes, String formatMinutesAndHours) {
//        final Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//
//        final String[] times = timeData.split(SEPARATOR_ESTIMATED_TIME);
//        final StringBuilder result = new StringBuilder(times.length * SPACE_PER_REMAINING_TIME);
//        for (String time : times) {
//            try {
//                result.append(toRemainingTime(now, time, formatOnlyMinutes, formatMinutesAndHours, calendar));
//                result.append(SEPARATOR_ESTIMATED_TIME);
//            } catch (Exception e) {
//                Log.e(TAG, "could not convert " + time, e);
//                result.append(time);
//            }
//        }
//        if (result.length()>0) {
//            //remove last comma
//            result.deleteCharAt(result.length() - 1);
//        }
//
//        return result.toString();
//    }
//
//    private static String toRemainingTime(Date now, String time,
//                                          String formatOnlyMinutes, String formatMinutesAndHours,
//                                          Calendar calendar) {
//        final String[] hoursMinutes = time.split(":");
//        if (hoursMinutes.length != 2) {
//            throw new IllegalArgumentException(
//                    "could not split hours-minutes: " + time);
//        }
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursMinutes[0]));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(hoursMinutes[1]));
//
//        final long arrivingTime = calendar.getTimeInMillis();
//
//        final long remainingTimeInMillis = arrivingTime - now.getTime();
//        if (remainingTimeInMillis < 0) {
//            throw new IllegalArgumentException(String.format(
//                    "negative remaining time: time = %s, now = %s, diff = %s",
//                    time, now, remainingTimeInMillis));
//        }
//        return remainingTimeToHumanReadableForm(remainingTimeInMillis, formatOnlyMinutes, formatMinutesAndHours);
//    }
//
//    /**
//     * @param remaining
//     * @return something like ~1?.22?.
//     */
//    private static String remainingTimeToHumanReadableForm(long remaining,
//                                                           String formatOnlyMinutes, String formatMinutesAndHours) {
//        int minutes = (int) ((remaining % MILLIS_TO_HOURS) / MILLIS_TO_MINUTES);
//        int hours = (int) (remaining / MILLIS_TO_HOURS);
//
//        if (hours > 0) {
//            return String.format(formatMinutesAndHours, hours, minutes);
//        } else {
//            return String.format(formatOnlyMinutes, minutes);
//        }
//    }
//
//    public static String removeTrailingSeparator(String timeData) {
//        if (!timeData.endsWith(SEPARATOR_ESTIMATED_TIME)) {
//            return timeData;
//        }
//        // sometimes estimated time has one more comma: 21:00,21:20,
//        return timeData.substring(0, timeData.length() - 1);
//    }

}
