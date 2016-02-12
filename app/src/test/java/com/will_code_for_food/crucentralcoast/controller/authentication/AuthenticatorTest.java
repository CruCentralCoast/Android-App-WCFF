package com.will_code_for_food.crucentralcoast.controller.authentication;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Gavin on 2/11/2016.
 */
public class AuthenticatorTest extends WCFFUnitTest {
    private String origName;
    private String origPass;
    private boolean origLoggedIn;
    // TODO we REALLY need to figure out a way to test stuff that needs a context (IO)
/*
    @Before
    public void setup() {
        origName = null;
        if ( LocalStorageIO.fileExists(LocalFiles.CREDENTIALS)) {
            Log.i("Auth Test", "Temporarily deleting credentials file");
            HashMap<String, String> map = LocalStorageIO.readMap(LocalFiles.CREDENTIALS);
            origName = map.get(Authenticator.USERNAME_KEY);
            origPass = map.get(Authenticator.PASSWORD_KEY);
            LocalStorageIO.deleteFile(LocalFiles.CREDENTIALS);
        }
        origLoggedIn = Authenticator.isUserLoggedIn();
        if (origLoggedIn) {
            Authenticator.logOut();
        }
    }

    @Test
    public void testLogIn() {
        Assert.assertFalse(Authenticator.isUserLoggedIn());
        Credentials cred = new Credentials("user", "1234");

        // test string login
        Authenticator.logIn(cred.username, cred.password);
        Assert.assertTrue(Authenticator.isUserLoggedIn());

        // test cred log in
        Authenticator.logOut();
        Authenticator.logIn(cred);
        Assert.assertTrue(Authenticator.isUserLoggedIn());
    }

    @Test
    public void testSavedCredentials() {
        Assert.assertFalse(Authenticator.isUserLoggedIn());
        Authenticator.logIn("user", "1234");
        Authenticator.logOut();
        Assert.assertFalse(Authenticator.isUserLoggedIn());

        Authenticator.logIn();
        Assert.assertTrue(Authenticator.isUserLoggedIn());
    }

    @Test
    public void testLogout() {
        Assert.assertFalse(Authenticator.isUserLoggedIn());
        Authenticator.logIn("user", "1234");
        Authenticator.logOut();
        Assert.assertFalse(Authenticator.isUserLoggedIn());
    }

    @Test
    public void testInvalidCredentials() {
        Assert.assertFalse(Authenticator.isUserLoggedIn());
        Authenticator.logIn("GARBAGE", "GARBAGE");
        Assert.assertFalse(Authenticator.isUserLoggedIn());
    }

    @After
    public void tearDown() {
        if (origName != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Authenticator.USERNAME_KEY, origName);
            map.put(Authenticator.PASSWORD_KEY, origPass);
            LocalStorageIO.writeMap(map, LocalFiles.CREDENTIALS);
        }
        if (origLoggedIn) {
            Authenticator.logIn();
        }
    }
    */
}
