package model;

/**
 * Enforcing the subject-observer pattern.
 */
public interface Subject {
    void subscribe(Observer observer);

    void unSubscribe(Observer observer);

    void notifyObservers(TimerEvent timerEvent);
}
