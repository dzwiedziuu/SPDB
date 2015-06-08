package algorithm;

import java.util.List;

import model.point.ModelPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;

public class PointAlgorithm extends AbstractGDBSCANAlgorithm
{
	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	private DensePredicate<ModelObjectWrapper> densePredicate = new DensePredicate<ModelObjectWrapper>()
	{
		private static final int CARD = 7;

		@Override
		public boolean isDenseEnough(List<ModelObjectWrapper> list)
		{
			return list.size() >= CARD;
		}
	};

	private NeighbourhoodPredicate<ModelObjectWrapper> neighbourhoodPredicate = new NeighbourhoodPredicate<ModelObjectWrapper>()
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

	@Override
	protected DensePredicate<ModelObjectWrapper> getDensePredicate()
	{
		return densePredicate;
	}

	@Override
	protected NeighbourhoodPredicate<ModelObjectWrapper> getNeighbourhoodPredicate()
	{
		return neighbourhoodPredicate;
	}
}
