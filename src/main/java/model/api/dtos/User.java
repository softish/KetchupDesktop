package model.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by softish on 2017-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String userName;
    private String password;

    @JsonCreator
    public User(@JsonProperty("userName") String userName,
                @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
