package org.example.utils;

import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * 此时默认这里的事件日志是简单事件日志
 * 此时默认日志文件中trace中事件的出现顺序就是执行顺序，为了实现简单就不考虑时间戳的排序了。
 */
public class CsvReader {

    List<Trace> eventLog = new ArrayList<>();

    public static List<Event> getEventList(String csvFilePath) {

        List<Event> eventList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            line = reader.readNext();
            while ((line = reader.readNext()) != null) {
                // 处理每一行的数据
                Event event = new Event();
                event.setCaseId(line[0]);
                event.setEventId(UUID.randomUUID().toString());
                event.setActivity(line[1]);
                eventList.add(event);
            }
            return eventList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Trace> getTraceList(String csvFilePath) {
//        List<Event> eventList = CsvReader.getEventList("src/main/java/org/example/data/log.csv");
        List<Event> eventList = CsvReader.getEventList(csvFilePath);
        List<Trace> traceList = new ArrayList<>();

        Set<String> caseIdList = new HashSet<>();
        eventList.forEach(event -> caseIdList.add(event.getCaseId()));

        List<String> traceStringList = new ArrayList<>();

        caseIdList.forEach(caseId -> {

            StringBuilder traceStringBuilder = new StringBuilder();

            Trace trace = new Trace();

            List<Event> events = new ArrayList<>();

            for (Event event : eventList) {
                if (caseId.equals(event.getCaseId())) {
                    traceStringBuilder.append(event.getActivity());
                    events.add(event);
                }
            }

            String traceString = traceStringBuilder.toString();
            if (!traceStringList.contains(traceString)) {
                trace.setTraceId(caseId);
                trace.setEventList(events);
                traceList.add(trace);
                traceStringList.add(traceString);
            }
        });

        return traceList;
    }

    public static String sortString(String originalString) {
        char[] charArray = originalString.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public static void main(String[] args) {
        List<Trace> traceList = CsvReader.getTraceList("src/main/java/org/example/data/log.csv");
        for (Trace trace : traceList) {
            List<Event> eventList = trace.getEventList();
            String traceString = "";
            for (Event event : eventList) {
                traceString += event.getActivity();
            }
            System.out.println(traceString);
        }
    }
}