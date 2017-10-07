package model.api;

import model.api.dtos.AuthenticatedUser;
import model.api.dtos.TimedSession;
import model.api.dtos.User;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by softish on 2017-10-04.
 */
public class APIDriver {

    private User user;

    private static final String BASE_URL = "http://localhost:8080";

    public void saveSession(long userId, long sessionDuration) {
        TimedSession timedSession = new TimedSession(userId, sessionDuration);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TimedSession> request = new HttpEntity<>(timedSession);
        restTemplate.postForObject(BASE_URL+"/session/save", request, TimedSession.class);
    }

    public void saveSession(int duration) {
        AuthenticatedUser user = authenticate("KetchupUser", "KetchupUser");
        saveSession(user.getId(), duration);
    }

    public void register(String username, String password) {
        User user = new User(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForObject(BASE_URL+"/user/register", request, User.class);
    }

    public AuthenticatedUser authenticate(String username, String password) {
        User user = new User(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(user);
        return restTemplate.postForObject(BASE_URL+"/user/authenticate", request, AuthenticatedUser.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
