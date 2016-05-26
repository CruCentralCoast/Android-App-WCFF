package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.RideSorter;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.SortMethod;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by ShelliCrispen on 4/11/16.
 */
public class RideAdapter extends CardAdapter {
    Ride current;
    Context context;

    public RideAdapter(Context context, int resource, Content content) {
        super(context, resource, content);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        current = (Ride) cards.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_ride_card, parent, false);
        }

        setDriverName(convertView);
        setLeaveDate(convertView);
        setLeaveLocation(convertView);
        setSeatsLeft(convertView);

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
        TextView seatsLeft = (TextView) hold.findViewById(R.id.card_ride_seats_left);
        String text = "";

        if (current.isToEvent()) {
            text += current.getNumAvailableSeatsToEvent();
            text += " " + Util.getString(R.string.ridesharing_to);
        }
        if (current.isTwoWay()) {
            text += " - ";
        }
        if (current.isFromEvent()) {
            text += current.getNumAvailableSeatsFromEvent();
            text += " " + Util.getString(R.string.ridesharing_from);
        }

        seatsLeft.setText(text);
    }

    public void sortByNewest() {
        Collections.sort(cards, new HorribleDateComparator(SortMethod.ASCENDING));
        this.notifyDataSetChanged();
    }

    public void sortByOldest() {
        Collections.sort(cards, new HorribleDateComparator(SortMethod.DESCENDING));
        this.notifyDataSetChanged();
    }

    public void sortByTime(Date date){
//        sortRideList(cards, date);
        cards = new Content<>(RideSorter.sortRideList(cards.getObjects(), date), cards.getType());
        this.notifyDataSetChanged();
    }


    public void search(final String phrase) {
        Collections.sort(cards, new Comparator<DatabaseObject>() {
            private int diff(String phrase, String query) {
                int diff = phrase.length() == query.length() ? 0 : 1;
                for (int i = 0; i < (phrase.toLowerCase().length() < query.toLowerCase().length()
                        ? phrase.length() : query.length()); i++) {
                    if (phrase.charAt(i) != query.charAt(i)) {
                        ++diff;
                    }
                }
                return diff;
            }
            @Override
            public int compare(DatabaseObject lhs, DatabaseObject rhs) {
                return Integer.valueOf(diff(phrase, ((Ride)lhs).getDriverName())).compareTo(
                        (diff(phrase, ((Ride)rhs).getDriverName())));
            }
        });
        this.notifyDataSetChanged();
    }

    private class HorribleDateComparator implements Comparator<DatabaseObject> {
        private SortMethod method;

        public HorribleDateComparator(SortMethod method) {
            this.method = method;
        }
        public Date getDate(String time, String day) {
            // this is awful, I know this is awful, but it's late and date formatters are annoying
            Date date = new Date();
            String[] dayInfo = day.split("/");
            date.setMonth(Integer.parseInt(dayInfo[0]));
            date.setDate(Integer.parseInt(dayInfo[1]));
            date.setYear(Integer.parseInt("20" + dayInfo[2]));
            String[] timeInfo = time.substring(0, time.length() - 2).trim().split(":");
            date.setHours(Integer.parseInt(timeInfo[0]));
            date.setMinutes(Integer.parseInt(timeInfo[1]));
            return date;
        }
        @Override
        public int compare(DatabaseObject lhs, DatabaseObject rhs) {
            if (method == SortMethod.ASCENDING) {
                return getDate(((Ride) rhs).getLeaveTime(), ((Ride) rhs).getLeaveDate()).compareTo(
                        getDate(((Ride) lhs).getLeaveTime(), ((Ride) lhs).getLeaveDate())
                );
            } else {
                return getDate(((Ride) lhs).getLeaveTime(), ((Ride) lhs).getLeaveDate()).compareTo(
                        getDate(((Ride) rhs).getLeaveTime(), ((Ride) rhs).getLeaveDate())
                );
            }
        }
    }
}
