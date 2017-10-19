package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models and handles a timer.
 * The subject-observer pattern is used to
 * notify observers of changes to the timer.
 */
public class TimerModel implements Subject {

    private long timeTargetMillis;
    private long timeLeft;
    private TimerModelTask timerModelTask;

    private List<Observer> observers;

    /**
     * Constructs a timer with time out at the provided timeTargetMillis.
     *
     * @param timeTargetMillis when the time out should occur in milliseconds
     */
    public TimerModel(long timeTargetMillis) {
        this.timeTargetMillis = timeTargetMillis;
        timerModelTask = new TimerModelTask(this.timeTargetMillis, this);
        timeLeft = this.timeTargetMillis;
        observers = new ArrayList<>();
    }

    public void startTimer() {
        if(!timerModelTask.isAlive()) {
            timerModelTask = new TimerModelTask(timeLeft, this);
            timerModelTask.start();
        }
    }

    public void stopTimer() {
        timerModelTask.end();
    }

    public boolean isTimerActive() {
        return timerModelTask.isAlive();
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
        notifyObservers(TimerEvent.TICK);
    }

    public void timeout() {
        notifyObservers(TimerEvent.TIME_OUT);
    }

    public void resetTimer() {
        timeLeft = timeTargetMillis;
        notifyObservers(TimerEvent.RESET);
    }

    public int getSessionDurationMillis() {
        return Math.toIntExact(timeTargetMillis - timeLeft / 60000);
    }

    @Override
    public void subscribe(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unSubscribe(Observer observer) {
        if(observers.contains(observer)) {
            observers.remove(observers.indexOf(observer));
        }
    }

    @Override
    public void notifyObservers(TimerEvent timerEvent) {
        for(Observer observer : observers) {
            observer.update(timerEvent);
        }
    }

    /**
     * This class is responsible of managing the flow of time.
     */
    private class TimerModelTask extends Thread {

        private long timeTarget;
        private TimerModel timerModel;
        private boolean end;

        public TimerModelTask(long timeTarget, TimerModel timerModel) {
            this.timeTarget = timeTarget;
            this.timerModel = timerModel;
        }

        @Override
        public void run() {
            while (timeTarget > 0) {
                try {
                    if(end) {
                        break;
                    }

                    timeTarget -= 1000;
                    timerModel.setTimeLeft(timeTarget);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(timeTarget == 0) {
                timerModel.timeout();
            }
        }

        public void end() {
            end = true;
        }
    }
}
