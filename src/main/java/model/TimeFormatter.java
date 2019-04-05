package model;

/**
 * This is a utility class for formatting time.
 */
public class TimeFormatter {

    private TimeFormatter() {

    }

    public static String getTimeLeftFormatted(TimerModel timerModel) {
        long timeLeft = timerModel.getTimeLeft();
        long hour = (timeLeft / (60 * 60 * 1000)) % 24;
        long min = (timeLeft / (60 * 1000)) % 60;
        long sec = (timeLeft / 1000) % 60;

        return addLeadingOs(hour) + ":" + addLeadingOs(min) + ":" + addLeadingOs(sec);
    }

    private static String addLeadingOs(long value) {
        return value == 0 ? "00" : (value < 10) ? "0" + value : value + "";
    }
}
