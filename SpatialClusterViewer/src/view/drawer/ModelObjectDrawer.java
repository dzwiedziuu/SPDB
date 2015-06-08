package view.drawer;

import java.awt.Graphics;
import java.util.List;

import model.ModelObject;

public interface ModelObjectDrawer
{
	public void drawObject(Graphics g);

	public ModelObject findClosest(List<ModelObjectDrawer> list, int x, int y);

	public ModelObject getModelObject();
}
