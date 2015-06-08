package model.polygon;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.ModelObject;

public class Polygon implements ModelObject
{
	public List<Vertex> vertices;

	public List<Edge> edges = new LinkedList<Edge>();

	public int polygonId;

	private Integer clusterId;

	public static int nextPolygonId = 0;

	public Polygon(List<Vertex> vertices)
	{
		this(vertices, nextPolygonId++);
	}

	public Polygon(List<Vertex> vertices, int id)
	{
		this.polygonId = nextPolygonId++;
		this.vertices = vertices;
	}

	public boolean isInside(Vertex vertex)
	{
		java.awt.Polygon awtPolygon = getAWTPolygon();
		return awtPolygon.contains(vertex.x, vertex.y);
	}

	public List<Vertex> getBoundingRect()
	{
		java.awt.Polygon awtPolygon = getAWTPolygon();
		Rectangle r = awtPolygon.getBounds();
		Vertex v1 = new Vertex((int) r.getMinX(), (int) r.getMinY());
		Vertex v2 = new Vertex((int) r.getMaxX(), (int) r.getMaxY());
		return Arrays.asList(v1, v2);
	}

	private static Random random = new Random();

	public Vertex getRandomPointFromInsidePolygon()
	{
		List<Vertex> vs = getBoundingRect();
		Vertex randomV = null;
		do
		{
			int x = random.nextInt(vs.get(1).x - vs.get(0).x);
			int y = random.nextInt(vs.get(1).y - vs.get(0).y);
			randomV = new Vertex(vs.get(0).x + x, vs.get(0).y + y);
		} while (!this.isInside(randomV));
		return randomV;
	}

	public Vertex getCenterOfGravity()
	{
		int totalX = 0, totalY = 0;
		for (Vertex v : vertices)
		{
			totalX += v.x;
			totalY += v.y;
		}
		return new Vertex(totalX / vertices.size(), totalY / vertices.size());
	}

	private java.awt.Polygon getAWTPolygon()
	{
		return getAWTPolygon(0);
	}

	private java.awt.Polygon getAWTPolygon(int padding)
	{
		int[] xs = new int[vertices.size()];
		int[] ys = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); i++)
		{
			Vertex v = getBetterVertex(vertices, padding, i);
			xs[i] = v.x;
			ys[i] = v.y;
		}
		return new java.awt.Polygon(xs, ys, vertices.size());
	}

	private Vertex getBetterVertex(List<Vertex> list, int padding, int index)
	{
		return list.get(index);
	}

	@Override
	public int getId()
	{
		return polygonId;
	}

	@Override
	public Integer getClusterId()
	{
		return clusterId;
	}

	@Override
	public void setClusterId(Integer clusterId)
	{
		this.clusterId = clusterId;
	}

	public double getArea()
	{

		double p = 0;
		for (Edge e : edges)
			p += e.getLength();
		p = p / 2;
		double area = p;
		for (Edge e : edges)
			area = area * (p - e.getLength());
		area = Math.sqrt(area);
		return area;
	}

	public boolean isPolygonSplitable()
	{
		// Edge maxEdge = findLongestEdge();
		// double otherSum = 0;
		// for (Edge e : edges)
		// if (e != maxEdge)
		// otherSum += Math.pow(e.getLength(), 2);
		// otherSum = Math.sqrt(otherSum);
		// if (maxEdge.getLength() > otherSum)
		// return true;
		Edge[] arr = new Edge[edges.size()];
		edges.toArray(arr);
		Arrays.sort(arr, new Comparator<Edge>()
		{
			@Override
			public int compare(Edge o1, Edge o2)
			{
				return Double.compare(o1.getLength(), o2.getLength());
			}
		});
		double cos = Math.cos(arr[arr.length - 1].getLength() / arr[arr.length - 2].getLength());
		return cos < -0.05;
	}

	public Edge findLongestEdge()
	{
		double maxE = Double.MIN_VALUE;
		Edge maxEdge = null;
		for (Edge e : edges)
			if (maxE < e.getLength())
			{
				maxE = e.getLength();
				maxEdge = e;
			}
		return maxEdge;
	}

	@Override
	public String toString()
	{
		return "id=" + getId() + Arrays.toString(vertices.toArray());
	}
}
