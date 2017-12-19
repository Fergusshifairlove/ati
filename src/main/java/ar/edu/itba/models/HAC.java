package ar.edu.itba.models;

import java.util.*;
import java.util.stream.Collectors;

public class HAC {

    public Set<GreyPixelCluster> clusterize(List<GreyPixel> pixels, double maxDistance) {
        System.out.println("creating initialClusters");
        Set<GreyPixelCluster> clusters = pixels
                .parallelStream()
                .map(Arrays::asList)
                .map(GreyPixelCluster::new)
                .collect(Collectors.toSet());

        System.out.println("creating pairs");
        ClusterPair clusterPair;
        PriorityQueue<ClusterPair> queue = new PriorityQueue<>();
        clusters.forEach(cluster -> {
            for (GreyPixelCluster other : clusters) {
                if (cluster != other) {
                    ClusterPair pair = new ClusterPair(cluster, other);
                    queue.add(pair);
                }
            }
        });


        GreyPixelCluster cluster;
        boolean done = false;
        System.out.println("agglomerating pairs");
        while (!queue.isEmpty()) {
            System.out.println(clusters.size());
            clusterPair = queue.poll();
            if (!clusters.contains(clusterPair.cluster1) || !clusters.contains(clusterPair.cluster2))
                continue;

            if (clusterPair.distance > maxDistance) {
                System.out.println("max distance reached: " + clusterPair.distance);
                return clusters;
            }

            cluster = GreyPixelCluster.mergeClusters(clusterPair.cluster1, clusterPair.cluster2);
            clusters.remove(clusterPair.cluster1);
            clusters.remove(clusterPair.cluster2);

            for (GreyPixelCluster other: clusters) {
                queue.add(new ClusterPair(cluster, other));
            }

            clusters.add(cluster);
        }

        return clusters;
    }

    class ClusterPair implements Comparable<ClusterPair>{
        GreyPixelCluster cluster1;
        GreyPixelCluster cluster2;
        double distance;

        ClusterPair(GreyPixelCluster c1, GreyPixelCluster c2) {
            this.cluster1 = c1;
            this.cluster2 = c2;
            this.distance = Math.sqrt(Math.pow(c1.getCentroid()[0] - c2.getCentroid()[0],2) + Math.pow(c1.getCentroid()[1] - c2.getCentroid()[1],2));
        }

        @Override
        public int compareTo(ClusterPair o) {
            return Double.compare(distance, o.distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClusterPair that = (ClusterPair) o;

            if (cluster1 != null ? !cluster1.equals(that.cluster1) : that.cluster1 != null) return false;
            return cluster2 != null ? cluster2.equals(that.cluster2) : that.cluster2 == null;
        }

        @Override
        public int hashCode() {
            int result = cluster1 != null ? cluster1.hashCode() : 0;
            result = 31 * result + (cluster2 != null ? cluster2.hashCode() : 0);
            return result;
        }
    }
}
