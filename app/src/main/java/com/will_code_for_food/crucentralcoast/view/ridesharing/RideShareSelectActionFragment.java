package com.will_code_for_food.crucentralcoast.view.ridesharing;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Event;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by MasonJStevenson on 2/1/2016.
 */
public class RideShareSelectActionFragment extends CruFragment {
    private Button btnNeedRide;
    private Button btnCanDrive;
    private ImageView imageView;
    private Event event;
    private TextView dateText;
    private Activity currentActivity;

    public static final String TITLE = "Ridesharing";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        btnNeedRide = (Button) fragmentView.findViewById(R.id.btn_need_a_ride);
        btnCanDrive = (Button) fragmentView.findViewById(R.id.btn_i_can_drive);
        imageView = (ImageView) fragmentView.findViewById(R.id.rideshare_select_action_image);
        event = EventsActivity.getEvent();
        dateText = (TextView) fragmentView.findViewById(R.id.ridesharing_select_action_date);
        currentActivity = (Activity) EventsActivity.context;

        loadImage();
        initButtons();
        dateText.setText(event.getEventFullDate());

        return fragmentView;
    }

    private void loadImage() {
        if (event.getImage() != null && event.getImage() != "") {
            Picasso.with(currentActivity).load(event.getImage()).fit().into(imageView);
        }
    }

    private void initButtons() {
        btnNeedRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent().loadFragmentById(R.layout.fragment_ride_list, "Rides", new RidesFragment(), getParent());
                //getParent().loadFragmentById(R.layout.fragment_ridesharing_rider_form, "Rider Form", new RideShareRiderFormFragment(), getParent());

            }
        });

        btnCanDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch with can-drive form
                getParent().loadFragmentById(R.layout.fragment_ridesharing_driver_form,
                        Util.getString(R.string.ridesharing_driver_form_title),
                        new RideShareDriverFormFragment(), getParent());
            }
        });
    }

}