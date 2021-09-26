/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for (int i = from + 1; i < to; i++)
            for (int j = i; j > from && helper.less(xs[j], xs[j-1]); j--)
                helper.swap(xs, j - 1, j);
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    private Integer[] generateArr(int n) {
        Random random = new Random();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt();
        }
        return a;
    }

    private void trials() {
        try {
            FileWriter writer = new FileWriter("assignment_reports\\assignment2_Tanay_Saxena\\insertion_sort.csv");
            writer.write("n,original,reverseOrdered,sorted,partiallyOrdered\n");
            for (int i = 200; i < 64000; i *= 2) {
//            create 4 copies of a random array of length i with different order
                Integer[] originalArray = generateArr(i);

                Integer[] sortedArray = originalArray.clone();
                (new InsertionSort<Integer>()).sort(sortedArray, 0, sortedArray.length);

                Integer[] reversedArray = sortedArray.clone();
                Collections.reverse(Arrays.asList(reversedArray));

                Integer[] partiallyOrderedArray = originalArray.clone();
                (new InsertionSort<Integer>()).sort(partiallyOrderedArray, (int) (0.6 * partiallyOrderedArray.length),
                        partiallyOrderedArray.length);

                UnaryOperator<Integer[]> pre = orig -> {return orig.clone();};
                Consumer<Integer[]> fun = orig -> (new InsertionSort<Integer>()).sort( orig, 0, orig.length);
                Benchmark_Timer<Integer[]> benchmarkTimer = new Benchmark_Timer<Integer[]>("Insertion Sort",
                        pre, fun, null);

                double a = benchmarkTimer.run(originalArray, 30);
                double b = benchmarkTimer.run(reversedArray, 30);
                double c = benchmarkTimer.run(sortedArray, 30);
                double d = benchmarkTimer.run(partiallyOrderedArray, 30);
                System.out.println(i + "," + a
                        + "," + b
                        + "," + c
                        + "," + d );
                writer.write(i + "," + a + "," + b + "," + c + "," + d + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        (new InsertionSort<Integer>()).trials();
    }
}
