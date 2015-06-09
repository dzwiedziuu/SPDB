package predicate;

import algorithm.ModelObjectWrapper;

public interface NeighbourhoodPredicate
{
	public boolean isNeighbour(ModelObjectWrapper modelObject1, ModelObjectWrapper modelObject2);
}
