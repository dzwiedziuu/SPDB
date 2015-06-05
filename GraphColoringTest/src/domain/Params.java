package domain;

public abstract class Params
{
	public int vertexNumber;
	public double probability;
	public Double initialTemperature;
	public Double minimalTemperature;
	public Double alpha;
	public Long k;
	public int graphNumber;
	public int tries;

	public abstract boolean doNextTest();

	public abstract Params nextTestParams();

	public Params copy()
	{
		Params result;
		try
		{
			result = this.getClass().newInstance();
			result.alpha = this.alpha;
			result.initialTemperature = this.initialTemperature;
			result.k = this.k;
			result.minimalTemperature = this.minimalTemperature;
			result.probability = this.probability;
			result.vertexNumber = this.vertexNumber;
			result.graphNumber = this.graphNumber;
			result.tries = this.tries;
			return result;
		} catch (InstantiationException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String toString()
	{
		return "InitialTemp=" + initialTemperature + ", minimalTemp=" + minimalTemperature + ", alfa=" + alpha + ", k=" + k;
	}
}
