package ar.edu.itba.controllers;

import ar.edu.itba.events.LoadHistogram;
import ar.edu.itba.models.Histogram;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

/**
 * Created by root on 8/25/17.
 */
public class HistogramController {

    public BarChart barChart;
    private EventBus eventBus;

    @Inject
    public HistogramController(EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Subscribe
    public void loadHistogram(LoadHistogram loadHistogram) {
        System.out.println("HISTOGRAM");
        Histogram histogram = loadHistogram.getHistogram();
        XYChart.Series series = new XYChart.Series();
        series.setName("Histogram");
        for (Integer level: histogram.getCategories()) {
            series.getData().add(new XYChart.Data<>(level.toString(), histogram.getFrequency(level)));
        }
        barChart.getData().addAll(series);
    }
}
