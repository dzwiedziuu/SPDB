package model.polygon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.ModelObject;
import model.ModelObjectReader;

public class ModelPolygonReader implements ModelObjectReader
{
	List<Vertex> vertices = new LinkedList<Vertex>();
	List<Edge> edgeList = new LinkedList<Edge>();
	List<Polygon> polygonList = new LinkedList<Polygon>();

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
				Edge e1 = findEdge(Integer.parseInt(parts[2]));
				Edge e2 = findEdge(Integer.parseInt(parts[3]));
				Edge e3 = findEdge(Integer.parseInt(parts[4]));
				List<Edge> polygonEdges = Arrays.asList(e1, e2, e3);
				Polygon polygon = new Polygon(getUniqueVertices(polygonEdges), id);
				polygon.edges.addAll(polygonEdges);
				polygon.setClusterId(clusterId);
				polygonList.add(polygon);
			}
		}
		return polygonList;
	}

	private List<Vertex> getUniqueVertices(List<Edge> edges)
	{
		Set<Vertex> vertices = new LinkedHashSet<Vertex>();
		for (Edge e : edges)
			vertices.addAll(e.vertices);
		Vertex[] vs = new Vertex[vertices.size()];
		return Arrays.asList(vertices.toArray(vs));
	}

	private Vertex findOrRegisterVertex(Vertex vertex)
	{
		for (Vertex e : vertices)
			if (e.isSame(vertex))
				return e;
		vertices.add(vertex);
		return vertex;
	}

	private Edge findEdge(int id)
	{
		for (Edge e : edgeList)
			if (e.edgeId == id)
				return e;
		throw new RuntimeException("Not found edge: edgeId=" + id);
	}

	private Edge createNewEdge(List<Vertex> vertices, int id)
	{
		for (Edge e : edgeList)
			if (e.isConnectingEdge(vertices))
				throw new RuntimeException("Krawedz o V: " + vertices + " juz jest [id=" + e.edgeId + " - " + e.vertices + "]");
		Edge e = new Edge(vertices, id);
		edgeList.add(e);
		return e;
	}

}
