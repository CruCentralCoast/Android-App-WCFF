package com.will_code_for_food.crucentralcoast.model.common.common.sorting;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.ridesharing.Ride;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Compares rides so that they can be sorted by similarity to a given Date
 */
public class RideSorter {

    public static List<DatabaseObject> sortRideList(final List<DatabaseObject> list, final Date givenTime) {
        Collections.sort(list, new RideComparator(givenTime));
        return list;
    }

    private static class RideComparator implements Comparator<DatabaseObject> {
        private final Date given;

        public RideComparator(final Date given) {
            this.given = given;
        }

        public int compare(DatabaseObject r1, DatabaseObject r2) {
            if (!(r1 instanceof Ride) || !(r2 instanceof Ride)) {
                return 0;
            }
            return getTimeDiffFromGiven((Ride) r2, given).compareTo(getTimeDiffFromGiven((Ride) r1, given));
        }

        private Long getTimeDiffFromGiven(final Ride ride, final Date given) {
            if (ride.isToEvent() || ride.isTwoWay()) {
                if (ride.getLeaveTimeToEvent() != null) {
                    return Math.abs(given.getTime() - ride.getLeaveTimeToEvent().getTime());
                } else {
                    Logger.e("Sorting Rides", "Missing leave time to event. " + ride.getLeaveTime());
                    return Long.MAX_VALUE;
                }
            }
            else {
                if (ride.getLeaveTimeFromEvent() != null) {
                    return Math.abs(given.getTime() - ride.getLeaveTimeFromEvent().getTime());
                } else {
                    Logger.e("Sorting Rides", "Missing leave time from event. " + ride.getLeaveTime());
                    return Long.MAX_VALUE;
                }
            }
        }
    }
}
