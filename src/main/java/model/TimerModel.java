package model;

/**
 * Created by softish on 2017-10-02.
 */
public class TimerModel {

    private long timeTarget;
    private long timeLeft;
    private TimerModelTask timerModelTask;

    /**
     * Constructs a timer with time out at the provided timeTarget.
     *
     * @param timeTarget when the time out should occur in minutes
     */
    public TimerModel(long timeTarget) {
        this.timeTarget = timeTarget * 60 * 1000;
        timerModelTask = new TimerModelTask(this.timeTarget, this);
        timeLeft = this.timeTarget;
    }

    public void startTimer() {
        timerModelTask.start();
    }

    public void stopTimer() {

    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }
}
