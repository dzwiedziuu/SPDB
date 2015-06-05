package main;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import test.RunTest;
import test.RunTest.GroupResult;
import view.ChartView;
import domain.Params;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Params params = createParams();
		List<GroupResult> result = new RunTest().run(params);
		printChart(result);
	}

	private static void printChart(List<GroupResult> result)
	{
		ChartView v = new ChartView(2);
		v.add(new ChartCreator(result)
		{
			@Override
			void extractValue(XYSeries series1, XYSeries series2, XYSeries series3, GroupResult gr)
			{
				series1.add(gr.measure, gr.colors);
				series2.add(gr.measure, gr.worseSteps);
			}
		}.createChart("liczba kolorów", "liczba skoków do gorszego stanu", ""));
		v.add(new ChartCreator(result)
		{
			@Override
			void extractValue(XYSeries series1, XYSeries series2, XYSeries series3, GroupResult gr)
			{
				series2.add(gr.measure, gr.pctg);
				series3.add(gr.measure, gr.worseSteps);
				series1.add(gr.measure, gr.betterSteps);
			}

		}.createChart("liczba skoków do lepszego stanu", "% skoków do gorszego stanu", "liczba skoków do gorszego stanu"));
		v.setVisible(true);
	}

	private static abstract class ChartCreator
	{
		List<GroupResult> result;

		ChartCreator(List<GroupResult> result)
		{
			this.result = result;
		}

		ChartPanel createChart(String label, String label2, String label3)
		{
			XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
			XYSeriesCollection xySeriesCollection2 = new XYSeriesCollection();
			XYSeries colors = new XYSeries(label);
			XYSeries colors2 = new XYSeries(label2);
			XYSeries colors3 = new XYSeries(label3);
			xySeriesCollection.addSeries(colors);
			xySeriesCollection2.addSeries(colors2);
			xySeriesCollection.addSeries(colors3);
			for (GroupResult gr : result)
			{
				extractValue(colors, colors2, colors3, gr);
			}

			JFreeChart objChart = ChartFactory.createXYLineChart("Tytul", "InitialTemp", label, xySeriesCollection, PlotOrientation.VERTICAL, true, // include
					// legend?
					true, // include tooltips?
					true // include URLs?
					);

			final XYPlot plot = objChart.getXYPlot();
			final NumberAxis axis2 = new NumberAxis(label2);
			axis2.setAutoRangeIncludesZero(false);
			plot.setRangeAxis(1, axis2);
			plot.setDataset(1, xySeriesCollection2);
			plot.mapDatasetToRangeAxis(1, 1);
			final XYItemRenderer renderer = plot.getRenderer();
			renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
			if (renderer instanceof XYLineAndShapeRenderer)
			{
				final XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer) renderer;
				rr.setLinesVisible(true);
				rr.setShapesVisible(true);
			}

			final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
			renderer2.setSeriesPaint(0, Color.black);
			renderer2.setPlotLines(true);
			renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
			plot.setRenderer(1, renderer2);

			return new ChartPanel(objChart);
		}

		abstract void extractValue(XYSeries series1, XYSeries series2, XYSeries colors3, GroupResult gr);
	}

	private static Params createParams()
	{
		return new TestParams();
	}
}
