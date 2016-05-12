package com.will_code_for_food.crucentralcoast.view.ridesharing;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.tasks.Filterer;

import java.util.ArrayList;

/**
 * Filters out rides the user is already driving/riding
 */
public class MyRideFilterer implements Filterer {
    private final String phoneNum;

    public MyRideFilterer(final String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public Content filter(final Content content) {
        ArrayList<Ride> list = new ArrayList<>();
        for (Object obj : content.getObjects()) {
            if (obj instanceof Ride) {
                Ride ride = (Ride) obj;
                Passenger passenger = DBObjectLoader.getPassenger(phoneNum);
                if (!ride.getDriverNumber().equals(phoneNum)
                        && !ride.hasPassenger(passenger.getId())) {
                    list.add(ride);
                }
            }
        }
        return new Content<>(list, content.getType());
    }
}
