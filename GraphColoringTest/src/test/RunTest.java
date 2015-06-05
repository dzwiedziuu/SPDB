package test;

import graph.GraphGenerator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algorithm.AlgorithmResult;
import algorithm.GraphColoringAlgorithm;
import domain.Graph;
import domain.Params;

public class RunTest
{
	private static final Logger logger = LoggerFactory.getLogger(RunTest.class);

	private static final Logger logger2 = LoggerFactory.getLogger("test2.RunTest");

	GraphGenerator graphGenerator = new GraphGenerator();
	GraphColoringAlgorithm graphColoringAlgorithm = new GraphColoringAlgorithm();

	// private List<AlgorithmResult> result;

	public List<GroupResult> run(Params params) throws IOException
	{

		File graphDir = new File("graphs");
		graphDir.mkdir();
		for (int i = 0; i < params.graphNumber; i++)
		{
			graphGenerator.generateGraph(params.vertexNumber, params.probability);
			graphGenerator.storeGraph(new File(graphDir, i + ".gra"), false);
		}
		List<GroupResult> groupResults = new LinkedList<RunTest.GroupResult>();
		for (; params.doNextTest(); params = params.nextTestParams())
		{
			logger.warn(params.toString());
			List<AlgorithmResult> result = new LinkedList<AlgorithmResult>();
			for (int i = 0; i < params.tries; i++)
			{
				for (int j = 0; j < params.graphNumber; j++)
				{
					Graph g = graphColoringAlgorithm.readGraph(new File(graphDir, j + ".gra"));
					g = graphColoringAlgorithm.colorGraph(g, params.initialTemperature, params.minimalTemperature, params.alpha, params.k);
					AlgorithmResult algorithmResult = graphColoringAlgorithm.printResult(g, null, false);
					result.add(algorithmResult);
					logger2.info(algorithmResult.toString());
				}
			}
			GroupResult groupResult = printResult(result, params.initialTemperature);
			groupResults.add(groupResult);
		}
		return groupResults;
	}

	private GroupResult printResult(List<AlgorithmResult> result, Double measure)
	{
		double totalColors = 0;
		double totalPctg = 0.0;
		double totalWorseSteps = 0.0;
		double totalBetterSteps = 0;
		for (AlgorithmResult r : result)
		{
			totalColors += r.colorNumber;
			totalPctg += r.getPctWorseSteps();
			totalBetterSteps += r.getBetterSteps();
			totalWorseSteps += r.worseSteps;
		}
		GroupResult groupResult = new GroupResult();
		groupResult.colors = totalColors / result.size();
		groupResult.pctg = totalPctg / result.size();
		groupResult.betterSteps = totalBetterSteps / result.size();
		groupResult.measure = measure;
		groupResult.worseSteps = totalWorseSteps / result.size();
		logger2.warn(groupResult.toString());
		return groupResult;
	}

	public static class GroupResult
	{
		public double colors;
		public double measure;
		public double pctg;
		public double betterSteps;
		public double worseSteps;

		@Override
		public String toString()
		{
			return "avgColors=" + colors + ", avgPct=" + pctg + ", avgBetterSteps=" + betterSteps + ", avgWorseSteps=" + worseSteps;
		}
	}
}
