package predicate;

import java.util.List;

import algorithm.ModelObjectWrapper;

public interface DensePredicate
{
	public boolean isDenseEnough(List<ModelObjectWrapper> list);
}
