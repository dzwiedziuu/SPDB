package predicate;

import java.util.List;

import algorithm.ModelObjectWrapper;

/*
 * interfejs predykatu gêstoœci
 */
public interface DensePredicate
{
	public boolean isDenseEnough(List<ModelObjectWrapper> list);
}
