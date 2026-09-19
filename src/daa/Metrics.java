package daa;

public class Metrics {
    public long comparisons, calls;
    public int maxDepth;
    public void reset() {
        comparisons = calls = 0;
        maxDepth = 0;
    }
    public void enter(int depth) {
        calls++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }
}
