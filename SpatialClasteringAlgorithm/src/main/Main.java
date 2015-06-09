package main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.ModelObject;
import model.ModelObjectReader;
import model.point.ModelPointReader;
import view.View;
import algorithm.AbstractGDBSCANAlgorithm;
import algorithm.PointAlgorithm;

public class Main
{
	private static ModelObjectReader modelObjectReader = new ModelPointReader();
	private static AbstractGDBSCANAlgorithm algrithm = new PointAlgorithm(7);

	public static void main(String[] args) throws IOException
	{
		List<? extends ModelObject> list = modelObjectReader.read(new File("test.spa"));
		algrithm.loadList(list).setClusters();
		new View().showList(list);
	}
}
