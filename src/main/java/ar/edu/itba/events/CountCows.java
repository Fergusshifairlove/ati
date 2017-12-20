package ar.edu.itba.events;

public class CountCows {
    private int maskSize;
    private int maxDistance;
    private int minSize;
    private int threshold;
    private double r;
    private double g;
    private double b;
    private double maxSpread;

    public CountCows(int maskSize, int maxDistance, int minSize, int threshold, double r, double g, double b, double maxSpread) {
        this.maskSize = maskSize;
        this.maxDistance = maxDistance;
        this.minSize = minSize;
        this.threshold = threshold;
        this.r = r;
        this.g = g;
        this.b = b;
        this.maxSpread = maxSpread;
    }

    public int getMaskSize() {
        return maskSize;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getThreshold() {
        return threshold;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public double getMaxSpread() {
        return maxSpread;
    }
}
