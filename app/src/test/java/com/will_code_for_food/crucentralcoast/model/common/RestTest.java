package com.will_code_for_food.crucentralcoast.model.common;

import com.will_code_for_food.crucentralcoast.model.common.components.RestUtil;
import junit.framework.Assert;
import org.junit.Test;
import java.io.IOException;

/**
 * Created by Brian on 11/16/2015.
 */
public class RestTest
{
    @Test
    public void testGet()
    {
        try {
            String getJson = RestUtil.get("");
            Assert.assertNotNull(getJson);
            Assert.assertNotSame("", getJson);
        }
        catch (IOException e)
        {
            Assert.fail(e.getMessage());
        }
    }
}
