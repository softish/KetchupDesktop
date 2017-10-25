package model.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class represents a Domain Transfer Object of a session.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimedSession {

    private long userId;
    private long duration;
    private String task;
    private String endDateTime;

    public TimedSession(long userId, long duration) {
        this.userId = userId;
        this.duration = duration;
    }

    public TimedSession(long userId, long duration, String task, String endDateTime) {
        this.userId = userId;
        this.duration = duration;
        this.task = task;
        this.endDateTime = endDateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
