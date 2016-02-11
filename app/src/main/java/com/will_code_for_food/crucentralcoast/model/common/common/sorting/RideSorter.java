package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;

import java.util.Comparator;

/**
 * Compares rides so that they can be sorted by similarity to a given ride.
 * Priorities are as follows:
 *      1. Sort by the closest departure date to the given ride
 *      2. In the event of a tie, sort by closest distance
 *      3. In the event of a tie, sort by number of seats remaining (fewest remaining first)
 *
 *      Note: If the time's in question are less than an hour different from the given ride,
 *      it sorts by distance first, then time (to convenience the driver)
 */
public class RideSorter implements Comparator<Ride> {
    private final Ride givenRide;
    private static final int HOUR = 3600000; // TODO verify

    public RideSorter(final Ride ride) {
        this.givenRide = ride;
    }

    public int compare(Ride r1, Ride r2) {
        Long time1 = getTimeDiffFromGiven(r1);
        Long time2 = getTimeDiffFromGiven(r2);
        int comp;

        if (time1 < HOUR && time2 < HOUR) {
            // sort by distance first
            comp = getDistanceFromGiven(r1).compareTo(getDistanceFromGiven(r2));
            if (comp == 0) {
                comp = getTimeDiffFromGiven(r1).compareTo(getTimeDiffFromGiven(r2));
            }
        } else {
            // sort by closest time first
            comp = getTimeDiffFromGiven(r1).compareTo(getTimeDiffFromGiven(r2));
            if (comp == 0) {
                comp = getDistanceFromGiven(r1).compareTo(getDistanceFromGiven(r2));
            }
        }
        if (comp == 0) {
            comp = ((Integer)r1.getNumAvailableSeats()).compareTo(r2.getNumAvailableSeats());
        }
        return comp;
    }

    private Integer getDistanceFromGiven(Ride ride) {
        // TODO calculate distance between ride locations (without violating Google's TOS)
        return 0;
    }

    private Long getTimeDiffFromGiven(Ride ride) {
        long diff = 0;
        if (ride.isToEvent()) {
            diff += Math.abs(ride.getLeaveTimeToEvent() - givenRide.getLeaveTimeToEvent());
        }
        if (ride.isFromEvent()) {
            diff += Math.abs(ride.getLeaveTimeFromEvent() - givenRide.getLeaveTimeFromEvent());
        }
        return diff;
    }
}
