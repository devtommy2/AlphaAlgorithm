package org.example.utils;

import lombok.Data;

import java.util.List;

@Data
public class Trace {

    private String traceId;

    private List<Event> eventList;
}
