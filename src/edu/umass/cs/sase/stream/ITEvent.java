package edu.umass.cs.sase.stream;

public interface ITEvent extends Event {
    /**
     *
     * return duration time
     */

    public int getDurationTime();

    /**
     *
     * return end timestamp
     */

    public int getEndTimestamp();
}
