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
	private static ModelObjectReader modelObjectReader = new ModelPolygonReader();

	public static void main(String[] args) throws IOException
	{
		List<? extends ModelObject> list = modelObjectReader.read(new File("test.spa"));
		new View().showList(list);
	}

}
