package ar.edu.itba.models.clustering;

import ar.edu.itba.models.GreyPixel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Luis on 19/12/2017.
 */
public class HDC {

    public Set<GreyPixelCluster> clusterize(List<GreyPixel> pixels, double minDistance) {
        Set<GreyPixelCluster> clusters = new HashSet<>();
        GreyPixelCluster cluster = new GreyPixelCluster(pixels);
        clusters.add(cluster);

        PriorityQueue<HDCCluster> queue = new PriorityQueue<>();

        queue.add(new HDCCluster(cluster));
        HDCCluster hdcCluster;

        List<GreyPixel> l1, l2, total;
        GreyPixelCluster c1, c2;

        l1 = new ArrayList<>();
        l2 = new ArrayList<>();

        double d1, d2;
        int times = 0;
        while (!queue.isEmpty()) {
            System.out.println(times++);
            hdcCluster = queue.poll();
            if (hdcCluster.spread < minDistance) {
                System.out.println("min distance reached: " + hdcCluster.spread);
                return clusters;
            }
            cluster = hdcCluster.cluster;
            clusters.remove(cluster);

            total = cluster.getPixels().stream().collect(Collectors.toList());

            l1.clear();
            total.remove(hdcCluster.p1);
            l1.add(hdcCluster.p1);
            c1 = new GreyPixelCluster(l1);

            l2.clear();
            total.remove(hdcCluster.p2);
            l2.add(hdcCluster.p2);
            c2 = new GreyPixelCluster(l2);

            for (GreyPixel p: total) {
                d1 = Math.sqrt(Math.pow(p.getX()-c1.getCentroid()[0],2) + Math.pow(p.getY()-c1.getCentroid()[1],2));
                d2 = Math.sqrt(Math.pow(p.getX()-c2.getCentroid()[0],2) + Math.pow(p.getY()-c2.getCentroid()[1],2));
                if (d1 < d2) {
                    c1.addPixel(p);
                } else {
                    c2.addPixel(p);
                }
            }
            clusters.add(c1);
            clusters.add(c2);

            queue.add(new HDCCluster(c1));
            queue.add(new HDCCluster(c2));
        }



        return clusters;
    }


    class HDCCluster implements Comparable<HDCCluster>{
        GreyPixelCluster cluster;
        double spread;
        GreyPixel p1;
        GreyPixel p2;

        public HDCCluster(GreyPixelCluster cluster) {
            this.cluster = cluster;
            this.spread = this.calculateSpreadDistance();
        }

        private double calculateSpreadDistance() {
            double max = Double.MIN_VALUE;
            double dist;
            for (GreyPixel p: this.cluster.getPixels()) {
                for (GreyPixel q: this.cluster.getPixels()) {
                    dist = calculateDistance(p,q);
                    if (dist > max) {
                        max = dist;
                        p1 = p;
                        p2 = q;
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
        public int compareTo(HDCCluster o) {
            return Double.compare(o.spread, spread);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HDCCluster that = (HDCCluster) o;

            return cluster != null ? cluster.equals(that.cluster) : that.cluster == null;
        }

        @Override
        public int hashCode() {
            return cluster != null ? cluster.hashCode() : 0;
        }
    }
}
