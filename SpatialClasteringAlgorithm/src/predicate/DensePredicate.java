package predicate;

import java.util.List;

import algorithm.ModelObjectWrapper;

/*
 * interfejs predykatu g�sto�ci
 */
public interface DensePredicate
{
	public boolean isDenseEnough(List<ModelObjectWrapper> list);
}
