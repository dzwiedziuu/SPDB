package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ModelPointReader
{
	public List<ModelPoint> readPoints(File file) throws IOException
	{
		List<ModelPoint> result = new LinkedList<ModelPoint>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String line = br.readLine(); line != null; line = br.readLine())
		{
			String[] parts = line.split(" ");
			int id = Integer.parseInt(parts[0]);
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int colorId = Integer.parseInt(parts[3]);
			result.add(new ModelPointInstance(id, colorId, x, y));
		}
		return result;
	}
}
