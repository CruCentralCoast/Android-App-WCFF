package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.test.ActivityInstrumentationTestCase2;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Testing reflection-based implementation of Retrievers
 */
public class RetrieverTest extends ActivityInstrumentationTestCase2{

    public RetrieverTest(){
        super(Retriever.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testRetriever() throws Exception {
        SingleRetriever retriever = new SingleRetriever(RetrieverSchema.CAMPUS);
        List<DatabaseObject> objects = retriever.getAll().getObjects();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
    }
}