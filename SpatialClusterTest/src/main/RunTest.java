package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ModelObject;

import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.View;

public class RunTest
{
	private static final Logger logger = LoggerFactory.getLogger(RunTest.class);

	private static final String GRAPH_DIR = "models";
	private Map<Integer, List<? extends ModelObject>> lists = new LinkedHashMap<Integer, List<? extends ModelObject>>();

	public List<GroupResult> run(Params params) throws IOException
	{
		File graphDirFile = new File(GRAPH_DIR);
		graphDirFile.mkdir();

		List<GroupResult> groupResults = new LinkedList<GroupResult>();
		for (; params.doNextTest(); params = params.nextTestParams())
		{
			logger.debug(params.toString());
			for (int i = 0; i < params.modelNumber; i++)
				params.pointGenerator.generate(generateFileName(i), params.n, params.xmax, params.ymax);
			for (int i = 0; i < params.modelNumber; i++)
			{
				List<? extends ModelObject> list = params.modelObjectReader.read(new File(generateFileName(i)));
				lists.put(i, list);
			}

			GroupResult currentGroupResult = new GroupResult();
			currentGroupResult.vertexNum = params.n;
			for (int i = 0; i < params.tries; i++)
			{
				for (int j = 0; j < params.modelNumber; j++)
				{
					List<? extends ModelObject> list = lists.get(j);
					clearList(list);
					StopWatch stopWatch = new StopWatch("time of try=" + i + "-model=" + j);
					stopWatch.start();
					params.algrithm.loadList(list).setClusters();
					long elapsed = stopWatch.getElapsedTime();
					System.gc();
					currentGroupResult.addMiliSeconds(elapsed);
					currentGroupResult.totalClusterCnt += getClusterCnt(list);
					currentGroupResult.totalClusteredNumber += getTotalClusteredNumber(list);
					logger.debug(stopWatch.getTag() + ": " + elapsed);
				}
			}
			groupResults.add(currentGroupResult);
		}
		return groupResults;
	}

	private void clearList(List<? extends ModelObject> list)
	{
		for (ModelObject mo : list)
			mo.setClusterId(null);
	}

	private int getTotalClusteredNumber(List<? extends ModelObject> list)
	{
		int amt = 0;
		for (ModelObject mo : list)
			if (mo.getClusterId() != null)
				amt++;
		return amt;
	}

	private int getClusterCnt(List<? extends ModelObject> list)
	{
		Set<Integer> clusters = new LinkedHashSet<Integer>();
		for (ModelObject mo : list)
			if (mo.getClusterId() != null)
				clusters.add(mo.getClusterId());
		return clusters.size();
	}

	public void runOnce(Params params) throws IOException
	{
		params.pointGenerator.generate(generateFileName(0), params.n, params.xmax, params.ymax);
		List<? extends ModelObject> list = params.modelObjectReader.read(new File(generateFileName(0)));
		params.algrithm.loadList(list).setClusters();
		new View().showList(list);
	}

	private String generateFileName(int i)
	{
		return GRAPH_DIR + "/" + i + ".gra";
	}
}
