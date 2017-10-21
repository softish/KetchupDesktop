package model.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * This class represents a Domain Transfer Object of a user
 * that has been authenticated by the API.
 */
public class AuthenticatedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String userName;

    @JsonCreator
    public AuthenticatedUser(@JsonProperty("id") long id,
                             @JsonProperty("username") String username) {
        this.id = id;
        this.userName = username;
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
