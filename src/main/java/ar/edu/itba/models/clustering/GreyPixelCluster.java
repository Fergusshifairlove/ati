package ar.edu.itba.models.clustering;

import ar.edu.itba.models.GreyPixel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GreyPixelCluster {
    private double[] centroid;
    private List<GreyPixel> pixels;
    private double spread;

    public double[] getCentroid() {
        return centroid;
    }

    public List<GreyPixel> getPixels() {
        return pixels;
    }

    public GreyPixelCluster(List<GreyPixel> pixels) {
        if (pixels.size() < 1)
            throw new IllegalArgumentException();
        this.centroid = calculateCentroid(pixels);
        this.pixels = pixels;
        this.spread = -1;
    }

    private double[] calculateCentroid(List<GreyPixel> pixels) {
        double[] centroid = {0, 0};

        for (GreyPixel p: pixels) {
            centroid[0]+=p.getX();
            centroid[1]+=p.getY();
        }

        int size = pixels.size();
        centroid[0] /= size;
        centroid[1] /= size;

        return centroid;
    }

    void addPixel(GreyPixel pixel) {
        this.pixels.add(pixel);
        this.centroid = calculateCentroid(this.pixels);
    }

    public int getSize() {
        return pixels.size();
    }

    public double calculateSpreadDistance() {
        if (this.spread != -1)
            return this.spread;

        double max = Double.MIN_VALUE;
        double dist;
        for (GreyPixel p: pixels) {
            for (GreyPixel q: pixels) {
                dist = calculateDistance(p,q);
                if (dist > max) {
                    max = dist;
                }
            }
        }
        this.spread = max;
        return this.spread;
    }
    private double calculateDistance(GreyPixel p1, GreyPixel p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GreyPixelCluster that = (GreyPixelCluster) o;

        return pixels != null ? pixels.equals(that.pixels) : that.pixels == null;
    }

    @Override
    public int hashCode() {
        return pixels != null ? pixels.hashCode() : 0;
    }

    public static GreyPixelCluster mergeClusters(GreyPixelCluster pixelCluster1, GreyPixelCluster pixelCluster2) {
        return new GreyPixelCluster(Stream.concat(pixelCluster1.pixels.stream(), pixelCluster2.pixels.stream()).collect(Collectors.toList()));
    }

}
