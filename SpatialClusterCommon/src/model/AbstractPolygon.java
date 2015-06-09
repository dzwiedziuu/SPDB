package model;

import java.util.List;

import model.polygon.Edge;

public interface AbstractPolygon
{
	public double getArea();

	public boolean isNeighbour(AbstractPolygon otherPolygon);

	public List<Edge> getEdges();
}
