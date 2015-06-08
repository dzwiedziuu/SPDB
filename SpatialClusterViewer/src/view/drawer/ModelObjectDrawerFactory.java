package view.drawer;

import model.ModelObject;
import model.point.ModelPoint;
import model.polygon.Polygon;

public class ModelObjectDrawerFactory
{
	public static ModelObjectDrawer create(ModelObject modelObject)
	{
		if (modelObject instanceof ModelPoint)
			return new ModelPointDrawer((ModelPoint) modelObject);
		if (modelObject instanceof Polygon)
			return new ModelPolygonDrawer((Polygon) modelObject);
		throw new UnsupportedClassVersionError();
	}
}
