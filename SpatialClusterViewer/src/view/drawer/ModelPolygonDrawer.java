package view.drawer;

import java.awt.Graphics;
import java.util.List;

import model.ModelObject;
import model.polygon.Edge;
import model.polygon.Polygon;
import model.polygon.Vertex;

public class ModelPolygonDrawer implements ModelObjectDrawer
{
	private Polygon polygon;

	public ModelPolygonDrawer(Polygon modelObject)
	{
		this.polygon = modelObject;
	}

	@Override
	public void drawObject(Graphics g)
	{
		for (Edge e : polygon.edges)
			g.drawLine(e.vertices.get(0).x, e.vertices.get(0).y, e.vertices.get(1).x, e.vertices.get(1).y);
	}

	@Override
	public ModelObject findClosest(List<ModelObjectDrawer> list, int x, int y)
	{
		for (ModelObjectDrawer mod : list)
			if (((Polygon) mod.getModelObject()).isInside(new Vertex(x, y)))
				return mod.getModelObject();
		return null;
	}

	@Override
	public ModelObject getModelObject()
	{
		return polygon;
	}

}
