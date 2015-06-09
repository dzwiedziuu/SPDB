package rectangle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.AbstractPolygon;
import model.ModelObject;
import model.polygon.Edge;
import model.polygon.Vertex;

public class Rectangle implements ModelObject, AbstractPolygon
{
	public List<Vertex> vertices = new LinkedList<Vertex>();

	public List<Edge> edges = new LinkedList<Edge>();

	public int polygonId;

	private Integer clusterId;

	public static int nextRectangleId = 0;

	public Rectangle(List<Vertex> vertices)
	{
		this(vertices, nextRectangleId++);
	}

	public Rectangle(List<Vertex> vertices, int id)
	{
		this.polygonId = id;
		this.vertices = vertices;
	}

	public int getMinX()
	{
		return Math.min(vertices.get(0).x, vertices.get(2).x);
	}

	public int getMinY()
	{
		return Math.min(vertices.get(0).y, vertices.get(2).y);
	}

	public int getWidth()
	{
		return Math.abs(vertices.get(2).x - vertices.get(0).x);
	}

	public int getHeight()
	{
		return Math.abs(vertices.get(2).y - vertices.get(0).y);
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

	public java.awt.Rectangle getAWTRectangle()
	{
		int minX = Math.min(vertices.get(0).x, vertices.get(2).x);
		int minY = Math.min(vertices.get(0).y, vertices.get(2).y);
		return new java.awt.Rectangle(minX, minY, getWidth(), getHeight());
	}

	public boolean isInside(Vertex v)
	{
		return getAWTRectangle().contains(v.x, v.y);
	}

	@Override
	public String toString()
	{
		return "ID=" + getId() + ", clusterID=" + getClusterId() + ", area=" + getArea() + ", v=" + Arrays.toString(vertices.toArray()) + ", n="
				+ getNeighbourRectanglesIDs() + ", e=" + Arrays.toString(edges.toArray());
	}

	public String getNeighbourRectanglesIDs()
	{
		StringBuffer sb = new StringBuffer("[");
		for (Edge e : edges)
			sb.append(e.getOtherRectangleID(this)).append(", ");
		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}

	private static Random random = new Random();

	public Vertex getLegalPoint(Vertex splitVertex)
	{
		while (!isVertexLegal(splitVertex))
		{
			int x = random.nextInt(getWidth());
			int y = random.nextInt(getHeight());
			splitVertex = new Vertex(x + getMinX(), y + getMinY());
		}
		return splitVertex;
	}

	public boolean isBigEnoughRect()
	{
		return getWidth() >= minDist * 2 && getHeight() >= minDist * 2;
	}

	private static final int minDist = 5;

	private boolean isVertexLegal(Vertex vertex)
	{
		if (Math.abs(vertices.get(0).x - vertex.x) < minDist)
			return false;
		if (Math.abs(vertices.get(2).x - vertex.x) < minDist)
			return false;
		if (Math.abs(vertices.get(0).y - vertex.y) < minDist)
			return false;
		if (Math.abs(vertices.get(2).y - vertex.y) < minDist)
			return false;
		return true;
	}

	@Override
	public double getArea()
	{
		return getWidth() * getHeight();
	}

	@Override
	public boolean isNeighbour(AbstractPolygon otherPolygon)
	{
		for (Edge e : this.edges)
			for (Edge eo : otherPolygon.getEdges())
				if (e == eo && e != null)
					return true;
		return false;
	}

	@Override
	public List<Edge> getEdges()
	{
		return edges;
	}
}
