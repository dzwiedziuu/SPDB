package main;

import java.util.LinkedList;
import java.util.List;

import model.polygon.Polygon;
import model.polygon.Vertex;
import utils.randomSet.RandomPrioritySet;
import utils.randomSet.RandomPrioritySetValue;

public class BeautyPolygonGenerator extends PolygonGenerator
{
	protected Vertex findNewPoint(int xmax, int ymax)
	{
		Polygon polygon = findRandomBestPolygon();
		Vertex newPoint = correctPoint(polygon.getRandomPointFromInsidePolygon(), polygon);
		return newPoint;
	}

	private Polygon findRandomBestPolygon()
	{
		List<RandomPrioritySetValue<Polygon>> list = new LinkedList<RandomPrioritySetValue<Polygon>>();
		for (Polygon p : polygons)
			list.add(new RandomPrioritySetValue<Polygon>(p, p.getArea()));
		RandomPrioritySet<Polygon> rps = new RandomPrioritySet<Polygon>(list);
		return rps.getRandomValue();
	}

	@Override
	protected Vertex generateFirstPoint(int xmax, int ymax)
	{
		return new Vertex(xmax / 2, ymax / 2);
	}
}
