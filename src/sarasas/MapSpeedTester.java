package sarasas;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class MapSpeedTester
{
    public static void main(String[] args)
    {
        int size = Integer.parseInt(args[0]);
        float ratioInsertLookup = Float.parseFloat(args[1]);
//        float ratioInsertDelete = Float.parseFloat(args[2]);

        MapSpeedTester tester = new MapSpeedTester();
        // warm up caches
        tester.runTests(size, ratioInsertLookup, false);
        tester.runTests(size, ratioInsertLookup, true);
    }

    private void runTests(int size, float ratioInsertLookup,
                          boolean reportResults)
    {
        Map[] mapsToTest = getTestMaps();
        for (int i = 0; i < mapsToTest.length; i++)
        {
            Map map = mapsToTest[i];
            long totalTime = 0;
            for (int testRound = 0; testRound < 10000; testRound++)
            {
                long start = System.currentTimeMillis();
                testMap(map, size, ratioInsertLookup);
                long end = System.currentTimeMillis();
                totalTime += (end-start);
                map.clear();
            }

            if (reportResults)
            {
                System.out.println(map.getClass().getName() +
                                   ": "+totalTime+" ms");
            }
        }
    }

    private static void testMap(Map map, int size, float ratioInsertLookup)
    {
        for (int i = 0; i < size; i++)
        {
            map.put("Key"+i, "Value"+i);
        }

        float lookups = size/ratioInsertLookup;
        for (int i = 0; i < lookups; i++)
        {
            map.get("Key"+(i%size));
        }
    }

    private static Map[] getTestMaps()
    {
        return new Map[] {
            new ArrayListMap(false),
            new ArrayListMap(true),
            new HashMap(),
            new TreeMap(),
        };
    }
}
