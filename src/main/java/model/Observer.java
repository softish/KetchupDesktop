package model;

/**
 * Enforcing the subject-observer pattern.
 */
public interface Observer {
    void update(TimerEvent timerEvent);
}
