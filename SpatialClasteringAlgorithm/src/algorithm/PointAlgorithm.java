package algorithm;

import java.util.List;

import model.point.ModelPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;

/*
 * implementacja algorytmu GDBSCAN dla punktów
 */
public class PointAlgorithm extends AbstractGDBSCANAlgorithm
{
	public PointAlgorithm(Integer densepredicatevalue)
	{
		super(densepredicatevalue);
	}

	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	@Override
	protected DensePredicate getDensePredicate()
	{
		return new DensePredicate()
		{
			@Override
			public boolean isDenseEnough(List<ModelObjectWrapper> list)
			{
				return list.size() >= densepredicatevalue;
			}
		};
	}

	@Override
	protected NeighbourhoodPredicate getNeighbourhoodPredicate()
	{
		return new NeighbourhoodPredicate()
		{
			private static final int DIST = 40;

			@Override
			public boolean isNeighbour(ModelObjectWrapper point1, ModelObjectWrapper point2)
			{
				ModelPoint modelPoint1 = (ModelPoint) point1.getWrappedInstance();
				ModelPoint modelPoint2 = (ModelPoint) point2.getWrappedInstance();
				boolean result = Math.sqrt(Math.pow(modelPoint1.getX() - modelPoint2.getX(), 2) + Math.pow(modelPoint1.getY() - modelPoint2.getY(), 2)) < DIST;
				if (result)
					logger.trace(point1.getId() + " jest sasiadem " + point2.getId());
				return result;
			}
		};
	}
}
