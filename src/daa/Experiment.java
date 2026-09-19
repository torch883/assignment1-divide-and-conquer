package daa;
import java.io.*;
import java.util.*;

public class Experiment {
    static int[] gen(String type, int n, Random r) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = switch (type) {
                case "sorted" -> i;
                case "reverse" -> n - i;
                case "duplicates" -> r.nextInt(10);
                default -> r.nextInt();
            };
        }
        return a;
    }
    public static void run(String csvPath) throws IOException {
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        String[] types = {"random", "sorted", "reverse", "duplicates"};
        Random r = new Random(1);
        try (PrintWriter w = new PrintWriter(csvPath)) {
            w.println("algorithm,type,n,time_ms,max_depth,comparisons,calls");
            for (int n : sizes){
                for (String t : types) {
                    for (String alg : new String[]{"MergeSort", "QuickSort", "Select"}) {
                        int[] base = gen(t, n, r);
                        for (int warm = 0; warm < 2; warm++) exec(alg, base.clone());
                        int[] a = base.clone();
                        long[] res = exec(alg, a);
                        w.printf(Locale.US, "%s,%s,%d,%.3f,%d,%d,%d%n", alg, t, n, res[0] / 1e6, res[1], res[2], res[3]);
                    }
                }
            }
            for (int n : new int[]{1_000, 10_000, 100_000, 500_000}) {
                Point[] pts = new Point[n];
                for (int i = 0; i < n; i++){
                    pts[i] = new Point(r.nextDouble() * 1e6, r.nextDouble() * 1e6);
                }
                ClosestPairSolver s = new ClosestPairSolver();
                s.solve(pts);
                long t0 = System.nanoTime(); s.solve(pts); long t1 = System.nanoTime();
                w.printf(Locale.US, "ClosestPair,random,%d,%.3f,%d,%d,%d%n", n, (t1 - t0) / 1e6, s.m.maxDepth, s.m.comparisons, s.m.calls);
            }
        }
    }
    static long[] exec(String alg, int[] a) {
        long t0, t1; Metrics m;
        switch (alg) {
            case "MergeSort" -> {
                var s = new MergeSorter();
                t0 = System.nanoTime();
                s.sort(a);
                t1 = System.nanoTime();
                m = s.m;
            }
            case "QuickSort" -> {
                var s = new QuickSorter();
                t0 = System.nanoTime();
                s.sort(a);
                t1 = System.nanoTime();
                m = s.m;
            }
            default -> {
                var s = new DeterministicSelector();
                t0 = System.nanoTime();
                s.select(a, a.length / 2);
                t1 = System.nanoTime();
                m = s.m;
            }
        }
        return new long[]{t1 - t0, m.maxDepth, m.comparisons, m.calls};
    }
}
