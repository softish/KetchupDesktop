package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by softish on 2017-10-02.
 */
public class TimerModel implements Subject {

    private long timeTarget;
    private long timeLeft;
    private TimerModelTask timerModelTask;

    private List<Observer> observers;

    /**
     * Constructs a timer with time out at the provided timeTarget.
     *
     * @param timeTarget when the time out should occur in minutes
     */
    public TimerModel(long timeTarget) {
        this.timeTarget = timeTarget * 60 * 1000;
        timerModelTask = new TimerModelTask(this.timeTarget, this);
        timeLeft = this.timeTarget;
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
}
