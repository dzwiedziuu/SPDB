package model.polygon;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Edge
{
	public List<Vertex> vertices;

	public List<Polygon> polygons = new LinkedList<Polygon>();

	public int edgeId;

	private static int nextEdgeId = 0;

	public Edge(List<Vertex> vertices)
	{
		this(vertices, nextEdgeId++);
	}

	public Vertex takeOrthogonalProjection(Vertex pointToChange)
	{
		double a = ((double) this.getSecondEnd().y - this.getFirstEnd().y) / (this.getSecondEnd().x - this.getFirstEnd().x);
		if (this.getSecondEnd().x == this.getFirstEnd().x)
			return new Vertex(new Long(Math.round(this.getSecondEnd().x)).intValue(), new Long(Math.round(pointToChange.y)).intValue());
		if (this.getSecondEnd().y == this.getFirstEnd().y)
			return new Vertex(new Long(Math.round(pointToChange.x)).intValue(), new Long(Math.round(this.getSecondEnd().y)).intValue());
		double b = this.getFirstEnd().y - ((double) this.getFirstEnd().x) * a;
		double prostA = -1 / a;
		double prostB = pointToChange.y - ((double) pointToChange.x) * prostA;
		double newX = (prostB - b) / (a - prostA);
		double newY = a * newX + b;
		return new Vertex(new Long(Math.round(newX)).intValue(), new Long(Math.round(newY)).intValue());
	}

	public Vertex getFirstEnd()
	{
		return vertices.get(0);
	}

	public Vertex getSecondEnd()
	{
		return vertices.get(1);
	}

	public Edge(List<Vertex> vertices, int edgeId)
	{
		this.edgeId = edgeId;
		this.vertices = vertices;
	}

	public double getLength()
	{
		return Math.sqrt(vertices.get(0).getDistanceNotSquaredFrom(vertices.get(1)));
	}

	public boolean isConnectingEdge(List<Vertex> vertices)
	{
		for (Vertex v : vertices)
			if (!this.containsVertex(v))
				return false;
		return true;
	}

	public boolean containsVertex(Vertex v)
	{
		return this.vertices.contains(v);
	}

	public Vertex getSplitPoint(Vertex vertex)
	{
		// int x = (vertices.get(0).x + vertices.get(1).x) / 2;
		// int y = (vertices.get(0).y + vertices.get(1).y) / 2;
		// return new Vertex(x, y);
		return takeOrthogonalProjection(vertex);
	}

	@Override
	public String toString()
	{
		return edgeId + ": " + Arrays.toString(vertices.toArray());
	}
}
