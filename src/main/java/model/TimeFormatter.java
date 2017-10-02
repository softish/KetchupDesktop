package model;

/**
 * Created by softish on 2017-10-02.
 */
public class TimeFormatter {
    public static String getTimeLeftFormatted(TimerModel timerModel) {
        long timeLeft = timerModel.getTimeLeft();
        long hour = (timeLeft / (60 * 60 * 1000)) % 24;
        long min = (timeLeft / (60 * 1000)) % 60;
        long sec = (timeLeft / 1000) % 60;

        return addLeadingOs(hour) + ":" + addLeadingOs(min) + ":" + addLeadingOs(sec);
    }

    private static String addLeadingOs(long value) {
        return value == 0 ? "00" : (value < 10) ? "0" + value : value +"";
    }
}
