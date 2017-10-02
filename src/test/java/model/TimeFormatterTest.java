package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by softish on 2017-10-02.
 */
public class TimeFormatterTest {

    @Test
    public void getElapsedTimeFormatted() throws Exception {
        TimerModel timerModel = new TimerModel(20);
        String elapsed = TimeFormatter.getTimeLeftFormatted(timerModel);
        assertEquals("00:20:00", elapsed);
    }

    @Test
    public void getElapsedTimeFormatted2() throws Exception {
        TimerModel timerModel = new TimerModel(60);
        String elapsed = TimeFormatter.getTimeLeftFormatted(timerModel);
        assertEquals("01:00:00", elapsed);
    }

    @Test
    public void getElapsedTimeFormatted3() throws Exception {
        TimerModel timerModel = new TimerModel(120);
        String elapsed = TimeFormatter.getTimeLeftFormatted(timerModel);
        assertEquals("02:00:00", elapsed);
    }
}