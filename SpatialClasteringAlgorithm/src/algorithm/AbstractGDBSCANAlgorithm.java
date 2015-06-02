package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;
import domain.DomainObject;
import domain.DomainObject.Status;

public abstract class AbstractGDBSCANAlgorithm<T extends DomainObject>
{
	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	private List<T> list;

	public AbstractGDBSCANAlgorithm<T> loadList(List<T> list)
	{
		this.list = list;
		return this;
	}

	public void setClusters()
	{
		int nextClusterId = 1;
		for (T t : list)
		{
			if (t.getStatus().equals(Status.VISITED))
				continue;
			t.setStatus(Status.VISITED);
			logger.trace("Przetwarzanie id: " + t.getId() + "[" + t.toString() + "]");
			List<T> neighbours = getNeighbours(t);
			logger.trace("Sasiedzi " + t.getId() + ": " + neighbours.stream().map(tt -> "" + tt.getId()).collect(Collectors.joining(",")));
			if (!getDensePredicate().isDenseEnough(neighbours))
				t.setStatus(Status.NOISE);
			else
				expandCluster(t, neighbours, nextClusterId++);
		}
	}

	private void expandCluster(T point, List<T> neighbours, Integer cluster)
	{
		point.setClusterId(cluster);
		List<T> neighboursOfNeighbours = new LinkedList<T>();
		for (T neighbour : neighbours)
		{
			if (!neighbour.getStatus().equals(Status.VISITED))
			{
				neighbour.setStatus(Status.VISITED);
				List<T> neighboursOfNeighbour = getNeighbours(neighbour);
				if (getDensePredicate().isDenseEnough(neighboursOfNeighbour))
					neighboursOfNeighbours.addAll(neighboursOfNeighbour);
			}
			if (neighbour.getClusterId() == null)
				neighbour.setClusterId(cluster);
		}
		neighboursOfNeighbours = removeVisited(neighboursOfNeighbours, cluster);
		if (!neighboursOfNeighbours.isEmpty())
			expandCluster(point, neighboursOfNeighbours, cluster);
	}

	private List<T> removeVisited(List<T> neighboursOfNeighbours, Integer clusterId)
	{
		List<T> result = new LinkedList<T>();
		for (T p : neighboursOfNeighbours)
			if (!p.getStatus().equals(Status.VISITED) || p.getClusterId() != clusterId)
				result.add(p);
		return result;
	}

	private List<T> getNeighbours(T point)
	{
		List<T> result = new LinkedList<T>();
		for (T potentialNeighbour : list)
			if (point.getId() != potentialNeighbour.getId() && getNeighbourhoodPredicate().isNeighbour(point, potentialNeighbour))
				result.add(potentialNeighbour);
		result.add(point);
		return result;
	}

	protected abstract DensePredicate<T> getDensePredicate();

	protected abstract NeighbourhoodPredicate<T> getNeighbourhoodPredicate();
}
