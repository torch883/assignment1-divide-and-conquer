package daa;
import java.util.*;

public class ClosestPairSolver {
    public final Metrics m = new Metrics();

    public double solve(Point[] pts) {
        m.reset();
        if (pts.length < 2) {
            return Double.POSITIVE_INFINITY;
        }
        Point[] p = pts.clone();
        Arrays.sort(p, Comparator.comparingDouble(Point::x));
        return rec(p, new Point[p.length], 0, p.length, 1);
    }
    private double rec(Point[] p, Point[] tmp, int lo, int hi, int d) {
        m.enter(d);
        if (hi - lo <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++) {
                for (int j = i + 1; j < hi; j++) {
                    best = Math.min(best, dist(p[i], p[j]));
                }
            }
            Arrays.sort(p, lo, hi, Comparator.comparingDouble(Point::y));
            return best;
        }
        int mid = (lo + hi) >>> 1;
        double midX = p[mid].x();
        double best = Math.min(rec(p, tmp, lo, mid, d + 1), rec(p, tmp, mid, hi, d + 1));
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            tmp[k++] = p[i].y() <= p[j].y() ? p[i++] : p[j++];
        }
        while (i < mid) {
            tmp[k++] = p[i++];
        }
        while (j < hi) {
            tmp[k++] = p[j++];
        }
        System.arraycopy(tmp, lo, p, lo, hi - lo);
        int s = 0;
        for (int t = lo; t < hi; t++) {
            if (Math.abs(p[t].x() - midX) >= best) continue;
            for (int q = s - 1; q >= 0 && p[t].y() - tmp[q].y() < best; q--) {
                m.comparisons++;
                best = Math.min(best, dist(p[t], tmp[q]));
            }
            tmp[s++] = p[t];
        }
        return best;
    }
    static double dist(Point a, Point b) {
        return Math.hypot(a.x() - b.x(), a.y() - b.y());
    }
}
