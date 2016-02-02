package com.will_code_for_food.crucentralcoast.view.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.MainActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.RideShareActivity;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;

import java.util.List;

/**
 * Created by Kayla on 1/31/2016.
 */
public class RideCardFactory implements CardFragmentFactory {

    List<Ride> cards;

    @Override
    public boolean include(DatabaseObject object) {
        //TODO: filter based on seats left and time leaving
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(List cardObjects) {
        return new RideAdapter(RideShareActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(MainActivity currentActivity, List myDBObjects) {
        return null;
    }

    private class RideAdapter extends ArrayAdapter<Ride> {

        public RideAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            cards = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Ride current = cards.get(position);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View hold = inflater.inflate(R.layout.fragment_ride_card, parent, false);

            TextView driverName = (TextView) hold.findViewById(R.id.card_driver_name);
            driverName.setText(current.getDriverName());

            TextView leaveDate = (TextView) hold.findViewById(R.id.card_ride_leave_date);
            String text = String.format(Util.getString(R.string.ridesharing_leaving_date),
                    current.getLeaveTime(), current.getLeaveDate());
            leaveDate.setText(text);

            TextView leaveLocation = (TextView) hold.findViewById(R.id.card_ride_leave_location);
            // Currently just the country in location in database...
            text = String.format(Util.getString(R.string.ridesharing_leaving_location),
                    "the PAC circle"); // use dummy value
            leaveLocation.setText(text);

            TextView seatsLeft = (TextView) hold.findViewById(R.id.card_ride_seats_left);
            int seats = current.getNumSeats();
            if (seats == 1) {
                text = String.format(Util.getString(R.string.ridesharing_seat_left), seats);
            } else {
                text = String.format(Util.getString(R.string.ridesharing_seats_left), seats);
            }
            seatsLeft.setText(text);

            return hold;
        }
    }
}
