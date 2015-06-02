package predicate;

import domain.DomainObject;

public interface NeighbourhoodPredicate<T extends DomainObject>
{
	public boolean isNeighbour(T point1, T point2);
}
