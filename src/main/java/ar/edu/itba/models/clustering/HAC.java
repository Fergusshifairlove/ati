package ar.edu.itba.models.clustering;

import ar.edu.itba.models.GreyPixel;

import java.util.*;
import java.util.stream.Collectors;

public class HAC {
    private double maxDistance;
    private int minSize;

    public HAC(double maxDistance, int minSize) {
        this.maxDistance = maxDistance;
        this.minSize = minSize;
    }

    public Set<GreyPixelCluster> clusterize(List<GreyPixel> pixels) {
        System.out.println("creating initialClusters");
        Set<GreyPixelCluster> initialClusters = pixels
                .parallelStream()
                .map(Arrays::asList)
                .map(GreyPixelCluster::new)
                .collect(Collectors.toSet());

        System.out.println("creating pairs");
        HACClusterPair clusterPair;
        PriorityQueue<HACClusterPair> queue = new PriorityQueue<>(HACClusterPair::compareTo);
        initialClusters.forEach(cluster -> {
            for (GreyPixelCluster other : initialClusters) {
                if (cluster != other) {
                    HACClusterPair pair = new HACClusterPair(cluster, other);
                    if (pair.distance <= maxDistance) {
                        queue.add(pair);
                    }
                }
            }
        });


        HashSet<GreyPixelCluster> clusters = new HashSet<>();
        clusters.addAll(initialClusters);
        initialClusters.clear();

        GreyPixelCluster cluster;
        boolean done = false;
        System.out.println("agglomerating pairs");
        while (!queue.isEmpty()) {
            //System.out.println(queue.size());
            clusterPair = queue.poll();
            if (!clusters.contains(clusterPair.cluster1) || !clusters.contains(clusterPair.cluster2)) {
                continue;
            }

            if (clusterPair.distance > maxDistance) {
                System.out.println("max distance reached: " + clusterPair.distance);
                break;
            }

            cluster = GreyPixelCluster.mergeClusters(clusterPair.cluster1, clusterPair.cluster2);
            clusters.remove(clusterPair.cluster1);
            clusters.remove(clusterPair.cluster2);

            System.out.println("calculating distances");
            System.out.println("cluster cant: " + clusters.size());

            long time = System.nanoTime();
            List<HACClusterPair> newPairs = generatePairs(cluster, clusters);
            System.out.println(System.nanoTime() - time);
            queue.addAll(newPairs);

            clusters.add(cluster);
        }

        return clusters.stream().filter(c -> c.getSize() > minSize).collect(Collectors.toSet());
    }

    private List<HACClusterPair> generatePairs(GreyPixelCluster cluster, Collection<GreyPixelCluster> clusters) {
        return clusters
                .parallelStream()
                .map(other -> new HACClusterPair(cluster, other))
                .filter(p -> p.distance <= maxDistance)
                .collect(Collectors.toList());
    }

    class HACClusterPair implements Comparable<HACClusterPair>{
        GreyPixelCluster cluster1;
        GreyPixelCluster cluster2;
        double distance;

        HACClusterPair(GreyPixelCluster c1, GreyPixelCluster c2) {
            this.cluster1 = c1;
            this.cluster2 = c2;
            this.distance = Math.sqrt(Math.pow(c1.getCentroid()[0] - c2.getCentroid()[0],2) + Math.pow(c1.getCentroid()[1] - c2.getCentroid()[1],2));
        }

        @Override
        public int compareTo(HACClusterPair o) {
            return Double.compare(distance, o.distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HACClusterPair that = (HACClusterPair) o;

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
