package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import model.polygon.Edge;
import model.polygon.Polygon;
import model.polygon.Vertex;

public class PolygonGenerator extends PointGenerator
{
	public List<Polygon> polygons = new LinkedList<Polygon>();
	public List<Edge> edges = new LinkedList<Edge>();

	@Override
	public void generate(String oFile, int n, int xmax, int ymax) throws IOException
	{
		Vertex v1 = new Vertex(0, 0);
		Vertex v2 = new Vertex(0, ymax);
		Vertex v3 = new Vertex(xmax, ymax);
		Vertex v4 = new Vertex(xmax, 0);

		Vertex firstPoint = generateFirstPoint(xmax, ymax);
		createPolygonsFromVertices(Arrays.asList(v1, v2, v3, v4), firstPoint);

		for (int i = 4; i < n; i++)
		{
			Polygon polygonToSplit = null;
			for (Polygon p : polygons)
				if (p.isPolygonSplitable())
					polygonToSplit = p;
			if (polygonToSplit != null)
			{
				dropPolygon(polygonToSplit);
				for (List<Vertex> triples : splitPolygonOnHalf(polygonToSplit))
					createPolygon(triples);
				continue;
			}
			// nie znaleziono trojkata rozwartego
			Vertex newPoint = findNewPoint(xmax, ymax);
			Polygon polygon = findPolygonOfPoint(newPoint);
			splitPolygon(polygon, newPoint);
			i = i + 1;
		}

		writeStateToFile(oFile);
	}

	protected Vertex generateFirstPoint(int xmax, int ymax)
	{
		return Vertex.generateRandom(xmax, ymax);
	}

	protected Vertex findNewPoint(int xmax, int ymax)
	{
		Vertex newPoint = Vertex.generateRandom(xmax, ymax);
		Polygon polygon = findPolygonOfPoint(newPoint);
		newPoint = correctPoint(newPoint, polygon);
		return newPoint;
	}

	private void writeStateToFile(String oFile) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(oFile)));
		for (Edge e : edges)
			bw.write(e.edgeId + " " + e.vertices.get(0).x + " " + e.vertices.get(0).y + " " + e.vertices.get(1).x + " " + e.vertices.get(1).y + "\n");
		bw.write("\n");
		for (Polygon p : polygons)
		{
			bw.write("" + p.polygonId + " " + 0);
			for (Edge e : p.edges)
				bw.write(" " + e.edgeId);
			bw.write("\n");
		}
		bw.close();
	}

	protected Vertex correctPoint(Vertex newPoint, Polygon polygon)
	{
		Vertex result = newPoint;
		Vertex v = polygon.getCenterOfGravity();
		double minDist = result.getDistanceNotSquaredFrom(v);
		for (int i = 0; i < 5; i++)
		{
			Vertex rand = polygon.getRandomPointFromInsidePolygon();
			double dist = rand.getDistanceNotSquaredFrom(v);
			if (dist < minDist)
			{
				minDist = dist;
				result = rand;
			}
		}
		return result;
	}

	protected Polygon findPolygonOfPoint(Vertex newPoint)
	{
		for (Polygon p : polygons)
			if (p.isInside(newPoint))
				return p;
		return null;
	}

	private void splitPolygon(Polygon polygon, Vertex newPoint)
	{
		dropPolygon(polygon);
		createPolygonsFromVertices(polygon.vertices, newPoint);
	}

	private void createPolygonsFromVertices(List<Vertex> oldVertices, Vertex newPoint)
	{
		for (List<Vertex> newPoligonVertices : splitToNeighbourPairs(oldVertices))
		{
			newPoligonVertices = new LinkedList<Vertex>(newPoligonVertices);
			newPoligonVertices.add(newPoint);
			// for (List<Vertex> splitedVertices :
			// splitIfNeeded(newPoligonVertices))
			// createPolygon(splitedVertices);
			createPolygon(newPoligonVertices);
		}
	}

	private List<List<Vertex>> splitPolygonOnHalf(Polygon polygon)
	{
		Edge maxEdge = polygon.findLongestEdge();
		Vertex otherV = null;
		for (Vertex v : polygon.vertices)
			if (!maxEdge.containsVertex(v))
				otherV = v;
		Vertex halfVertex = maxEdge.getSplitPoint(otherV);
		return Arrays.asList(Arrays.asList(maxEdge.vertices.get(0), otherV, halfVertex), Arrays.asList(maxEdge.vertices.get(1), otherV, halfVertex));
	}

	private void dropPolygon(Polygon polygon)
	{
		polygons.remove(polygon);
		for (Edge e : polygon.edges)
			e.polygons.remove(polygon);
	}

	private Polygon createPolygon(List<Vertex> vertices)
	{
		Polygon polygon = new Polygon(vertices);
		for (List<Vertex> pair : splitToNeighbourPairs(vertices))
		{
			Edge e = findOrCreateEdge(pair);
			e.polygons.add(polygon);
			polygon.edges.add(e);
		}
		polygons.add(polygon);
		return polygon;
	}

	private List<List<Vertex>> splitToNeighbourPairs(List<Vertex> vertices)
	{
		List<List<Vertex>> result = new LinkedList<List<Vertex>>();
		for (int i = 0; i < vertices.size() - 1; i++)
			result.add(Arrays.asList(vertices.get(i), vertices.get(i + 1)));
		result.add(Arrays.asList(vertices.get(vertices.size() - 1), vertices.get(0)));
		return result;
	}

	private Edge findOrCreateEdge(List<Vertex> vertices)
	{
		for (Edge e : edges)
			if (e.isConnectingEdge(vertices))
				return e;
		Edge e = new Edge(vertices);
		edges.add(e);
		return e;
	}
}
