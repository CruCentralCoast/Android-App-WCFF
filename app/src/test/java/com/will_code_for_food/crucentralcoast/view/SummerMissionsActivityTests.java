package com.will_code_for_food.crucentralcoast.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.ArrayList;

/**
 * Created by Kayla on 3/9/2016.
 */
public class SummerMissionsActivityTests extends ActivityInstrumentationTestCase2<SummerMissionsActivity> {

    SummerMissionsActivity activity;
    ArrayList<SummerMission> missions;

    public SummerMissionsActivityTests () {
        super(SummerMissionsActivity.class);
        missions = TestDB.getSummerMissions();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testSetEvent() {
        assertNotNull(activity);

        SummerMission test = new SummerMission(null);
        activity.setMission(test);
        assertEquals(test, activity.getMission());
    }

    public void testEventCards() {
        // Test the list exists
        ListView listView = (ListView) activity.findViewById(R.id.list_cards);
        assertTrue(listView != null);

        // Test the card exists
        View card = listView.getChildAt(0);
        assertTrue(card != null);

        // Test the card image
        ImageView imageView = (ImageView) card.findViewById(R.id.card_image);
        assertTrue(imageView != null);
        // Test the card name
        TextView titleView = (TextView) card.findViewById(R.id.card_text);
        assertTrue(titleView != null);
        assertEquals(missions.get(0).getName(), titleView.getText());
        // Test the card date
        TextView dateView = (TextView) card.findViewById(R.id.card_date);
        assertTrue(dateView != null);
        assertEquals(missions.get(0).getMissionDateString(), dateView.getText());
    }

    public void testSelectCard() {
        // Perform the card selection
        ListView listView = (ListView) activity.findViewById(R.id.list_cards);
        listView.performItemClick(
                listView.getAdapter().getView(0, null, null),
                0,
                listView.getAdapter().getItemId(0));

        // Test the buttons
        Button applyButton = (Button) activity.findViewById(R.id.button_apply_mission);
        assertTrue(applyButton != null);

        // Test the image
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_event);
        assertTrue(imageView != null);
        // Test the cost
        TextView cost = (TextView) activity.findViewById(R.id.text_mission_cost);
        assertTrue(cost != null);
        assertEquals("Cost: $" + missions.get(0).getCost(), cost.getText());
        // Test the date
        TextView date = (TextView) activity.findViewById(R.id.text_mission_date);
        assertTrue(date != null);
        assertEquals(missions.get(0).getMissionFullDate(), date.getText());
        // Test the description
        TextView description = (TextView) activity.findViewById(R.id.text_mission_description);
        assertTrue(description != null);
        assertEquals(missions.get(0).getDescription(), description.getText());
    }
}