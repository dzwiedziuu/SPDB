package main;

import model.ModelObjectReader;
import rectangle.RectangleReader;
import algorithm.AbstractGDBSCANAlgorithm;
import algorithm.RectangleAlgorithm;

public class Params
{
	public int xmax;
	public int ymax;
	public int n;
	public PointGenerator pointGenerator = new RectangleGenerator();
	public ModelObjectReader modelObjectReader = new RectangleReader();
	public AbstractGDBSCANAlgorithm algrithm = new RectangleAlgorithm(30000);

	public int modelNumber;
	public int tries;

	public Params()
	{
		this(20);
	}

	private static final int initialSize = 10;

	public Params(int n)
	{
		this.xmax = 500;
		this.ymax = 500;
		this.n = n;
		this.modelNumber = 10;
		this.tries = 20;
	}

	public boolean doNextTest()
	{
		return this.n <= 100;
	}

	public Params nextTestParams()
	{
		Params params = copy();
		params.n += 10;
		return params;
	}

	@Override
	public String toString()
	{
		return "n=" + n;
	}

	public Params copy()
	{
		Params params = new Params(0);
		params.xmax = this.xmax;
		params.ymax = this.ymax;
		params.n = this.n;
		params.modelNumber = this.modelNumber;
		params.tries = this.tries;
		return params;
	}
}
