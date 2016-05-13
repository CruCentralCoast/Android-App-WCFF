package com.will_code_for_food.crucentralcoast.view;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.lang.Exception;
import java.lang.Override;

/**
 * Created by MasonJStevenson on 11/5/2015.
 *
 * This class is an example of an Android Instrumentation Test. Here you can test pieces of the UI.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public MainActivityTests () {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        activity = getActivity();
    }

    public void testTextViewNotNull() {
        assertNotNull(activity);

        //TextView textView = (TextView) activity.findViewById(R.id.helloTextView);
        //assertNotNull(textView);

        //uncomment this if you want to see a test fail
        //assertEquals("not equal", textView.getText().toString());
    }

    /**
     * Test loadFragmentById since it is a pinch point (all descendents of MainActivity use it regularly)
     */
    public void testLoadFragmentById() {
        activity.loadFragmentById(R.layout.fragment_event, "Events", null, activity);
        ListView listView = (ListView) activity.findViewById(R.id.list_cards);
        assertTrue(listView != null);
    }
}
