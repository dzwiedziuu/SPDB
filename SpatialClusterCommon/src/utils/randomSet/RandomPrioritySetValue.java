package utils.randomSet;

import java.util.Comparator;

public class RandomPrioritySetValue<T>
{
	public static class RandomPrioritySetValueComparator<T> implements Comparator<RandomPrioritySetValue<T>>
	{
		@Override
		public int compare(RandomPrioritySetValue<T> arg0, RandomPrioritySetValue<T> arg1)
		{
			return Double.compare(arg0.getPriorityValue(), arg1.getPriorityValue());
		}
	}

	private T object;
	private double priorityValue;

	public RandomPrioritySetValue(T object, double priorityValue)
	{
		this.object = object;
		this.priorityValue = priorityValue;
	}

	public double getPriorityValue()
	{
		return priorityValue;
	}

	public T getObject()
	{
		return object;
	}
}
