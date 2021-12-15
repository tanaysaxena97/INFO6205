package edu.neu.coe.info6205.pell;

public class Pell {
    public Pell() {
    }

    public long get(int n) {
        if (n < 0) throw new UnsupportedOperationException("Pell.get is not supported for negative n");
        Long[] a = new Long[3];
        a[0] = Long.valueOf(0);
        a[1] = Long.valueOf(1);
        if (n <= 1) return a[n];
        for (int i = 2; i <= n; i++) {
            a[i % 3] = 2 * a[(i + 2) % 3] + a[(i + 1) % 3];
        }
        return a[n%3];
    }
}
