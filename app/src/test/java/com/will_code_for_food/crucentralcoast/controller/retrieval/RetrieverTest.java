package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Testing reflection-based implementation of Retrievers
 *
 * For some reason, these tests won't run...
 */
public class RetrieverTest extends ActivityInstrumentationTestCase2 <MainActivity>{

    public RetrieverTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testGetCampuses() throws Exception {
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.CAMPUS);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
    }

    @Test
    public void testGetMinistries() throws Exception {
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.MINISTRY);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
    }

    @Test
    public void testGetRides() throws Exception {
        System.out.println("**");
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.RIDE);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
        assertEquals(0, 1);
    }

    @Test
    public void testGetEvents() throws Exception {
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.EVENT);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
    }

    @Test
    public void testGetSummerMissions() throws Exception {
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.SUMMER_MISSION);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
    }
}