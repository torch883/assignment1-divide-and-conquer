package daa;

public class MergeSorter {
    public final Metrics m = new Metrics();
    static final int CUTOFF = 16;

    public void sort(int[] a){
        m.reset();
        if (a.length<2){
            return;
        }
        sort(a, new int[a.length], 0, a.length - 1, 1);
    }

    private void sort(int[] a, int[] buf, int lo, int hi, int d) {
        m.enter(d);
        if (hi - lo + 1 <= CUTOFF) {
            insertion(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, buf, lo, mid, d + 1);
        sort(a, buf, mid + 1, hi, d + 1);
        merge(a, buf, lo, mid, hi);
    }
    private void merge(int[] a, int[] buf, int lo, int mid, int hi) {
        System.arraycopy(a, lo, buf, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = buf[j++];
            else if (j > hi) a[k] = buf[i++];
            else {
                m.comparisons++;
                a[k] = buf[j] < buf[i] ? buf[j++] : buf[i++]; }
        }
    }
    private void insertion(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i], j = i - 1;
            while (j >= lo) {
                m.comparisons++;
                if (a[j] > x) {
                    a[j + 1] = a[j]; j--;
                } else break;
            }
            a[j + 1] = x;
        }
    }
}
