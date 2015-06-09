package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.polygon.Edge;
import rectangle.Rectangle;

/*
 * implementacja algorytmu GDBSCAN dla prostok¹tów
 */
public class RectangleAlgorithm extends PolygonAlgorithm
{
	public RectangleAlgorithm(Integer densepredicatevalue)
	{
		super(densepredicatevalue);
	}

	@Override
	protected List<ModelObjectWrapper> getNeighbours(ModelObjectWrapper point, Map<Integer, ModelObjectWrapper> map2)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		Rectangle rectangle = (Rectangle) point.getWrappedInstance();
		for (Edge e : rectangle.edges)
		{
			Rectangle r = e.getOtherRectangle(rectangle);
			if (r == null)
				continue;
			ModelObjectWrapper mow = findWrappedObject(r, map2);
			if (getNeighbourhoodPredicate().isNeighbour(point, mow))
				result.add(mow);
		}
		result.add(point);
		return result;
	}

	private ModelObjectWrapper findWrappedObject(Rectangle r, Map<Integer, ModelObjectWrapper> map2)
	{
		return map2.get(r.getId());
	}
}
