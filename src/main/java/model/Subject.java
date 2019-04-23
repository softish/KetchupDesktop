package model;

/**
 * Enforcing the subject-observer pattern.
 */
interface Subject {
    void subscribe(Observer observer);

    void unSubscribe(Observer observer);

    void notifyObservers(TimerEvent timerEvent);
}
