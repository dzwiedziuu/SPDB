package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.ModelPoint;
import model.ModelPointReader;
import view.View;
import algorithm.AlgorithmPointWrapper;
import algorithm.PointAlgorithm;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		List<ModelPoint> list = new ModelPointReader().readPoints(new File("test.spa"));
		new PointAlgorithm().loadList(createWrapperList(list)).setClusters();
		new View().showList(list);
	}

	private static List<AlgorithmPointWrapper> createWrapperList(List<ModelPoint> list)
	{
		List<AlgorithmPointWrapper> result = new LinkedList<AlgorithmPointWrapper>();
		for (ModelPoint mp : list)
			result.add(new AlgorithmPointWrapper(mp));
		return result;
	}
}
