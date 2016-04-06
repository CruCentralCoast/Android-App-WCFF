package com.will_code_for_food.crucentralcoast.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.view.getinvolved.GetInvolvedActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.ArrayList;

/**
 * Created by Mallika on 3/11/16.
 */
public class GetInvolvedActivityTests extends ActivityInstrumentationTestCase2<GetInvolvedActivity> {

    GetInvolvedActivity activity;
    ArrayList<MinistryTeam> teams;

    public GetInvolvedActivityTests () {
        super(GetInvolvedActivity.class);
        teams = TestDB.getMinistryTeams();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testSetEvent() {
        assertNotNull(activity);

        MinistryTeam test = new MinistryTeam(null);
        activity.setMinistryTeam(test);
        assertEquals(test, activity.getMinistryTeam());
    }

    public void testEventCards() {
        // Test the list exists
        ListView listView = (ListView) activity.findViewById(R.id.list_cards);
        assertTrue(listView != null);

        // Test the card exists
        View card = listView.getChildAt(0);
        assertTrue(card != null);

        // Test the card image
        ImageView imageView = (ImageView) card.findViewById(R.id.mt_image);
        assertTrue(imageView != null);

        // Test the card name
        TextView titleView = (TextView) card.findViewById(R.id.mt_text);
        assertTrue(titleView != null);
        assertEquals(teams.get(0).getName(), titleView.getText());
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
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_ministry_team);
        assertTrue(imageView != null);

        //Test the name
        TextView titleView = (TextView) activity.findViewById(R.id.text_ministry_team_name);
        assertTrue(titleView != null);
        assertEquals(teams.get(0).getName(), titleView.getText());

        // Test the description
        TextView description = (TextView) activity.findViewById(R.id.text_ministry_team_description);
        assertTrue(description != null);
        assertEquals(teams.get(0).getDescription(), description.getText());
    }
}
