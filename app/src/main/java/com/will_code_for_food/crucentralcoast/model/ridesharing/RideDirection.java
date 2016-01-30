package com.will_code_for_food.crucentralcoast.model.ridesharing;

/**
 * Created by Gavin on 1/24/2016.
 */
public enum RideDirection {
    ONE_WAY_TO_EVENT(true, false),
    ONE_WAY_FROM_EVENT(false, true),
    TWO_WAY(true, true);

    private final boolean hasTimeLeavingToEvent;
    private final boolean hasTimeLeavingFromEvent;

    private Long leaveTimeToEvent = null;
    private Long leaveTimeFromEvent = null;

    RideDirection(final boolean hasTimeLeavingToEvent, final boolean hasTimeLeavingFromEvent) {
        this.hasTimeLeavingFromEvent = hasTimeLeavingFromEvent;
        this.hasTimeLeavingToEvent = hasTimeLeavingToEvent;
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

    public static String toString(RideDirection dir) {
        switch (dir) {
            case ONE_WAY_FROM_EVENT:
                return "from";
            case ONE_WAY_TO_EVENT:
                return "to";
            case TWO_WAY:
                return "both";
            default:
                return "both";
        }
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
}
