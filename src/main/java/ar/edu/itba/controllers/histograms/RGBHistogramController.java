package ar.edu.itba.controllers.histograms;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.ImageModified;
import ar.edu.itba.events.OperationsConfirmed;
import ar.edu.itba.events.ShowHistogram;
import ar.edu.itba.models.GreyImageMatrix;
import ar.edu.itba.models.Histogram;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.models.RGBImageMatrix;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.awt.image.BufferedImage;

/**
 * Created by root on 8/25/17.
 */
public class RGBHistogramController {

    public BarChart rBarChartBefore;
    public BarChart gBarChartBefore;
    public BarChart bBarChartBefore;
    public BarChart rBarChartAfter;
    public BarChart gBarChartAfter;
    public BarChart bBarChartAfter;
    private Histogram red, green, blue;
    private EventBus eventBus;

    @Inject
    public RGBHistogramController(EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Subscribe
    public void loadHistogram(ImageModified imageModified) {
        System.out.println("HISTOGRAM");
        if (! (imageModified.getModified() instanceof RGBImageMatrix))
            return;
        this.clearAfterData();

        ImageMatrix image = ImageMatrix.readImage(imageModified.getModified().getImage(false));

        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return;

        RGBImageMatrix matrix = (RGBImageMatrix) image;
        this.red = new Histogram(matrix.getRedBand());
        this.green = new Histogram(matrix.getGreenBand());
        this.blue = new Histogram(matrix.getBlueBand());
        System.out.println("LOAD HISTOGRAM");
        this.loadAfterData();
    }

    @Subscribe void imageOpened(ShowHistogram imageLoaded) {

        if (! (imageLoaded.getImage() instanceof RGBImageMatrix))
            return;

        this.clearBeforeData();
        this.clearAfterData();
        ImageMatrix image = imageLoaded.getImage();

        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return;

        System.out.println("IMAGE OPENED");
        RGBImageMatrix matrix = (RGBImageMatrix) ImageMatrix.readImage(imageLoaded.getImage().getImage(false));
        this.loadHistograms(matrix);
        this.loadBeforeData();

    }

    @Subscribe void operationsConfirmed(OperationsConfirmed operationsConfirmed) {
        clearBeforeData();
        System.out.println("OPERATIONS CONFIRMED");
        this.confirmChanges();
        this.clearAfterData();
    }

    private XYChart.Series getSeries(Histogram histogram) {
        XYChart.Series series = new XYChart.Series();
        series.setName("Histogram");
        for (Integer level: histogram.getCategories()) {
            series.getData().add(new XYChart.Data<>(level.toString(), histogram.getFrequency(level)));
        }
        series.setName("Frequency of grey level");
        return series;
    }

    private void loadHistograms(RGBImageMatrix matrix) {
        this.red = new Histogram(matrix.getRedBand());
        this.green = new Histogram(matrix.getGreenBand());
        this.blue = new Histogram(matrix.getBlueBand());
    }

    private void clearAfterData() {
        rBarChartAfter.getData().clear();
        gBarChartAfter.getData().clear();
        bBarChartAfter.getData().clear();
    }

    private void clearBeforeData() {
        rBarChartBefore.getData().clear();
        gBarChartBefore.getData().clear();
        bBarChartBefore.getData().clear();
    }
    private void loadAfterData() {
        rBarChartAfter.getData().addAll(this.getSeries(red));
        gBarChartAfter.getData().addAll(this.getSeries(green));
        bBarChartAfter.getData().addAll(this.getSeries(blue));
    }
    private void loadBeforeData() {
        rBarChartBefore.getData().addAll(this.getSeries(red));
        gBarChartBefore.getData().addAll(this.getSeries(green));
        bBarChartBefore.getData().addAll(this.getSeries(blue));
    }
    private void confirmChanges() {
        this.rBarChartBefore.getData().addAll(this.rBarChartAfter.getData());
        this.gBarChartBefore.getData().addAll(this.gBarChartAfter.getData());
        this.bBarChartBefore.getData().addAll(this.bBarChartAfter.getData());
    }
}
