package model.polygon;

import java.util.LinkedList;
import java.util.List;

import rectangle.Rectangle;

/*
 * klasa reprezentujaca krawedz
 */
public class Edge
{
	public List<Vertex> vertices;

	public List<Polygon> polygons = new LinkedList<Polygon>();

	public List<Rectangle> rectangles = new LinkedList<Rectangle>();

	public int edgeId;

	private static int nextEdgeId = 0;

	public Edge(List<Vertex> vertices)
	{
		this(vertices, nextEdgeId++);
	}

	/*
	 * znajdz rzut wierzcholka na prosta zawierajaca ten odcinek
	 */
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
		return Math.sqrt(getFirstEnd().getDistanceNotSquaredFrom(getSecondEnd()));
	}

	/*
	 * czy wierzcholki sa tak naprawde wierzcholkami tej krawedzi
	 */
	public boolean isConnectingEdge(List<Vertex> vertices)
	{
		for (Vertex v : vertices)
			if (!this.containsVertex(v))
				return false;
		return true;
	}

	/*
	 * czy wierzcholek jest jednym z koncow krawedzi
	 */
	public boolean containsVertex(Vertex v)
	{
		for (Vertex ver : this.vertices)
			if (ver.equals(v))
				return true;
		return false;
	}

	/*
	 * znajdz drugi koniec krawedzi
	 */
	public Vertex getOtherVertex(Vertex v)
	{
		if (getFirstEnd().equals(v))
			return getSecondEnd();
		return getFirstEnd();
	}

	/*
	 * znajdz punkt podzialu odcinka (uzywany przy generowaniu modelu trojkatow)
	 */
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
		// return edgeId + ": " + Arrays.toString(vertices.toArray());
		return "edgeId=" + edgeId;
	}

	/*
	 * znajdz ID prostokata, z ktorym prostokat w argumencie dzieli ta krawedz
	 */
	public Integer getOtherRectangleID(Rectangle r)
	{
		Rectangle other = getOtherRectangle(r);
		return other == null ? null : other.getId();
	}

	/*
	 * znajdz prostokat, z ktorym prostokat w argumencie dzieli ta krawedz
	 */
	public Rectangle getOtherRectangle(Rectangle r)
	{
		for (Rectangle rr : rectangles)
			if (r.getId() != rr.getId())
				return rr;
		return null;
	}

	/*
	 * znajdz wspolczynnik a prostej zawierajacej ten odcinek
	 */
	public Double getA()
	{
		if (getFirstEnd().x == getSecondEnd().x)
			return Double.POSITIVE_INFINITY;
		if (getFirstEnd().y == getSecondEnd().y)
			return 0.0;
		return ((double) getFirstEnd().y - getSecondEnd().y) / (getFirstEnd().x - getSecondEnd().x);
	}

	/*
	 * znajdz wspolczynnik b prostej zawierajacej ten odcinek
	 */
	public Double getB()
	{
		Double a = getA();
		if (!Double.isFinite(a))
			return 0.0;
		return getFirstEnd().y - a * getFirstEnd().x;
	}

	/*
	 * czy ten odcinek zawiera odcinek w argumencie
	 */
	public boolean contains(Edge e)
	{
		if (!this.isOnSameLine(e))
			return false;
		int minX = Math.min(getFirstEnd().x, getSecondEnd().x);
		int maxX = Math.max(getFirstEnd().x, getSecondEnd().x);
		int minY = Math.min(getFirstEnd().y, getSecondEnd().y);
		int maxY = Math.max(getFirstEnd().y, getSecondEnd().y);
		for (Vertex v : e.vertices)
			if (v.x < minX || v.x > maxX || v.y < minY || v.y > maxY)
				return false;
		return true;
	}

	/*
	 * czy ten odcinek i odcinek w argumencie znajduja sie na tej samej prostej
	 */
	public boolean isOnSameLine(Edge e)
	{
		return this.getA().equals(e.getA()) && this.getB().equals(e.getB());
	}

	/*
	 * czy prosta w argumencie ma wspolny wierzcholek z ta prosta
	 */
	public boolean touch(Edge e)
	{
		if (this.containsVertex(e.getFirstEnd()) || this.containsVertex(e.getSecondEnd()))
			return true;
		return false;
	}

	/*
	 * zwraca wierzcholek, ktory znajduje sie blizej punktu
	 */
	public Vertex getClosestVertex(Vertex startingVertex)
	{
		double distFirst = getFirstEnd().getDistanceNotSquaredFrom(startingVertex);
		double distSecond = getSecondEnd().getDistanceNotSquaredFrom(startingVertex);
		return distFirst < distSecond ? getFirstEnd() : getSecondEnd();
	}
}
