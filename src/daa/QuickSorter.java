package daa;
import java.util.Random;

public class QuickSorter {
    public final Metrics m = new Metrics();
    private final Random rnd = new Random(31331);

    public void sort(int[] a) {
        m.reset();
        sort(a, 0, a.length - 1, 1);
    }

    private void sort(int[] a, int lo, int hi, int d) {
        while (lo < hi) {
            m.enter(d);
            int p = a[lo + rnd.nextInt(hi - lo + 1)];
            int lt = lo, gt = hi, i = lo;
            while (i <= gt) {
                m.comparisons++;
                if (a[i] < p) {
                    swap(a, lt++, i++);
                }
                else if (a[i] > p) {
                    swap(a, i, gt--);
                }
                else i++;
            }
            if (lt - lo < hi - gt) {
                sort(a, lo, lt - 1, d + 1);
                lo = gt + 1;
            }
            else {
                sort(a, gt + 1, hi, d + 1);
                hi = lt - 1;
            }
        }
    }
    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
