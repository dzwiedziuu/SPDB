package main;

import java.util.Arrays;

import model.polygon.Edge;
import model.polygon.Vertex;

import org.junit.Assert;
import org.junit.Test;

public class RectangleGeneratorTest
{

	@Test
	public void test()
	{
		RectangleGenerator rectangleGenerator = new RectangleGenerator();
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(0, 0), new Vertex(500, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(0, 0), new Vertex(240, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(240, 0), new Vertex(500, 0))));
		rectangleGenerator.normalizeEdges();
		Assert.assertEquals(2, rectangleGenerator.edges.size());
	}

	@Test
	public void test2()
	{
		RectangleGenerator rectangleGenerator = new RectangleGenerator();
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(0, 0), new Vertex(500, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(0, 0), new Vertex(240, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(500, 0), new Vertex(240, 0))));
		rectangleGenerator.normalizeEdges();
		Assert.assertEquals(2, rectangleGenerator.edges.size());
	}

	@Test
	public void test3()
	{
		RectangleGenerator rectangleGenerator = new RectangleGenerator();
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(0, 0), new Vertex(200, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(100, 0), new Vertex(240, 0))));
		rectangleGenerator.edges.add(new Edge(Arrays.asList(new Vertex(300, 0), new Vertex(440, 0))));
		rectangleGenerator.normalizeEdges();
		Assert.assertEquals(5, rectangleGenerator.edges.size());
	}
}
