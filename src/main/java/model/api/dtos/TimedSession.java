package model.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by softish on 2017-10-04.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimedSession {

    private long userId;
    private long duration;

    public TimedSession(long userId, long duration) {
        this.userId = userId;
        this.duration = duration;
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
}
