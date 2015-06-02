package algorithm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;

public class PointAlgorithm extends AbstractGDBSCANAlgorithm<AlgorithmPointWrapper>
{
	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	private DensePredicate<AlgorithmPointWrapper> densePredicate = new DensePredicate<AlgorithmPointWrapper>()
	{
		private static final int CARD = 7;

		@Override
		public boolean isDenseEnough(List<AlgorithmPointWrapper> list)
		{
			return list.size() >= CARD;
		}
	};

	private NeighbourhoodPredicate<AlgorithmPointWrapper> neighbourhoodPredicate = new NeighbourhoodPredicate<AlgorithmPointWrapper>()
	{
		private static final int DIST = 40;

		@Override
		public boolean isNeighbour(AlgorithmPointWrapper point1, AlgorithmPointWrapper point2)
		{
			boolean result = Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2)) < DIST;
			if (result)
				logger.trace(point1.getId() + " jest sasiadem " + point2.getId());
			return result;
		}
	};

	@Override
	protected DensePredicate<AlgorithmPointWrapper> getDensePredicate()
	{
		return densePredicate;
	}

	@Override
	protected NeighbourhoodPredicate<AlgorithmPointWrapper> getNeighbourhoodPredicate()
	{
		return neighbourhoodPredicate;
	}
}
