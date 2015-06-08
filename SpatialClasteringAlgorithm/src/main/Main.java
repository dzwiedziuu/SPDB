package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.ModelObject;
import model.ModelObjectReader;
import model.point.ModelPointReader;
import view.View;
import algorithm.AbstractGDBSCANAlgorithm;
import algorithm.ModelObjectWrapper;
import algorithm.PointAlgorithm;

public class Main
{
	private static ModelObjectReader modelObjectReader = new ModelPointReader();
	private static AbstractGDBSCANAlgorithm algrithm = new PointAlgorithm();

	public static void main(String[] args) throws IOException
	{
		List<? extends ModelObject> list = modelObjectReader.read(new File("test.spa"));
		new PointAlgorithm().loadList(createWrapperList(list)).setClusters();
		new View().showList(list);
	}

	private static List<ModelObjectWrapper> createWrapperList(List<? extends ModelObject> list)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObject mp : list)
			result.add(new ModelObjectWrapper(mp));
		return result;
	}
}
