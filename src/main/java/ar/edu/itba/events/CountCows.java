package ar.edu.itba.events;

public class CountCows {
    private int maskSize;
    private int maxDistance;
    private int minSize;
    private double maxSpread;

    public CountCows(int maskSize, int maxDistance, int minSize, double maxSpread) {
        this.maskSize = maskSize;
        this.maxDistance = maxDistance;
        this.minSize = minSize;
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

    public double getMaxSpread() {
        return maxSpread;
    }
}
