package model.polygon;

import java.util.Random;

/*
 * klasa reprezentujaca punkt/wierzcholek
 */
public class Vertex
{
	public int x, y;
	static Random random = new Random();

	public Vertex(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	/*
	 * odleglosc od punktu (niespierwiastkowana)
	 */
	public double getDistanceNotSquaredFrom(Vertex v)
	{
		return (this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y);
	}

	/*
	 * znajdz losowy punkt z prostokata (0,0) - (xmax, ymax)
	 */
	public static Vertex generateRandom(int xmax, int ymax)
	{
		return new Vertex(random.nextInt(xmax), random.nextInt(ymax));
	}

	@Override
	public String toString()
	{
		return "[" + x + "x" + y + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vertex))
			return false;
		if (obj == null)
			return false;
		Vertex v = (Vertex) obj;
		if (v.x == this.x && v.y == this.y)
			return true;
		return false;
	}
}
