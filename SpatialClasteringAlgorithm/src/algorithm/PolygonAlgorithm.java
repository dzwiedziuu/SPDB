package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.AbstractPolygon;
import model.polygon.Edge;
import model.polygon.Polygon;
import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;

/*
 * implementacja algorytmu GDBSCAN dla trójk¹tów
 */
public class PolygonAlgorithm extends AbstractGDBSCANAlgorithm
{
	public PolygonAlgorithm(Integer densepredicatevalue)
	{
		super(densepredicatevalue);
	}

	@Override
	protected DensePredicate getDensePredicate()
	{
		return new DensePredicate()
		{
			@Override
			public boolean isDenseEnough(List<ModelObjectWrapper> list)
			{
				double totalArea = 0;
				for (ModelObjectWrapper mow : list)
					totalArea += ((AbstractPolygon) mow.getWrappedInstance()).getArea();
				return totalArea > densepredicatevalue;
			}
		};
	}

	@Override
	protected NeighbourhoodPredicate getNeighbourhoodPredicate()
	{
		return new NeighbourhoodPredicate()
		{
			@Override
			public boolean isNeighbour(ModelObjectWrapper modelObject1, ModelObjectWrapper modelObject2)
			{
				AbstractPolygon p1 = (AbstractPolygon) modelObject1.getWrappedInstance();
				AbstractPolygon p2 = (AbstractPolygon) modelObject2.getWrappedInstance();
				return p1.isNeighbour(p2);
			}
		};
	}

	@Override
	protected List<ModelObjectWrapper> getNeighbours(ModelObjectWrapper point, Map<Integer, ModelObjectWrapper> map2)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		Polygon polygon = (Polygon) point.getWrappedInstance();
		for (Edge e : polygon.edges)
			for (Polygon p : e.polygons)
				result.add(new ModelObjectWrapper(p));
		return result;
	}
}
