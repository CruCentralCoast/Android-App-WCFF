package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import com.will_code_for_food.crucentralcoast.model.ridesharing.RideDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Compares rides so that they can be sorted by similarity to a given ride.
 * Priorities are as follows:
 *      1. Sort by the closest departure date to the given ride
 *      2. In the event of a tie, sort by closest distance
 *
 *      Note: If the time's in question are less than an hour different from the given ride,
 *      it sorts by distance first, then time (to convenience the driver)
 *
 *      If rides aren't in the correct direction, they are filtered down
 */
public class RideSorter {

    public static List<Ride> sortRideList(final List<Ride> list, final Ride givenRide) {
        Collections.sort(list, new RideComparator(givenRide));
        if (givenRide.getDirection() != RideDirection.TWO_WAY) {
            // remove rides of the wrong type
            List<Ride> filteredList = new ArrayList<>();
            for (Ride ride : list) {
                if (givenRide.isToEvent() && ride.isToEvent()) {
                    filteredList.add(ride);
                }
                if (givenRide.isFromEvent() && ride.isFromEvent()) {
                    filteredList.add(ride);
                }
            }
            return filteredList;
        } else {
            return list;
        }
    }

    private static class RideComparator implements Comparator<Ride> {
        private static final int HOUR = 60 * 60 * 1000;
        private final Ride givenRide;

        public RideComparator(final Ride ride) {
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
}
