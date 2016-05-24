package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 4/27/2016.
 */
public class MyPassengersFragment extends CruFragment {

    private Ride ride;
    private SwipeRefreshLayout layout;
    private Content<Passenger> passengers;
    private ListView listView;
    private PassengerAdapter adapter;

    public MyPassengersFragment(Ride myRide) {
        ride = myRide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        listView = (ListView) fragmentView.findViewById(R.id.list_cards);
        layout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.card_refresh_layout);
        passengers = new Content<>(new ArrayList<Passenger>(), ContentType.CACHED);
        adapter = new PassengerAdapter(getActivity(), R.layout.fragment_passenger_card, passengers);
        listView.setAdapter(adapter);

        loadList();
        init();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    private void init() {
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("What would you like to remove this passenger from your ride?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Passenger toRemove = passengers.get(position);
                                new DropPassenger(toRemove).execute();
                                DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT);
                                passengers.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                view.setSelected(true);
            }
        });
    }

    private void refreshList() {
        if (!DBObjectLoader.loadObjects(RetrieverSchema.RIDE, Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh passengers", Toast.LENGTH_SHORT).show();
        }
        loadList();
    }

    private void loadList() {
        passengers.clear();
        List<Passenger> allPassengers = DBObjectLoader.getPassengers();
        JsonArray passengerIDs = ride.getField(Database.JSON_KEY_RIDE_PASSENGERS).getAsJsonArray();

        for (Passenger passenger : allPassengers) {
            if (passengerIDs.contains(new JsonPrimitive(passenger.getId()))) {
                passengers.add(passenger);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private class PassengerAdapter extends CardAdapter {

        public PassengerAdapter(Context context, int resource, Content content) {
            super(context, resource, content);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Passenger current = (Passenger) cards.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_passenger_card, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.passenger_name);
            TextView number = (TextView) convertView.findViewById(R.id.passenger_number);
            name.setText(current.getName());
            number.setText(current.getPhoneNumber());

            return convertView;
        }
    }

    private class DropPassenger extends AsyncTask<Void, Void, Void> {
        Passenger thisPassenger;

        public DropPassenger(Passenger thisPassenger) {
            this.thisPassenger = thisPassenger;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestUtil.dropPassenger(ride.getId(), thisPassenger.getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getParent(), "Dropped passenger", Toast.LENGTH_SHORT).show();
        }
    }
}
