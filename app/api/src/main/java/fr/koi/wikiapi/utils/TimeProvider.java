package fr.koi.wikiapi.utils;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The time provider.
 */
public final class TimeProvider {
    /**
     * The unique instance.
     */
    private static TimeProvider instance = null;

    /**
     * The default clock.
     */
    private final Clock defaultClock = Clock.systemDefaultZone();

    /**
     * The currently defined clock.
     */
    private Clock clock = defaultClock;

    /**
     * Hidden constructor.
     */
    private TimeProvider() {
    }

    /**
     * Get the unique instance.
     *
     * @return The unique instance
     */
    public static TimeProvider getInstance() {
        if (instance == null) {
            instance = new TimeProvider();
        }

        return instance;
    }

    /**
     * Set the clock.
     *
     * @param value The new clock
     */
    public void setClock(final Clock value) {
        this.clock = value;
    }

    /**
     * Get the timezone of current clock.
     *
     * @return The timezone of current clock.
     */
    public ZoneId getTimezone() {
        return this.clock.getZone();
    }

    /**
     * Get the ZonedDateTime.now(clock) using the currently defined clock.
     *
     * @return The calculated zoned date time
     */
    public ZonedDateTime nowZonedDateTime() {
        return ZonedDateTime.now(this.clock);
    }
}
