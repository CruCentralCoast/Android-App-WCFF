package com.will_code_for_food.crucentralcoast.controller;

import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testing reflection-based implementation of Retrievers
 */
public class RetrieverTest {
    @Test
    public void testRetriever() throws Exception {
        SingleRetriever retriever = new SingleRetriever(null);
        List<DatabaseObject> objects = retriever.getAll();
    }
}