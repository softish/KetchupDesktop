package model;

/**
 * Represents the types of events that the
 * timer can use to notify its observers about changes.
 */
public enum TimerEvent {
    TICK,
    TIME_OUT,
    RESET
}
