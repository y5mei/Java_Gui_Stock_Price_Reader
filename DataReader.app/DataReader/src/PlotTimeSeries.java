import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PlotTimeSeries extends ApplicationFrame {
    private ChartPanel chartPanel; // chartPanel 可以当 Jpanel 用
    // 一共有四个method：环环相扣
    // 1） PlotTimeSeries 用一个 Timeseries 初始化一个 PlotTimeSeries;
    // 2) createDataset 把一个TimeSeries 变成一个 XYDataset;
    // 3) creatChart create a JFreeChart from a XYDataset;
    // 4) createPanel 把 JFreeChart 变成 JPanel


    public PlotTimeSeries(String title, TimeSeries s) {
        super(title);
        XYDataset dataset = createDataset(s);
        JFreeChart chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);
    }

    private static XYDataset createDataset(TimeSeries s) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s);
        dataset.setDomainIsPointsInTime(true);
        return dataset;
    }

    public static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Stock Price",  // title
                "Date", // x-axis label
                "Price Per Unit",// y-axis label
                dataset,// data
                true,// create legend?
                true,// generate tooltips?
                false// generate URLs?
        );
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public JPanel createPanel() {
        return chartPanel;
    }


}
