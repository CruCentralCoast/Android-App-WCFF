package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.Video;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by MasonJStevenson on 3/8/2016.
 */
public class DatabaseObjectSorterTest extends TestCase {

    Content<DatabaseObject> mockFeed;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mockFeed = new Content<DatabaseObject>(TestDB.getFeed(), ContentType.TEST);
    }

    public void testFilterByName() {
        System.out.println("DatabaseObjectSorterTest : testFilterByName");

        Content<DatabaseObject> filteredFeed = DatabaseObjectSorter.filterByName(mockFeed, "max");
        assertEquals(2, filteredFeed.size());

        filteredFeed = DatabaseObjectSorter.filterByName(mockFeed, "MAX");
        assertEquals(2, filteredFeed.size());

        for (DatabaseObject obj : filteredFeed) {
            if (obj instanceof Event) {
                assertEquals("Max's Golf Lessons", obj.getName());
            } else if (obj instanceof Resource) {
                assertEquals("Max's Guide to Leadership for Freshmen", ((Resource) obj).getTitle());
            } else if (obj instanceof Video) {
                //do nothing for now
            } else {
                fail("invalid database object in TestDB.getFeed()");
            }
        }
    }

    public void testSortFeedObjectsByType() {
        System.out.println("DatabaseObjectSorterTest : testSortFeedObjectsByType");

        int typeSwitches = 0;

        DatabaseObjectSorter.sortFeedObjectsByType(mockFeed, SortMethod.DESCENDING);

        for (int count = 1; count < mockFeed.size(); count++) {
            if (mockFeed.get(count).getClass() != mockFeed.get(count - 1).getClass()) {
                typeSwitches++;
            }
        }

        //currently, there are three types of objects in the feed, so it should switch 2 times
        assertEquals(2, typeSwitches);
    }
}
