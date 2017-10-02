package model;

/**
 * Created by softish on 2017-10-02.
 */
public class TimerModelTask extends Thread {

    private long timeTarget;
    private TimerModel timerModel;

    public TimerModelTask(long timeTarget, TimerModel timerModel) {
        this.timeTarget = timeTarget;
        this.timerModel = timerModel;
    }

    @Override
    public void run() {
        while (timeTarget > 0) {
            try {
                timeTarget -= 1000;
                timerModel.setTimeLeft(timeTarget);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
