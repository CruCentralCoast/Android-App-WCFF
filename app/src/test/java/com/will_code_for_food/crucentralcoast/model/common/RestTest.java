package com.will_code_for_food.crucentralcoast.model.common;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import junit.framework.Assert;

import com.google.gson.JsonArray;
import com.will_code_for_food.crucentralcoast.values.Database;

import org.junit.Test;
import java.io.IOException;
import java.util.List;

/**
 * Created by Brian on 11/16/2015.
 */
public class RestTest
{
    @Test
    public void testGet()
    {
        //Ministries
        JsonArray ministryJSON = RestUtil.get(Database.REST_MINISTRY);
    }

    @Test
    public void testFind()
    {

    }
}


