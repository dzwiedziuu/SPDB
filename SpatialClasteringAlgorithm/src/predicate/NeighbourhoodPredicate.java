package predicate;

import algorithm.ModelObjectWrapper;

/*
 * interfejs predykatu s�siedztwa
 */
public interface NeighbourhoodPredicate
{
	public boolean isNeighbour(ModelObjectWrapper modelObject1, ModelObjectWrapper modelObject2);
}
