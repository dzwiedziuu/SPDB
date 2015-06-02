package predicate;

import java.util.List;

import domain.DomainObject;

public interface DensePredicate<T extends DomainObject>
{
	public boolean isDenseEnough(List<T> list);
}
