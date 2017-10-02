package model;

/**
 * Created by softish on 2017-10-02.
 */
public interface Subject {
    void subscribe(Observer observer);
    void unSubscribe(Observer observer);
    void notifyObservers();
}
