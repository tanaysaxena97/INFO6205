package edu.neu.coe.info6205.sort.par;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        trials();
    }

    public static void trials() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(Paths.get("assignment_reports",
                    "assignment5_Tanay_Saxena", "par_sort.csv").toString());
            writer.write("size,thread_count,cutoff,time\n");
            Random random = new Random();
            for (int pw = 18; pw <= 24; pw++) {
                int size = 1<<pw;
                int[] array = new int[size];
                for (int p = 1; p <= 16; p *= 2) {
                    ParSort.setCustomParallelism(p);
                    for (int j = 50; j < 100; j += 5) {
                        ParSort.cutoff = 10000 * (j + 1);
                        double time;
                        long startTime = System.currentTimeMillis();
                        for (int t = 0; t < 10; t++) {
                            for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                            ParSort.sort(array, 0, array.length);
                        }
                        long endTime = System.currentTimeMillis();
                        time = (double) (endTime - startTime) / 10;
                        writer.write(size + "," + p + "," + ParSort.cutoff + "," + time + "\n");
                        System.out.println("cutoff：" + ParSort.cutoff + "\t\tTime:" + time + "ms");
                    }
                }
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
