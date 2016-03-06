package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;

import java.util.List;

/**
 * Created by Kayla on 1/31/2016.
 */
public class RideCardFactory implements CardFragmentFactory {
    private List<Ride> cards;
    private Event event;
    private MainActivity parent;

    public RideCardFactory(MainActivity parent) {
        this.parent = parent;
        event = EventsActivity.getEvent();
    }

    @Override
    public boolean include(DatabaseObject object) {
        Ride ride = (Ride) object;

        // Filter for the event that was chosen
        //TODO: filter based on time leaving, 1/2-way, etc.
        // TODO use the RideSorter class!

        return event != null && event.getId().equals(ride.getEventId()) && (!ride.isFullFromEvent() || !ride.isFullToEvent());

    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new RideAdapter(parent,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity, final Content myDBObjects) {
        return new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride ride = (Ride) myDBObjects.get(position);
                RideShareActivity.setRide(ride);
                currentActivity.loadFragmentById(R.layout.fragment_ride_info, ride.getDriverName() + "'s Ride", new RideInfoFragment(), currentActivity);
            }
        };
    }

    private class RideAdapter extends ArrayAdapter<Ride> {

        Ride current;
        Context context;

        public RideAdapter(Context context, int resource, Content content) {
            super(context, resource, content);
            cards = content;
            this.context = context;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if (convertView == null) {
                current = cards.get(position);
                //LayoutInflater inflater = LayoutInflater.from(getContext());
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_ride_card, parent, false);

                setDriverName(convertView);
                setLeaveDate(convertView);
                setLeaveLocation(convertView);
                setSeatsLeft(convertView);
            }

            return convertView;
        }

        private void setDriverName(View hold) {
            TextView driverName = (TextView) hold.findViewById(R.id.card_driver_name);
            driverName.setText(current.getDriverName());
        }

        private void setLeaveDate(View hold) {
            String text;

            TextView leaveDate = (TextView) hold.findViewById(R.id.card_ride_leave_date);
            text = String.format(Util.getString(R.string.ridesharing_leaving_date),
                    current.getLeaveTime(), current.getLeaveDate());
            leaveDate.setText(text);
        }

        private void setLeaveLocation (View hold) {
            String text;
            String location = current.getLocation().getStreet();

            if (location == null) {
                location = "unknown";
            }

            TextView leaveLocation = (TextView) hold.findViewById(R.id.card_ride_leave_location);
            text = String.format(Util.getString(R.string.ridesharing_leaving_location), location);
            leaveLocation.setText(text);
        }

        private void setSeatsLeft(View hold) {
            String text;

            TextView seatsLeft = (TextView) hold.findViewById(R.id.card_ride_seats_left);
            // TODO this is not the way we should be calculating number of seats left
            int seats = current.getNumAvailableSeatsFromEvent() + current.getNumAvailableSeatsToEvent();
            if (seats == 1) {
                text = String.format(Util.getString(R.string.ridesharing_seat_left), seats);
            } else {
                text = String.format(Util.getString(R.string.ridesharing_seats_left), seats);
            }
            seatsLeft.setText(text);
        }
    }
}
