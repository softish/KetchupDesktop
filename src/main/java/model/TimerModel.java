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
        timerModelTask.start();
    }

    public void stopTimer() {

    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
        notifyObservers();
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
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(TimeFormatter.getTimeLeftFormatted(this));
        }
    }
}
