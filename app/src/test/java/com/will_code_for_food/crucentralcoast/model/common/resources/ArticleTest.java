package com.will_code_for_food.crucentralcoast.model.common.resources;

import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.model.resources.LeaderArticleCardFactory;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.view.resources.ArticleCardFactory;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Kayla on 4/4/2016.
 */
public class ArticleTest extends TestCase {

    public void testLeaderArticles() {
        ArrayList<Resource> resources = TestDB.getResources();
        ArticleCardFactory factory = new LeaderArticleCardFactory();

        for (Resource resource : resources) {
            assertEquals(resource.isRestricted(), factory.include(resource));
        }
    }

    public void testNonLeaderArticles() {
        ArrayList<Resource> resources = TestDB.getResources();
        ArticleCardFactory factory = new ArticleCardFactory();

        for (Resource resource : resources) {
            assertEquals(!resource.isRestricted(), factory.include(resource));
        }
    }
}
