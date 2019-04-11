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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is responsible of interacting with the API.
 */
public class APIDriver {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ssX";

    private final String baseUrl;

    private User user;
    private AuthenticatedUser authenticatedUser;
    private final String baseUrl;

    public APIDriver(boolean developmentMode) {
        if (developmentMode) {
            baseUrl = "http://localhost:8080";
        } else {
            baseUrl = "http://ketchup.zapto.org:5757";
        }
    }

    public void saveSession(long userId, long sessionDuration, String task) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        TimedSession timedSession = new TimedSession(userId, sessionDuration, task, simpleDateFormat.format(new Date()));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TimedSession> request = new HttpEntity<>(timedSession);

        try {
            restTemplate.postForObject(baseUrl + "/session/save", request, TimedSession.class);
        } catch (RestClientException e) {
            throw new ServerUnreachableException("Server unreachable");
        }
    }

    public void saveSession(int duration, String task) {
        if (authenticatedUser == null) {
            authenticatedUser = authenticate(this.user.getUsername(), this.user.getPassword());
        }
        saveSession(authenticatedUser.getId(), duration, task);
    }

    public void register(String username, String password) {
        User user = new User(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(user);

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                if (response.getStatusCode().equals(HttpStatus.CONFLICT)) {
                    throw new UserAlreadyExistsException("User already exists");
                }
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {

            }
        });

        try {
            restTemplate.postForObject(baseUrl + "/user/register", request, User.class);
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
                if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    throw new ErroneousCredentialsException("No such user");
                }
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {

            }
        });

        try {
            authenticatedUser = restTemplate.postForObject(baseUrl + "/user/authenticate", request, AuthenticatedUser.class);
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

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
}
