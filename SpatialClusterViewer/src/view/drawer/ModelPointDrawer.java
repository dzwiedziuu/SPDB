package view.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import model.ModelObject;
import model.point.ModelPoint;

public class ModelPointDrawer implements ModelObjectDrawer
{
	private static final int DIAMETER = 5;
	private static final int REGION_DIAMETER = 80;

	private ModelPoint mp;

	public ModelPointDrawer(ModelPoint modelObject)
	{
		this.mp = modelObject;
	}

	@Override
	public void drawObject(Graphics g)
	{
		g.fillOval(mp.getX() - DIAMETER / 2, mp.getY() - DIAMETER / 2, DIAMETER, DIAMETER);
		if (mp.getClusterId() != null)
		{
			g.setColor(Color.BLACK);
			g.drawOval(mp.getX() - REGION_DIAMETER / 2, mp.getY() - REGION_DIAMETER / 2, REGION_DIAMETER, REGION_DIAMETER);
		}
	}

	public ModelObject getModelObject()
	{
		return mp;
	}

	@Override
	public ModelObject findClosest(List<ModelObjectDrawer> list, int x, int y)
	{
		double minDist = Double.MAX_VALUE;
		ModelPointDrawer minMp = null;
		for (ModelObjectDrawer modelObjectDrawer : list)
		{
			ModelPointDrawer modelPointDrawer = (ModelPointDrawer) modelObjectDrawer;
			double curDist = (x - modelPointDrawer.mp.getX()) * (x - modelPointDrawer.mp.getX()) + (y - modelPointDrawer.mp.getY())
					* (y - modelPointDrawer.mp.getY());
			if (curDist < minDist)
			{
				minMp = modelPointDrawer;
				minDist = curDist;
			}
		}
		return minMp.getModelObject();
	}
}
