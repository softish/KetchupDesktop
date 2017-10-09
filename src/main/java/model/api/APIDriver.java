package model.api;

import exception.ErroneousCredentialsException;
import exception.ServerUnreachableException;
import exception.UserAlreadyExistsException;
import model.api.dtos.AuthenticatedUser;
import model.api.dtos.TimedSession;
import model.api.dtos.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                if(response.getStatusCode().equals(HttpStatus.CONFLICT)) {
                    throw new UserAlreadyExistsException("User already exists");
                }
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        });

        try {
            restTemplate.postForObject(BASE_URL+"/user/register", request, User.class);
        } catch (RestClientException e) {
            throw new ServerUnreachableException("Server unreachable");
        }
    }

    public AuthenticatedUser authenticate(String username, String password) {
        User user = new User(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(user);
        AuthenticatedUser authenticatedUser;

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    throw new ErroneousCredentialsException("No such user");
                }
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        });

        try {
            authenticatedUser = restTemplate.postForObject(BASE_URL+"/user/authenticate", request, AuthenticatedUser.class);
        } catch (RestClientException e) {
            throw new ServerUnreachableException("Server unreachable");
        }

        return authenticatedUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
