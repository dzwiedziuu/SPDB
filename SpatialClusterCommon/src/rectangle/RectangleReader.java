package rectangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import model.ModelObject;
import model.polygon.Edge;
import model.polygon.ModelPolygonReader;
import model.polygon.Vertex;

public class RectangleReader extends ModelPolygonReader
{
	List<Rectangle> rectangles = new LinkedList<Rectangle>();

	@Override
	public List<? extends ModelObject> read(File file) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		boolean blankLineRead = false;
		while ((line = br.readLine()) != null)
		{
			if (line.isEmpty())
				blankLineRead = true;
			else if (!blankLineRead)
			{
				String[] parts = line.split(" ");
				Vertex v1 = findOrRegisterVertex(new Vertex(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
				Vertex v2 = findOrRegisterVertex(new Vertex(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
				createNewEdge(Arrays.asList(v1, v2), Integer.parseInt(parts[0]));
			} else
			{
				String[] parts = line.split(" ");
				Integer id = Integer.parseInt(parts[0]);
				Integer clusterId = Integer.parseInt(parts[1]);
				List<Edge> polygonEdges = new LinkedList<Edge>();
				for (int i = 2; i < parts.length; i++)
					polygonEdges.add(findEdge(Integer.parseInt(parts[i])));
				Rectangle rectangle = new Rectangle(getBounds(polygonEdges), id);
				rectangle.edges.addAll(polygonEdges);
				rectangle.setClusterId(clusterId);
				for (Edge e : polygonEdges)
					e.rectangles.add(rectangle);
				rectangles.add(rectangle);
			}
		}
		br.close();
		return rectangles;
	}

	private List<Vertex> getBounds(List<Edge> polygonEdges)
	{
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Edge e : polygonEdges)
			for (Vertex v : e.vertices)
			{
				minX = Math.min(minX, v.x);
				maxX = Math.max(maxX, v.x);
				minY = Math.min(minY, v.y);
				maxY = Math.max(maxY, v.y);
			}
		return Arrays.asList(new Vertex(minX, minY), new Vertex(maxX, minY), new Vertex(maxX, maxY), new Vertex(minX, maxY));
	}
}
