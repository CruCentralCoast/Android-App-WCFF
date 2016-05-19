package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Compares rides so that they can be sorted by similarity to a given Date
 */
public class RideSorter {

    public static List<Ride> sortRideList(final List<Ride> list, final Date givenTime) {
        Collections.sort(list, new RideComparator(givenTime));
        return list;
    }

    private static class RideComparator implements Comparator<Ride> {
        private final Date given;

        public RideComparator(final Date given) {
            this.given = given;
        }

        public int compare(Ride r1, Ride r2) {
            return getTimeDiffFromGiven(r1, given).compareTo(getTimeDiffFromGiven(r2, given));
            // switch these lines to reverse the order
//            return getTimeDiffFromGiven(r2, given).compareTo(getTimeDiffFromGiven(r1, given));
        }

        private Long getTimeDiffFromGiven(final Ride ride, final Date given) {
            if (ride.isToEvent() || ride.isTwoWay()) {
                return Math.abs(given.getTime() - ride.getLeaveTimeToEvent().getTimeInMillis());
            }
            else {
                return Math.abs(given.getTime() - ride.getLeaveTimeFromEvent().getTimeInMillis());
            }
        }
    }
}
