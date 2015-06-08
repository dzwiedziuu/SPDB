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
		Assert.assertFalse(polygon.isInside(new Vertex(0, 0)));
		Assert.assertFalse(polygon.isInside(new Vertex(10, 10)));
		Assert.assertFalse(polygon.isInside(new Vertex(0, 5)));
		Assert.assertTrue(polygon.isInside(new Vertex(1, 1)));
	}
}
