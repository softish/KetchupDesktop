package model;

import model.api.APIDriver;
import model.api.dtos.AuthenticatedUser;
import model.api.dtos.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by softish on 2017-10-04.
 */
public class APIDriverTest {
    @Test
    public void testSaveSession() throws Exception {
        APIDriver apiDriver = new APIDriver();
        apiDriver.saveSession(1, 20);
    }

    @Test
    public void testRegister() throws Exception {
        APIDriver apiDriver = new APIDriver();
        apiDriver.register("testUser", "testPassword");
    }

    @Test
    public void testRegister2() throws Exception {
        APIDriver apiDriver = new APIDriver();
        apiDriver.register("user", "12345678");
    }
    @Test
    public void testAuthenticate() throws Exception {
        APIDriver apiDriver = new APIDriver();
        AuthenticatedUser user = apiDriver.authenticate("testUser", "testPassword");
        assertEquals(user.getId(), 1);
    }
}