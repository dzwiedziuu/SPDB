package main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.ModelObject;
import model.ModelObjectReader;
import rectangle.RectangleReader;
import view.View;
import algorithm.AbstractGDBSCANAlgorithm;
import algorithm.RectangleAlgorithm;

public class Main
{
	private static final String fileName = "test.spa";
	private static final Integer vNumber = 100;
	private static final Integer xMax = 500;
	private static final Integer yMax = 500;
	private static final Integer densePredicateValue = 30000;

	private static final PointGenerator generator = new RectangleGenerator();
	private static ModelObjectReader modelObjectReader = new RectangleReader();
	private static AbstractGDBSCANAlgorithm algrithm = new RectangleAlgorithm(densePredicateValue);

	public static void main(String[] args) throws IOException
	{
		if (generator != null)
			generator.generate(fileName, vNumber, xMax, yMax);
		List<? extends ModelObject> list = modelObjectReader.read(new File(fileName));
		if (algrithm != null)
			algrithm.loadList(list).setClusters();
		new View().showList(list);
	}
}
