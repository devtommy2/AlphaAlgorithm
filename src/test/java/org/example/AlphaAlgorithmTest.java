package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.utils.CsvReader;
import org.example.utils.Trace;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class AlphaAlgorithmTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AlphaAlgorithmTest(String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( AlphaAlgorithmTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue( true );
    }

    public void testCheckIsInHashMapMethod() {

        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        Set<String> s11 = new HashSet<>();
        Set<String> s22 = new HashSet<>();

        s1.add("a");
        s2.add("b");

        s11.add("b");
        s22.add("a");

        List<Set<String>> arr = new ArrayList<>();
        List<Set<String>> arr1 = new ArrayList<>();
        arr.add(s1);
        arr.add(s2);
        arr1.add(s11);
        arr1.add(s22);

        HashMap<List<Set<String>>, Integer> hashMap = new HashMap<>();

        hashMap.put(arr, 1);

        System.out.println(AlphaAlgorithm.checkIsInHashMap(arr1, hashMap));

        System.out.println(hashMap);
        System.out.println(arr1);
    }

    public void testAddValToHashMap() {
        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        Set<String> s11 = new HashSet<>();
        Set<String> s22 = new HashSet<>();

        s1.add("a");
        s2.add("b");

        s11.add("b");
        s22.add("a");

        List<Set<String>> arr = new ArrayList<>();
        List<Set<String>> arr1 = new ArrayList<>();
        arr.add(s1);
        arr.add(s2);
        arr1.add(s11);
        arr1.add(s22);

        HashMap<List<Set<String>>, Integer> hashMap = new HashMap<>();

        hashMap.put(arr, 1);

        System.out.println(AlphaAlgorithm.getHashMapValue(arr1, hashMap));
    }

    public void testGetX_L() {
//        String eventLogFilePath = "src/main/java/org/example/data/log.csv";
        String eventLogFilePath = "src/main/java/org/example/data/log1.csv";
        List<Trace> traceList = CsvReader.getTraceList(eventLogFilePath);

        HashMap<String, List<List<Set<String>>>> xL = AlphaAlgorithm.getX_L(traceList);

        System.out.println(xL);


    }

    public void testGetY_L() {
//        String eventLogFilePath = "src/main/java/org/example/data/log.csv";
        String eventLogFilePath1 = "src/main/java/org/example/data/log1.csv";
        List<Trace> traceList = CsvReader.getTraceList(eventLogFilePath1);
        System.out.println(traceList);
        List<List<Set<String>>> yL = AlphaAlgorithm.getY_L(traceList);
        System.out.println(yL);
    }

    public void testGetPlaceAndF() {
        String eventLogFilePath = "src/main/java/org/example/data/log.csv";
//        String eventLogFilePath1 = "src/main/java/org/example/data/log1.csv";
        List<Trace> traceList = CsvReader.getTraceList(eventLogFilePath);
        System.out.println(traceList);
        List<List<Set<String>>> yL = AlphaAlgorithm.getY_L(traceList);
//        System.out.println(yL);


    }

    public void testContainsAndContainsAll() {
        List<Set<String>> setList = new ArrayList<>();
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        Set<String> set3 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        set1.add("c");
        set2.add("a");
        set2.add("b");
        set3.add("a");
        set3.add("b");

//        setList.add(set1);
//        setList.add(set2);
//        setList.add(set3);
//        System.out.println(setList);
//        System.out.println(set2);
        System.out.println(set1);
        System.out.println(set2);
        System.out.println(set1.contains(set2));
    }

    public void testCheckHasDirectRelation() {
        List<List<Set<String>>> template = new ArrayList<>();
        Set<String> templateSet1 = new HashSet<>();
        Set<String> templateSet11 = new HashSet<>();
        templateSet1.add("a");
        templateSet11.add("b");

        Set<String> templateSet2 = new HashSet<>();
        Set<String> templateSet22 = new HashSet<>();
        templateSet2.add("c");
        templateSet22.add("d");


        List<Set<String>> templateSetList1 = new ArrayList<>();
        List<Set<String>> templateSetList2 = new ArrayList<>();

        templateSetList1.add(templateSet1);
        templateSetList1.add(templateSet11);

        templateSetList2.add(templateSet2);
        templateSetList2.add(templateSet22);

        template.add(templateSetList1);
        template.add(templateSetList2);

        System.out.println(template);

        List<Set<String>> testTuple = new ArrayList<>();
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        testTuple.add(set1);
        testTuple.add(set2);

        set1.add("a");
//        set1.add("b");
        set1.add("c");

        set2.add("b");
        set2.add("d");

        boolean b = AlphaAlgorithm.checkHasDirectRelation(testTuple, template);
        System.out.println(b);
    }

    public void testConvertSet2Array() {
        List<Set<String>> tuple1 = new ArrayList<>();
        List<Set<String>> tuple2 = new ArrayList<>();

        Set<String> set1 = new HashSet<>();
        Set<String> set11 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        Set<String> set22 = new HashSet<>();

        set1.add("a");
        set11.add("b");
        set2.add("b");
        set22.add("a");

        tuple1.add(set1);
        tuple1.add(set11);
        tuple2.add(set2);
        tuple2.add(set22);

        boolean b = AlphaAlgorithm.checkIsPalindromicString(tuple1, tuple2);
        System.out.println(b);
    }
}
