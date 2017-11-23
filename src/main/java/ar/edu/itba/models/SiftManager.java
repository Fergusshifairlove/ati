package ar.edu.itba.models;

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
}
