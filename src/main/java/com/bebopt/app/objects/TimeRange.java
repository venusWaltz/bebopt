package com.bebopt.app.objects;

/**
 * Represents different time ranges; used in requests sent to the Spotify API.
 */
public enum TimeRange {
    SHORT_TERM("short_term"),
    MEDIUM_TERM("medium_term"),
    LONG_TERM("long_term");

    private final String timeRange;

    /**
     * Constructs a {@code TimeRange} enum with the specified time range string.
     * 
     * @param timeRange The string representing the time range.
     */
    TimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * Returns the string representation of the time range.
     * 
     * @return The string representing the time range.
     */
    public String getTimeRange() {
        return timeRange;
    }
}
