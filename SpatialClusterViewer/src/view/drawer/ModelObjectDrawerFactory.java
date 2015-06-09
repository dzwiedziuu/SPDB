package view.drawer;

import model.ModelObject;
import model.point.ModelPoint;
import model.polygon.Polygon;
import rectangle.Rectangle;

public class ModelObjectDrawerFactory
{
	public static ModelObjectDrawer create(ModelObject modelObject)
	{
		if (modelObject instanceof ModelPoint)
			return new ModelPointDrawer((ModelPoint) modelObject);
		if (modelObject instanceof Polygon)
			return new ModelPolygonDrawer((Polygon) modelObject);
		if (modelObject instanceof Rectangle)
			return new RectangleDrawer((Rectangle) modelObject);
		throw new UnsupportedClassVersionError();
	}
}
