package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import model.ModelObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;
import domain.DomainObject.Status;

public abstract class AbstractGDBSCANAlgorithm
{
	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	private List<ModelObjectWrapper> list;

	protected Integer densepredicatevalue;

	public AbstractGDBSCANAlgorithm(Integer densepredicatevalue)
	{
		this.densepredicatevalue = densepredicatevalue;
	}

	public AbstractGDBSCANAlgorithm loadList(List<? extends ModelObject> list)
	{
		this.list = createWrapperList(list);
		return this;
	}

	private List<ModelObjectWrapper> createWrapperList(List<? extends ModelObject> list)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObject mp : list)
			result.add(new ModelObjectWrapper(mp));
		return result;
	}

	public void setClusters()
	{
		int nextClusterId = 1;
		for (ModelObjectWrapper t : list)
		{
			if (t.getStatus().equals(Status.VISITED))
				continue;
			t.setStatus(Status.VISITED);
			logger.trace("Przetwarzanie id: " + t.getId() + "[" + t.toString() + "]");
			List<ModelObjectWrapper> neighbours = getNeighbours(t);
			logger.trace("Sasiedzi " + t.getId() + ": " + neighbours.stream().map(tt -> "" + tt.getId()).collect(Collectors.joining(",")));
			if (!getDensePredicate().isDenseEnough(neighbours))
				t.setStatus(Status.NOISE);
			else
				expandCluster(t, neighbours, nextClusterId++);
		}
	}

	private void expandCluster(ModelObjectWrapper point, List<ModelObjectWrapper> neighbours, Integer cluster)
	{
		point.setClusterId(cluster);
		List<ModelObjectWrapper> neighboursOfNeighbours = new LinkedList<ModelObjectWrapper>();
		for (ModelObjectWrapper neighbour : neighbours)
		{
			if (!neighbour.getStatus().equals(Status.VISITED))
			{
				neighbour.setStatus(Status.VISITED);
				List<ModelObjectWrapper> neighboursOfNeighbour = getNeighbours(neighbour);
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

	private List<ModelObjectWrapper> removeVisited(List<ModelObjectWrapper> neighboursOfNeighbours, Integer clusterId)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObjectWrapper p : neighboursOfNeighbours)
			if (!p.getStatus().equals(Status.VISITED) || p.getClusterId() != clusterId)
				result.add(p);
		return result;
	}

	private List<ModelObjectWrapper> getNeighbours(ModelObjectWrapper point)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObjectWrapper potentialNeighbour : list)
			if (point.getId() != potentialNeighbour.getId() && getNeighbourhoodPredicate().isNeighbour(point, potentialNeighbour))
				result.add(potentialNeighbour);
		result.add(point);
		return result;
	}

	protected abstract DensePredicate getDensePredicate();

	protected abstract NeighbourhoodPredicate getNeighbourhoodPredicate();
}
