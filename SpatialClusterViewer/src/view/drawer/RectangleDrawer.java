package view.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import model.ModelObject;
import model.polygon.Vertex;
import rectangle.Rectangle;

public class RectangleDrawer implements ModelObjectDrawer
{
	private Rectangle rectangle;

	public RectangleDrawer(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}

	@Override
	public void drawObject(Graphics g)
	{
		g.fillRect(rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
		g.setColor(Color.DARK_GRAY);
		g.drawRect(rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public ModelObject findClosest(List<ModelObjectDrawer> list, int x, int y)
	{
		for (ModelObjectDrawer mod : list)
		{
			Rectangle r = (Rectangle) mod.getModelObject();
			if (r.isInside(new Vertex(x, y)))
				return r;
		}
		return null;
	}

	@Override
	public ModelObject getModelObject()
	{
		return rectangle;
	}
}
