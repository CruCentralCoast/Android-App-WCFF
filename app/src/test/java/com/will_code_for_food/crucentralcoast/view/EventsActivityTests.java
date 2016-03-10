package com.will_code_for_food.crucentralcoast.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.TestDB;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.ArrayList;

/**
 * Created by Kayla on 3/9/2016.
 */
public class EventsActivityTests extends ActivityInstrumentationTestCase2<EventsActivity> {

    EventsActivity activity;
    ArrayList<Event> events;

    public EventsActivityTests () {
        super(EventsActivity.class);
        events = TestDB.getEvents();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testSetEvent() {
        assertNotNull(activity);

        Event testEvent = new Event("");
        activity.setEvent(testEvent);
        assertEquals(testEvent, activity.getEvent());
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
        assertEquals(events.get(0).getName(), titleView.getText());
        // Test the card date
        TextView dateView = (TextView) card.findViewById(R.id.card_date);
        assertTrue(dateView != null);
        assertEquals(events.get(0).getEventDate(), dateView.getText());
        // Test the card ride
        ImageView carView = (ImageView) card.findViewById(R.id.card_car_image);
        assertTrue(carView != null);
        assertEquals(View.VISIBLE, carView.getVisibility());
    }

    public void testSelectCard() {
        // Perform the card selection
        ListView listView = (ListView) activity.findViewById(R.id.list_cards);
        listView.performItemClick(
                listView.getAdapter().getView(0, null, null),
                0,
                listView.getAdapter().getItemId(0));

        // Test the buttons
        ImageButton calendarButton = (ImageButton) activity.findViewById(R.id.button_calendar);
        ImageButton ridesharingButton = (ImageButton) activity.findViewById(R.id.button_rideshare);
        ImageButton facebookButton = (ImageButton) activity.findViewById(R.id.button_facebook);
        ImageButton mapButton = (ImageButton) activity.findViewById(R.id.button_map);
        assertTrue(calendarButton != null);
        assertTrue(ridesharingButton != null);
        assertTrue(facebookButton != null);
        assertTrue(mapButton != null);

        // Test the event image
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_event);
        assertTrue(imageView != null);
        // Test the event location
        TextView locationLabel = (TextView) activity.findViewById(R.id.text_event_location);
        assertTrue(locationLabel != null);
        assertEquals(events.get(0).getEventLocation(), locationLabel.getText());
        // Test the event date
        TextView dateLabel = (TextView)activity.findViewById(R.id.text_event_date);
        assertTrue(dateLabel != null);
        assertEquals(events.get(0).getEventFullDate(), dateLabel.getText());
        // Test the event description
        TextView descriptionLabel = (TextView)activity.findViewById(R.id.text_event_description);
        assertTrue(descriptionLabel != null);
        assertEquals(events.get(0).getDescription(), descriptionLabel.getText());
    }
}