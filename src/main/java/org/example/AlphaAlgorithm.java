package org.example;

import org.example.utils.Event;
import org.example.utils.Trace;

import java.util.*;

public class AlphaAlgorithm {

    public static Set<String> getT_L(List<Trace> traceList) {
        Set<String> T_L = new HashSet<>();
        for (Trace trace : traceList) {
            for (Event event : trace.getEventList()) {
                T_L.add(event.getActivity());
            }
        }
        return T_L;
    }

    public static Set<String> getT_I(List<Trace> traceList) {
        Set<String> T_I = new HashSet<>();
        for (Trace trace : traceList) {
            T_I.add(trace.getEventList().get(0).getActivity());
        }
        return T_I;
    }

    public static Set<String> getT_O(List<Trace> traceList) {
        Set<String> T_O = new HashSet<>();
        for (Trace trace : traceList) {
            T_O.add(trace.getEventList().get(trace.getEventList().size() - 1).getActivity());
        }
        return T_O;
    }

    public static List<List<String>> formatTraceListToSetList(List<Trace> traceList) {
        List<List<String>> formatTrace = new ArrayList<>();

        for (Trace trace : traceList) {
            List<String> traceSet = new ArrayList<>();
            for (Event event : trace.getEventList()) {
                traceSet.add(event.getActivity());
            }
            formatTrace.add(traceSet);
        }

        return formatTrace;
    }

    public static HashMap<String, List<List<Set<String>>>> getX_L(List<Trace> traceList) {

        List<List<String>> traceSetList = formatTraceListToSetList(traceList);

        // 将trace形式转化为 >_w 逻辑关系
        List<List<Set<String>>> ans = new ArrayList<>();
        for (List<String> trace : traceSetList) {
            for (int i = 0; i < trace.size() - 1; i ++ ) {
                Set<String> set1 = new HashSet<>();
                Set<String> set2 = new HashSet<>();
                List<Set<String>> tuple = new ArrayList<>();

                set1.add(trace.get(i));
                set2.add(trace.get(i + 1));

                tuple.add(set1);
                tuple.add(set2);

                ans.add(tuple);
            }
        }

        List<List<Set<String>>> finalTupleList = new ArrayList<>();
        List<List<Set<String>>> allTupleList = new ArrayList<>();
        LinkedList<List<Set<String>>> multiTupleList = new LinkedList<>();

        // 通过 >w 关系 推导出 -->w 关系
        for (int i = 0; i < ans.size(); i ++ ) {
            boolean isPalindromicString = false;
            for (int j = 0; j < ans.size(); j ++ ) {
                if (checkIsPalindromicString(ans.get(i), ans.get(j))) {
                    isPalindromicString = true;
                    break;
                }
            }

            if (!isPalindromicString) {
                finalTupleList.add(ans.get(i));
            }

            allTupleList.add(ans.get(i));
        }

        // 对finalTupleList 进行去重
        List<List<Set<String>>> finalTupleList_ = new ArrayList<>();
        for (List<Set<String>> setList : finalTupleList) {
            if (!finalTupleList_.contains(setList)) {
                finalTupleList_.add(setList);
            }
        }
        finalTupleList = finalTupleList_;

//        System.out.println("***********************************************");
//        System.out.println(finalTupleList);

        for (int i = 0; i < finalTupleList.size() - 1; i ++ ) {

            // 存放每次内循环开始时的组合Tuple，这个Tuple可能不合法
            List<Set<String>> tmpTuple = new ArrayList<>();

            // 存放当前已经合法的组合结果Tuple
            List<Set<String>> tmpTuple1 = new ArrayList<>();
            for (int j = i + 1; j < finalTupleList.size(); j ++ ) {

                if (tmpTuple1.isEmpty()) {
                    List<Set<String>> tuple1 = finalTupleList.get(i);
                    List<Set<String>> tuple2 = finalTupleList.get(j);
                    // try to merge tuple1 and tuple2
                    Set<String> combine1 = new HashSet<>(tuple1.get(0));
                    combine1.addAll(tuple2.get(0));
                    Set<String> combine2 = new HashSet<>(tuple1.get(1));
                    combine2.addAll(tuple2.get(1));
                    tmpTuple.add(combine1);
                    tmpTuple.add(combine2);
                } else {
                    // 内循环中的下一个Tuple
                    List<Set<String>> tuple3 = finalTupleList.get(j);
                    Set<String> combine1 = new HashSet<>(tmpTuple1.get(0));
                    combine1.addAll(tuple3.get(0));
                    Set<String> combine2 = new HashSet<>(tmpTuple1.get(1));
                    combine2.addAll(tuple3.get(1));
                    tmpTuple.add(combine1);
                    tmpTuple.add(combine2);
                }

                // 检查 combine1 and combine2 中是否含有直接关联关系
                if (!checkHasDirectRelation(tmpTuple, allTupleList)) {
                    // 没有直接关联关系，拿着这个 tmpTuple参与到下一轮内循环中
                    tmpTuple1.clear();
                    tmpTuple1.addAll(tmpTuple);
                } else {
                    // 有直接因果关系
                    // 如果tmpTuple1非空的话，将tmpTuple1 添加到 MultiTuple中
                    if (!tmpTuple1.isEmpty()) {
                        multiTupleList.add(new ArrayList<>(tmpTuple1));
                        tmpTuple1.clear();
                    }
                }

                if (j == finalTupleList.size() - 1 && !tmpTuple1.isEmpty()) {
                    multiTupleList.add(new ArrayList<>(tmpTuple1));
                }

                tmpTuple.clear();
            }
        }

        HashMap<String, List<List<Set<String>>>> finalTupleListAndMultiTupleList = new HashMap<>();
        finalTupleListAndMultiTupleList.put("finalTupleList", finalTupleList);
        finalTupleListAndMultiTupleList.put("multiTupleList", multiTupleList);

        return finalTupleListAndMultiTupleList;
    }

    public static boolean checkIsPalindromicString(List<Set<String>> tuple1, List<Set<String>> tuple2) {
        String string = (String) tuple1.get(0).toArray()[0]
                + tuple1.get(1).toArray()[0] + tuple2.get(0).toArray()[0]
                + tuple2.get(1).toArray()[0];
        StringBuilder stringBuilder = new StringBuilder(string);
        return stringBuilder.reverse().toString().equals(string);
    }

    public static List<List<Set<String>>> getY_L(List<Trace> traceList) {
        HashMap<String, List<List<Set<String>>>> xL = getX_L(traceList);
        List<List<Set<String>>> finalTupleList = xL.get("finalTupleList");
        List<List<Set<String>>> multiTupleList = xL.get("multiTupleList");

        List<List<Set<String>>> tmpFinalTupleList = new ArrayList<>();
        for (List<Set<String>> tuple : multiTupleList) {
            Set<String> set0 = tuple.get(0);
            Set<String> set1 = tuple.get(1);
            for (String s0 : set0) {
                for (String s1 : set1) {

                    List<Set<String>> tmpTuple = new ArrayList<>();

                    Set<String> tmpSet0 = new HashSet<>();
                    Set<String> tmpSet1 = new HashSet<>();

                    tmpTuple.add(tmpSet0);
                    tmpTuple.add(tmpSet1);

                    tmpSet0.add(s0);
                    tmpSet1.add(s1);

                    tmpFinalTupleList.add(tmpTuple);
                }
            }
        }

        List<List<Set<String>>> ans = new ArrayList<>();
        for (List<Set<String>> setList : finalTupleList) {
            if (!tmpFinalTupleList.contains(setList)) {
                ans.add(setList);
            }
        }

        ans.addAll(multiTupleList);

        return ans;
    }

    public static void createPlaceAndF(List<Trace> traceList) {
        List<List<Set<String>>> yL = getY_L(traceList);
        Set<String> tI = getT_I(traceList);
        Set<String> tO = getT_O(traceList);
    }

    public static boolean checkHasDirectRelation(List<Set<String>> tuple, List<List<Set<String>>> templateTuple) {
        // 分别检查Tuple的两个元素，检查各自的内部是否存在直接因果关系
        Set<String> set = tuple.get(0);
        Set<String> set1 = tuple.get(1);

        // 如果两个set的大小都是1，说明各自内部肯定没有直接因果关系
        if (set.size() == 1 && set1.size() == 1) {
            return false;
        }

        // preparation
        List<Set<String>> directRelationSets = new ArrayList<>();
        for (List<Set<String>> setList : templateTuple) {
            Set<String> tmpSet = new HashSet<>();
            tmpSet.addAll(setList.get(0));
            tmpSet.addAll(setList.get(1));
            directRelationSets.add(tmpSet);
        }

        // 两个Set的大小大于1
        // todo 1 先检查set
        for (Set<String> directRelationSet : directRelationSets) {
            if (set.containsAll(directRelationSet)) {
                return true;
            }
        }

        // todo 2 检查 set1
        for (Set<String> directRelationSet : directRelationSets) {
            if (set1.containsAll(directRelationSet)) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkIsInHashMap(List<Set<String>> key, HashMap<List<Set<String>>, Integer> hashMap) {
        for (Map.Entry<List<Set<String>>, Integer> listIntegerEntry : hashMap.entrySet()) {
            Set<Set<String>> key1 = new HashSet<>(listIntegerEntry.getKey());
            Set<Set<String>> key_ = new HashSet<>(key);
            if (key1.equals(key_)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<List<Set<String>>, Integer> addValToHashMap(List<Set<String>> key, Integer value, HashMap<List<Set<String>>, Integer> hashMap) {
        for (Map.Entry<List<Set<String>>, Integer> listIntegerEntry : hashMap.entrySet()) {
            Integer value1 = listIntegerEntry.getValue();
            List<Set<String>> key1 = listIntegerEntry.getKey();

            Set<Set<String>> key1Set = new HashSet<>(key1);
            Set<Set<String>> keySet = new HashSet<>(key);

            if (keySet.equals(key1Set)) {
                hashMap.put(key1, value);
                return hashMap;
            }
        }

        hashMap.put(key, value);
        return hashMap;
    }

    public static Integer getHashMapValue(List<Set<String>> key, HashMap<List<Set<String>>, Integer> hashMap) {

        for (Map.Entry<List<Set<String>>, Integer> listIntegerEntry : hashMap.entrySet()) {
            Integer value = listIntegerEntry.getValue();
            List<Set<String>> key1 = listIntegerEntry.getKey();

            Set<Set<String>> key1Set = new HashSet<>(key1);
            Set<Set<String>> keySet = new HashSet<>(key);

            if (key1Set.equals(keySet)) {
                return value;
            }
        }
        return 0;
    }
}
