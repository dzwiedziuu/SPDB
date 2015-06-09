package utils.randomSet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * klasa pozwalajaca wybrac w losowy sposob element ze zbioru, proporcjonalnie do wspolczynnika prawdopodobienstwa
 */
public class RandomPrioritySet<T>
{
	private static Random rand = new Random();

	private List<RandomPrioritySetValue<T>> list = new LinkedList<RandomPrioritySetValue<T>>();
	double totalValue = 0;

	public RandomPrioritySet(Iterable<RandomPrioritySetValue<T>> iterable)
	{
		for (RandomPrioritySetValue<T> v : iterable)
			list.add(v);
		Collections.sort(list, new RandomPrioritySetValue.RandomPrioritySetValueComparator<T>());
		for (RandomPrioritySetValue<T> v : list)
			totalValue += v.getPriorityValue();
	}

	public T getRandomValue()
	{
		double randVal = rand.nextDouble() * totalValue;
		for (RandomPrioritySetValue<T> v : list)
		{
			randVal -= v.getPriorityValue();
			if (randVal <= 0)
				return v.getObject();
		}
		return null;
	}
}
