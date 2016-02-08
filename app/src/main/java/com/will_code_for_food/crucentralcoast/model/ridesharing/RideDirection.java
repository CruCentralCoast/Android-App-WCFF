package com.will_code_for_food.crucentralcoast.model.ridesharing;

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

    private Long leaveTimeToEvent = null;
    private Long leaveTimeFromEvent = null;

    RideDirection(final boolean hasTimeLeavingToEvent, final boolean hasTimeLeavingFromEvent, final String directionString) {
        this.hasTimeLeavingFromEvent = hasTimeLeavingFromEvent;
        this.hasTimeLeavingToEvent = hasTimeLeavingToEvent;
        this.directionString = directionString;
    }

    public boolean setLeaveTimeToEvent(Long leaveTime) {
        if (hasTimeLeavingToEvent && leaveTimeToEvent == null) {
            leaveTimeToEvent = leaveTime;
            return true;
        }
        return false;
    }

    public boolean setLeaveTimeFromEvent(Long leaveTime) {
        if (hasTimeLeavingFromEvent && leaveTimeFromEvent == null) {
            leaveTimeFromEvent = leaveTime;
            return true;
        }
        return false;
    }

    public Long getLeaveTimeToEvent() {
        return leaveTimeToEvent;
    }

    public Long getLeaveTimeFromEvent() {
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
        if (directionString.equals("from")) {
            return ONE_WAY_FROM_EVENT;
        } else if (directionString.equals("to")) {
            return ONE_WAY_TO_EVENT;
        } else {
            return TWO_WAY;
        }
    }
}
