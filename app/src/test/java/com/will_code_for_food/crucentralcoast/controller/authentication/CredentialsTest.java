package com.will_code_for_food.crucentralcoast.controller.authentication;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Gavin on 2/11/2016.
 */
public class CredentialsTest extends WCFFUnitTest {

    @Test
    public void testCredentials() {
        String name = "asCJAkda826%^&";
        String pass = "sf&ijsns98&^78s";
        Credentials cred = new Credentials(name, pass);

        Assert.assertEquals(name.toLowerCase(), cred.username);
        Assert.assertEquals(pass, cred.password);
    }
}
