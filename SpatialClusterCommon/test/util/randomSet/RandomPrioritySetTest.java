package util.randomSet;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import utils.randomSet.RandomPrioritySet;
import utils.randomSet.RandomPrioritySetValue;

public class RandomPrioritySetTest
{
	@Test
	public void test()
	{
		List<RandomPrioritySetValue> test = new LinkedList<RandomPrioritySetValue>();
		test.add(new RandomPrioritySetValue("test1", 6.0));
		test.add(new RandomPrioritySetValue("test2", 3.0));
		test.add(new RandomPrioritySetValue("test3", 1.0));
		RandomPrioritySet rps = new RandomPrioritySet(test);
		Map<Object, Integer> map = new LinkedHashMap<Object, Integer>();
		for (int i = 0; i < 10000; i++)
		{
			Object value = rps.getRandomValue();
			Integer result = map.get(value);
			if (result == null)
				result = 0;
			result++;
			map.put(value, result);
		}
		System.out.println(map);
	}
}
