package model.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by softish on 2017-10-06.
 */
public class AuthenticatedUser {

    private long id;
    private String userName;

    @JsonCreator
    public AuthenticatedUser(@JsonProperty("id") long id,
                             @JsonProperty("userName") String userName) {
        this.id = id;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
