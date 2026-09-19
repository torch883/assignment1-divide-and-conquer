package daa;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsTest {
    Random r = new Random(7);

    int[][] cases() {
        return new int[][]{ {}, {5}, {3, 3, 3, 3}, {1, 2, 3, 4, 5}, {5, 4, 3, 2, 1},
                r.ints(1000, -50, 50).toArray(), r.ints(5000).toArray() };
    }
    @Test void mergeSortMatchesArraysSort() {
        for (int[] c : cases()) { int[] e = c.clone(), a = c.clone(); Arrays.sort(e); new MergeSorter().sort(a); assertArrayEquals(e, a); }
    }
    @Test void quickSortMatchesArraysSort() {
        for (int[] c : cases()) { int[] e = c.clone(), a = c.clone(); Arrays.sort(e); new QuickSorter().sort(a); assertArrayEquals(e, a); }
    }
    @Test void selectMatchesSorted100Times() {
        for (int t = 0; t < 100; t++) {
            int n = 1 + r.nextInt(500);
            int[] a = r.ints(n, -100, 100).toArray();
            int k = r.nextInt(n);
            int[] s = a.clone(); Arrays.sort(s);
            assertEquals(s[k], new DeterministicSelector().select(a.clone(), k));
        }
    }
    @Test void closestPairMatchesBruteForce() {
        for (int t = 0; t < 50; t++) {
            int n = 2 + r.nextInt(2000);
            Point[] p = new Point[n];
            for (int i = 0; i < n; i++) p[i] = new Point(r.nextInt(1000), r.nextInt(1000)); // с дубликатами
            assertEquals(brute(p), new ClosestPairSolver().solve(p), 1e-9);
        }
    }
    static double brute(Point[] p) {
        double b = Double.MAX_VALUE;
        for (int i = 0; i < p.length; i++) for (int j = i + 1; j < p.length; j++) b = Math.min(b, ClosestPairSolver.dist(p[i], p[j]));
        return b;
    }
    @Test void quickSortDepthIsLogarithmic() {
        QuickSorter q = new QuickSorter(); q.sort(r.ints(100_000).toArray());
        assertTrue(q.m.maxDepth <= 2 * 17 + 5);
    }
}