package com.will_code_for_food.crucentralcoast.controller;

import com.will_code_for_food.crucentralcoast.WCFFUnitTest;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Tests LocalStorageIO
 */
public class LocalStorageTest extends WCFFUnitTest{
    private final String fname = "TEMP_FILE";
/*
    @Before
    public void setup() {
        LocalStorageIO.deleteFile(fname);
    }

    @Test
    public void testList() {
        // test writing
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("dog");
        list1.add("cat");
        list1.add("monkey");
        LocalStorageIO.writeList(list1, fname);
        List<String> list2 = LocalStorageIO.readList(fname);
        Assert.assertEquals(list1, list2);
        Assert.assertTrue(LocalStorageIO.listContains("dog", fname));

        // test append single item to list
        list1.add("new item");
        LocalStorageIO.appendToList("new item", fname);
        list2 = LocalStorageIO.readList(fname);
        Assert.assertEquals(list1, list2);

        // test append list to list
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("test");
        list3.add("test2");
        LocalStorageIO.appendToList(list3, fname);
        list1.addAll(list3);
        list2 = LocalStorageIO.readList(fname);
        Assert.assertEquals(list1, list2);


        // test overwriting
        list1 = new ArrayList<>();
        list1.add("new item");
        list1.add("new item 2");
        LocalStorageIO.writeList(list1, fname);
        list2 = LocalStorageIO.readList(fname);
        Assert.assertEquals(list1, list2);

        // test remove
        list1 = new ArrayList<>();
        list1.add("cat");
        LocalStorageIO.writeList(list1, fname);
        list2 = LocalStorageIO.readList(fname);
        Assert.assertFalse(list2 == null);
        Assert.assertTrue(list2.contains("cat"));
        LocalStorageIO.removeFromList("cat", fname);
        list2 = LocalStorageIO.readList(fname);
        Assert.assertFalse(list2 == null);
        Assert.assertFalse(list2.contains("cat"));
    }

    @Test
    public void testDeleteFile() {
        Assert.assertFalse(LocalStorageIO.fileExists(fname));
        LocalStorageIO.writeSingleLineFile(fname, "hello world");
        Assert.assertTrue(LocalStorageIO.fileExists(fname));
        LocalStorageIO.deleteFile(fname);
        Assert.assertFalse(LocalStorageIO.fileExists(fname));
        Assert.assertEquals(null, LocalStorageIO.readList(fname));
    }

    @Test
    public void testMaps() {
        // write
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("k", "v");
        map1.put("k2", "v2");
        LocalStorageIO.writeMap(map1, fname);
        HashMap map2 = LocalStorageIO.readMap(fname);
        Assert.assertEquals(map1, map2);

        Assert.assertFalse(LocalStorageIO.mapContainsKey("new key", fname));
        Assert.assertTrue(LocalStorageIO.mapContainsKey("k", fname));
        Assert.assertFalse(LocalStorageIO.mapContainsValue("new key", fname));
        Assert.assertTrue(LocalStorageIO.mapContainsValue("v", fname));

        // add
        LocalStorageIO.putInMap("new key", "new value", fname);
        Assert.assertTrue(LocalStorageIO.mapContainsKey("new key", fname));

        // remove
        LocalStorageIO.removeFromMap("new key", fname);
        Assert.assertFalse(LocalStorageIO.mapContainsKey("new key", fname));
    }

    @Test
    public void singleLineFile() {
        LocalStorageIO.writeSingleLineFile(fname, "line");
        String str = LocalStorageIO.readSingleLine(fname);
        Assert.assertEquals("line", str);
    }

    @After
    public void teardown() {
        LocalStorageIO.deleteFile(fname);
    }
    */
}
