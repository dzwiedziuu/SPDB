package model.polygon;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.AbstractPolygon;
import model.ModelObject;

/*
 * klasa reprezentujaca trójk¹t
 */
public class Polygon implements ModelObject, AbstractPolygon
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

	/*
	 * czy punkt znajduje sie wewnatrz figury
	 */
	public boolean isInside(Vertex vertex)
	{
		return isInside(vertex, 0);
	}

	@Deprecated
	public boolean isInside(Vertex vertex, int padding)
	{
		java.awt.Polygon awtPolygon = getAWTPolygon(padding);
		return awtPolygon.contains(vertex.x, vertex.y);
	}

	/*
	 * zwroc prostokat zawierajacy figure
	 */
	public List<Vertex> getBoundingRect()
	{
		java.awt.Polygon awtPolygon = getAWTPolygon();
		Rectangle r = awtPolygon.getBounds();
		Vertex v1 = new Vertex((int) r.getMinX(), (int) r.getMinY());
		Vertex v2 = new Vertex((int) r.getMaxX(), (int) r.getMaxY());
		return Arrays.asList(v1, v2);
	}

	private static Random random = new Random();

	/*
	 * znajdz losowy punkt z wnetrza tej figury
	 */
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

	/*
	 * znajdz srodek ciezkosci
	 */
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

	@Deprecated
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

	/*
	 * znajdz pole
	 */
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

	/*
	 * czy trojkat powinien zostac podzielony na 2 (bo jest rozwarty)
	 */
	public boolean isPolygonSplitable()
	{
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

	/*
	 * znajdz najdluzszy bok
	 */
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
		return "id=" + getId() + ", clusterID=" + clusterId + Arrays.toString(vertices.toArray());
	}

	/*
	 * czy trojkat w argumencie jest sasiadem tego
	 */
	public boolean isNeighbour(AbstractPolygon otherPolygon)
	{
		for (Edge e : this.edges)
			for (Edge eo : otherPolygon.getEdges())
				if (e == eo)
					return true;
		return false;
	}

	@Override
	public List<Edge> getEdges()
	{
		return edges;
	}
}
