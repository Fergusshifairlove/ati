package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.models.GreyImageMatrix;
import ar.edu.itba.models.Histogram;
import ar.edu.itba.models.ImageMatrix;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.sun.javafx.charts.Legend;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Created by root on 8/25/17.
 */
public class HistogramController {

    public BarChart barChartBefore, barChartAfter;
    private EventBus eventBus;

    @Inject
    public HistogramController(EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Subscribe
    public void loadHistogram(ImageModified imageModified) {
        System.out.println("HISTOGRAM");

        barChartAfter.getData().clear();
        Histogram histogram = new Histogram((GreyImageMatrix) ImageMatrix.readImage(imageModified.getModified().getImage(false)));
        System.out.println("LOAD HISTOGRAM");
        barChartAfter.getData().addAll(this.getSeries(histogram));
    }

    @Subscribe void imageOpened(ImageLoaded imageLoaded) {
        barChartAfter.getData().clear();
        barChartBefore.getData().clear();
        ImageMatrix image = imageLoaded.getImage();

        if (image.getType() == BufferedImage.TYPE_INT_RGB || image.getType() == BufferedImage.TYPE_3BYTE_BGR)
            return;

        System.out.println("IMAGE OPENED");
        Histogram histogram = new Histogram((GreyImageMatrix) image);
        barChartBefore.getData().addAll(this.getSeries(histogram));

    }

    @Subscribe void operationsConfirmed(OperationsConfirmed operationsConfirmed) {
        this.barChartBefore.getData().clear();
        System.out.println("OPERATIONS CONFIRMED");
        this.barChartBefore.getData().addAll(this.barChartAfter.getData());
        this.barChartAfter.getData().clear();
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
}
