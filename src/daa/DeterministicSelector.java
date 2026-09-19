package daa;

public class DeterministicSelector {
    public final Metrics m = new Metrics();

    public int select(int[] a, int k) {
        m.reset();
        return select(a, 0, a.length - 1, k, 1);
    }
    private int select(int[] a, int lo, int hi, int k, int d) {
        while (true) {
            m.enter(d);
            if (lo == hi) return a[lo];
            int pv = medianOfMedians(a, lo, hi, d);
            int lt = lo, gt = hi, i = lo;
            while (i <= gt) {
                m.comparisons++;
                if (a[i] < pv) {
                    QuickSorter.swap(a, lt++, i++);
                }
                else if (a[i] > pv) {
                    QuickSorter.swap(a, i, gt--);
                }
                else i++;
            }
            if (k < lt) {
                hi = lt - 1;
            } else if (k > gt) {
                lo = gt + 1;
            } else return a[k];
            d++;
        }
    }
    private int medianOfMedians(int[] a, int lo, int hi, int d) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertion(a, lo, hi);
            return a[lo + n / 2];
        }
        int cnt = 0;
        for (int i = lo; i <= hi; i += 5) {
            int e = Math.min(i + 4, hi);
            insertion(a, i, e);
            QuickSorter.swap(a, lo + cnt++, i + (e - i) / 2);
        }
        return select(a, lo, lo + cnt - 1, lo + (cnt - 1) / 2, d + 1);
    }
    private void insertion(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i], j = i - 1;
            while (j >= lo) {
                m.comparisons++;
                if (a[j] > x) {
                    a[j + 1] = a[j];
                    j--; }
                else break;
            }
            a[j + 1] = x;
        }
    }
}