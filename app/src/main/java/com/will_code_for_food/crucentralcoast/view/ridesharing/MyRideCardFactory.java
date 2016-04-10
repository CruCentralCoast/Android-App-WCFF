package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 2/15/2016.
 */
public class MyRideCardFactory implements CardFragmentFactory {
    private List<String> myRides;

    public MyRideCardFactory(){
        myRides = new ArrayList<String>();
        getMyRides();
    }

    @Override
    public boolean include(DatabaseObject object) {
        return myRides != null && myRides.contains(object.getId());
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new RideAdapter(RideShareActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity, final Content myDBObjects) {

        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride ride = (Ride) myDBObjects.get(position);

                //load ride info
                RideShareActivity.setRide(ride);
                currentActivity.loadFragmentById(R.layout.fragment_ride_info, ride.getDriverName() + "'s Ride", new RideInfoFragment(), currentActivity);
            }
        };
    }

    private void getMyRides() {
        String myPhoneNum = Util.getPhoneNum();
        ArrayList<Ride> rides = DBObjectLoader.getRides();

        for (Ride ride : rides) {
            if (isPassenger(ride, myPhoneNum) || isDriver(ride, myPhoneNum)) {
                System.out.println("*************************************************");
                myRides.add(ride.getId());
            }
        }
    }

    private boolean isPassenger(Ride ride, String phoneNum) {
        Passenger passenger = DBObjectLoader.getPassenger(phoneNum);

        return passenger != null && ride.hasPassenger(passenger.getId());
    }

    private boolean isDriver(Ride ride, String phoneNum) {
        if (ride != null) {
            Log.d("MyRideCardFactory", "comparing [" + phoneNum + "] to [" + ride.getDriverNumber() + "]");
        } else {
            Log.d("MyRideCardFactory", "ride is null");
        }
        return ride != null && ride.getDriverNumber().equals(phoneNum);
    }

    private class RideAdapter extends CardAdapter {

        public RideAdapter(Context context, int resource, Content content) {
            super(context, resource, content);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Ride current = (Ride) cards.get(position);
            
            
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.fragment_ride_card, parent, false);
            }
            
            TextView driverName = (TextView) convertView.findViewById(R.id.card_driver_name);
            driverName.setText(current.getDriverName());

            TextView leaveDate = (TextView) convertView.findViewById(R.id.card_ride_leave_date);
            String text = String.format(Util.getString(R.string.ridesharing_leaving_date),
                    current.getLeaveTime(), current.getLeaveDate());
            leaveDate.setText(text);

            TextView leaveLocation = (TextView) convertView.findViewById(R.id.card_ride_leave_location);
            String location = current.getLocation().getStreet();
            if (location == null) {
                location = Util.getString(R.string.ridesharing_unknown_location);
            }
            text = String.format(Util.getString(R.string.ridesharing_leaving_location), location);
            leaveLocation.setText(text);

            TextView event = (TextView) convertView.findViewById(R.id.card_ride_seats_left);
            List<Event> events = new SingleRetriever<Event>(RetrieverSchema.EVENT).getAll();
            for (Event e : events)
                if (e.getId().equals(current.getEventId()))
                    text = e.getName();
            event.setText(text);

            return convertView;
        }
    }
}
