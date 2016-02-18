package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.List;

/**
 * Created by Brian on 2/15/2016.
 */
public class MyRideCardFactory implements CardFragmentFactory {
    private List<String> myRides;
    List<Ride> cards;

    public MyRideCardFactory(){
        myRides = LocalStorageIO.readList(LocalFiles.USER_RIDES);
    }

    @Override
    public boolean include(DatabaseObject object) {
        if (myRides != null && myRides.contains(object.getId()))
            return true;
        return false;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new RideAdapter(RideShareActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(MainActivity currentActivity, Content myDBObjects) {
        return null;
    }

    private class RideAdapter extends ArrayAdapter<Ride> {

        public RideAdapter(Context context, int resource, Content content) {
            super(context, resource, content.getObjects());
            cards = content.getObjects();
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
            text = String.format(Util.getString(R.string.ridesharing_leaving_location),
                    "the PAC circle"); // use dummy value for now
            leaveLocation.setText(text);

            //TODO: Make this not use the same card
            TextView seatsLeft = (TextView) hold.findViewById(R.id.card_ride_seats_left);
            List<Event> events = new SingleRetriever<Event>(RetrieverSchema.EVENT).getAll();
            for (Event e : events)
                if (e.getId().equals(current.getEventId()))
                    text = e.getName();
            seatsLeft.setText(text);

            return hold;
        }
    }
}
