package main;

import domain.Params;

public class TestParams extends Params
{
	public TestParams()
	{
		this.alpha = 0.95;
		this.initialTemperature = 3.0;
		this.minimalTemperature = 0.3;
		this.k = 10l;
		this.vertexNumber = 50;
		this.probability = 0.5;
		this.graphNumber = 2;
		this.tries = 3;
	}

	@Override
	public boolean doNextTest()
	{
		return this.initialTemperature <= 8;
	}

	@Override
	public Params nextTestParams()
	{
		Params newParams = this.copy();
		newParams.initialTemperature = this.initialTemperature + 0.5;
		return newParams;
	}
}
