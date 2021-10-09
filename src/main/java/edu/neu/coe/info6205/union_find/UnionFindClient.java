package edu.neu.coe.info6205.union_find;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class UnionFindClient {

    public static double count(int n, int epochs) {
        double avgCount = 0;
        for (int t = 0; t < epochs; t++) {
            UF_HWQUPC dsu = new UF_HWQUPC(n, true);
            Random random = new Random();
            int curCOunt = 0;
            while (dsu.components() != 1) {
                dsu.connect((int)(Math.random() * n), (int)(Math.random() * n));
                curCOunt++;
            }
            avgCount += curCOunt;
        }
        return avgCount/epochs;
    }

    public static void main(String[] args) {
        try {
            String fileSeparator = File.separator;
            FileWriter writer = new FileWriter(Paths.get("assignment_reports",
                    "assignment3_Tanay_Saxena", "union_find.csv").toString());
            writer.write("inputSize,pairCount\n");
            for (int n = 64; n <= 10000; n += 30) {
                double m = UnionFindClient.count(n, 10);
                System.out.println("Number of steps n = " + n + ", number of unions required m = " + m);
                writer.write(n + "," + m + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
