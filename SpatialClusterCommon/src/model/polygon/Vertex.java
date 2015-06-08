package model.polygon;

import java.util.Random;

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

	public boolean isSame(Vertex vertex)
	{
		if (this.x == vertex.x && this.y == vertex.y)
			return true;
		return false;
	}

	public double getDistanceNotSquaredFrom(Vertex v)
	{
		return (this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y);
	}

	public static Vertex generateRandom(int xmax, int ymax)
	{
		return new Vertex(random.nextInt(xmax), random.nextInt(ymax));
	}

	@Override
	public String toString()
	{
		return "[" + x + "x" + y + "]";
	}
}
