package ar.edu.itba.models;

import ar.edu.itba.constants.SIFTComparation;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.process.ImageProcessor;
import mpicbg.ij.SIFT;
import mpicbg.imagefeatures.Feature;
import mpicbg.imagefeatures.FloatArray2DSIFT;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SiftManager {
    final static double DELTA = 0.4;

    public static List<Feature> getFeatures(BufferedImage img) {
        List<Feature> fs = new ArrayList<Feature>();
        FloatArray2DSIFT.Param p = new FloatArray2DSIFT.Param();
        GenericDialog gd = new GenericDialog("SIFT");
        SIFT.addFields(gd, p);
        SIFT.readFields(gd, p);
        ImagePlus imp = new ImagePlus("", img);
        ImageProcessor ip1 = imp.getProcessor().convertToFloat();
        SIFT sift = new SIFT(new FloatArray2DSIFT(p));
        sift.extractFeatures(ip1, fs);
        return fs;
    }

    public static SIFTComparation compareImages(List<Feature> keyPoints1, List<Feature> keyPoints2) {
        List<Feature> matchFrom1 = new ArrayList<>();
        List<Feature> matchFrom2 = new ArrayList<>();

        double dist;
        for (Feature keypoint1 : keyPoints1) {

            double minDis = Double.MAX_VALUE;
            Feature minDistFeature = null;
            for (Feature keypoint2 : keyPoints2) {

                dist = keypoint1.descriptorDistance(keypoint2);

                if (dist < minDis) {
                    minDis = dist;
                    minDistFeature = keypoint2;
                }

            }
            if (minDis < DELTA) {
                matchFrom2.add(minDistFeature);
                matchFrom1.add(keypoint1);
            }

        }

        double percentage = ((double) matchFrom1.size())
                / ((keyPoints1.size() < keyPoints2.size()) ? keyPoints2.size() : keyPoints1.size());


        // String resultMsg=(percentage<0.90)?containsMsg:equalsMsg;
        String resultMsg;
        if (percentage >= 0.8) {
            return SIFTComparation.EQUALS;
        } else if (percentage >= 0.5) {
            return SIFTComparation.CONTAINS;
        } else {
            return SIFTComparation.DIFFERNT;
        }

    }
}
