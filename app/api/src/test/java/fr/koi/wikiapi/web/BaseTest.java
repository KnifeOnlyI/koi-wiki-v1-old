package fr.koi.wikiapi.web;

import fr.koi.wikiapi.utils.TimeProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * The base class for all tests.
 */
public class BaseTest {
    /**
     * The clock.
     */
    private final Clock clock = Clock.fixed(Instant.parse("2023-01-01T00:00:00.00Z"), ZoneId.of("UTC"));

    /**
     * Executed before all tests and spring initialization.
     */
    @BeforeAll
    static void beforeAll() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Executed before each test.
     */
    @BeforeEach
    void beforeEach() {
        TimeProvider.getInstance().setClock(this.clock);
    }

    /**
     * Get the time zone.
     *
     * @return The time zone
     */
    protected ZoneId getTimezone() {
        return TimeProvider.getInstance().getTimezone();
    }

    /**
     * Get the now zoned date time.
     *
     * @return The now zoned date time
     */
    protected ZonedDateTime getNowZonedDateTime() {
        return TimeProvider.getInstance().nowZonedDateTime();
    }
}
