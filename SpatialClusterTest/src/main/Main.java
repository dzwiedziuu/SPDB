package main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.ModelObject;
import model.ModelObjectReader;
import model.polygon.ModelPolygonReader;
import view.View;

public class Main
{
	private static final PointGenerator generator = new BeautyPolygonGenerator();
	private static ModelObjectReader modelObjectReader = new ModelPolygonReader();

	private static final String fileName = "test.spa";
	private static final Integer vNumber = 100;
	private static final Integer xMax = 500;
	private static final Integer yMax = 500;

	public static void main(String[] args) throws IOException
	{
		generator.generate(fileName, vNumber, xMax, yMax);
		List<? extends ModelObject> list = modelObjectReader.read(new File(fileName));
		new View().showList(list);
	}
}
