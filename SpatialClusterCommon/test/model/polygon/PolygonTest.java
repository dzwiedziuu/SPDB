package model.polygon;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class PolygonTest
{

	@Test
	public void test()
	{
		Polygon polygon = new Polygon(Arrays.asList(new Vertex(0, 0), new Vertex(10, 0), new Vertex(10, 10), new Vertex(0, 10)));
		// Assert.assertFalse(polygon.isInside(new Vertex(0, 0)));
		// Assert.assertFalse(polygon.isInside(new Vertex(10, 10)));
		// Assert.assertFalse(polygon.isInside(new Vertex(0, 1)));
		// Assert.assertFalse(polygon.isInside(new Vertex(1, 1)));
		// Assert.assertFalse(polygon.isInside(new Vertex(3, 3)));
	}

	@Test
	public void test2()
	{
		Vertex v1 = new Vertex(0, 0), v2 = new Vertex(500, 0), v3 = new Vertex(0, 500);
		Polygon polygon = new Polygon(Arrays.asList(v1, v2, v3));
		polygon.edges.add(new Edge(Arrays.asList(v1, v2)));
		polygon.edges.add(new Edge(Arrays.asList(v2, v3)));
		polygon.edges.add(new Edge(Arrays.asList(v3, v1)));
		Assert.assertEquals(polygon.getArea(), 125000.0, 0.01);
	}
}
