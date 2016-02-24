package com.will_code_for_food.crucentralcoast.model.ridesharing;

import java.util.Calendar;

/**
 * Created by Gavin on 1/24/2016.
 */
public enum RideDirection {
    ONE_WAY_TO_EVENT(true, false, "to"),
    ONE_WAY_FROM_EVENT(false, true, "from"),
    TWO_WAY(true, true, "both");

    private final boolean hasTimeLeavingToEvent;
    private final boolean hasTimeLeavingFromEvent;
    private final String directionString;

    private Calendar leaveTimeToEvent = null;
    private Calendar leaveTimeFromEvent = null;

    RideDirection(final boolean hasTimeLeavingToEvent, final boolean hasTimeLeavingFromEvent, final String directionString) {
        this.hasTimeLeavingFromEvent = hasTimeLeavingFromEvent;
        this.hasTimeLeavingToEvent = hasTimeLeavingToEvent;
        this.directionString = directionString;
    }

    public boolean setLeaveTimeToEvent(final Calendar leaveTime) {
        if (hasTimeLeavingToEvent && leaveTimeToEvent == null) {
            leaveTimeToEvent = leaveTime;
            return true;
        }
        return false;
    }

    public boolean setLeaveTimeFromEvent(final Calendar leaveTime) {
        if (hasTimeLeavingFromEvent && leaveTimeFromEvent == null) {
            leaveTimeFromEvent = leaveTime;
            return true;
        }
        return false;
    }

    public Calendar getLeaveTimeToEvent() {
        return leaveTimeToEvent;
    }

    public Calendar getLeaveTimeFromEvent() {
        return leaveTimeFromEvent;
    }

    public boolean hasTimeLeavingToEvent() {
        return hasTimeLeavingToEvent;
    }

    public boolean hasTimeLeavingFromEvent() {
        return hasTimeLeavingFromEvent;
    }

    public String toString() { return directionString; }

    public static RideDirection fromString (String directionString){
        if (directionString != null) {
            if (directionString.equals("from")) {
                return RideDirection.ONE_WAY_FROM_EVENT;
            } else if (directionString.equals("to")) {
                return RideDirection.ONE_WAY_TO_EVENT;
            } else {
                return RideDirection.TWO_WAY;
            }
        } else {
            return null;
        }
    }
}
