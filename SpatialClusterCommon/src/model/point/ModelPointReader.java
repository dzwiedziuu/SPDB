package model.point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.ModelObject;
import model.ModelObjectReader;

/*
 * klasa wczytujaca punkty z pliku
 */
public class ModelPointReader implements ModelObjectReader
{
	public List<ModelObject> read(File file) throws IOException
	{
		List<ModelObject> result = new LinkedList<ModelObject>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String line = br.readLine(); line != null; line = br.readLine())
		{
			String[] parts = line.split(" ");
			int id = Integer.parseInt(parts[0]);
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int colorId = Integer.parseInt(parts[3]);
			result.add(new ModelPoint(id, colorId, x, y));
		}
		return result;
	}
}
