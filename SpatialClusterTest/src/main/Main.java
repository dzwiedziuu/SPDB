package main;

import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import view.ChartView;

public class Main
{
	private enum Mode
	{
		PERFORMANCE, ONCE_VISUAL;
	}

	private static final Mode mode = Mode.PERFORMANCE;

	public static void main(String[] args) throws IOException
	{
		if (Mode.ONCE_VISUAL.equals(mode))
			new RunTest().runOnce(new Params(40));
		if (Mode.PERFORMANCE.equals(mode))
		{
			List<GroupResult> result = new RunTest().run(new Params());
			printChart(result);
		}
	}

	private static void printChart(List<GroupResult> result)
	{
		ChartView v = new ChartView(3);
		v.add(new ChartCreator(result)
		{
			@Override
			void extractValue(XYSeries series1, GroupResult gr)
			{
				series1.add(gr.vertexNum, gr.getAvgMiliSeconds());
			}
		}.createChart("sredni czas [ms]", "liczba obiektow", "zaleznosc czasu od liczby obiektow"));
		v.add(new ChartCreator(result)
		{
			@Override
			void extractValue(XYSeries series1, GroupResult gr)
			{
				series1.add(gr.vertexNum, gr.totalClusteredNumber / gr.size);
			}
		}.createChart("srednia liczba sklastrowanych obiektow", "liczba obiektow", "zaleznosc liczby sklastrowanych obiektow od liczby obiektow"));
		v.add(new ChartCreator(result)
		{
			@Override
			void extractValue(XYSeries series1, GroupResult gr)
			{
				series1.add(gr.vertexNum, gr.totalClusterCnt / gr.size);
			}
		}.createChart("srednia liczba klastrow", "liczba obiektow", "zaleznosc liczby klastrow od liczby obiektow"));
		v.setVisible(true);
	}

	private static abstract class ChartCreator
	{
		List<GroupResult> result;

		ChartCreator(List<GroupResult> result)
		{
			this.result = result;
		}

		ChartPanel createChart(String ylabel, String xlabel, String titleLabel)
		{
			XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
			XYSeries colors = new XYSeries(ylabel);
			xySeriesCollection.addSeries(colors);
			for (GroupResult gr : result)
			{
				extractValue(colors, gr);
			}

			JFreeChart objChart = ChartFactory.createXYLineChart(titleLabel, xlabel, ylabel, xySeriesCollection, PlotOrientation.VERTICAL, true, // include
					// legend?
					true, // include tooltips?
					true // include URLs?
					);
			return new ChartPanel(objChart);
		}

		abstract void extractValue(XYSeries series1, GroupResult gr);
	}
}
