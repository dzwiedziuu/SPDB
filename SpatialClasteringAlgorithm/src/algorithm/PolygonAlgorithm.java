package algorithm;

import java.util.List;

import model.AbstractPolygon;
import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;

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

}
