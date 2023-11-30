package org.example;

import lombok.Data;

import java.util.List;


/**
 * Hello world!
 *
 */
public class App {

    public static void eventLogFileReader() {

    }

    public static void main( String[] args ) {

    }

    /**
     * Trace
     */
    @Data
    class Trace {
        private String traceId;
        private List<Event> events;
    }

    @Data
    class Event {
        private String activityName;
        private String eventId;
    }
}
