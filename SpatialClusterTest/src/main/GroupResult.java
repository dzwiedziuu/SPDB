package main;

public class GroupResult
{
	public int vertexNum;

	public double totalMiliseconds, totalClusterCnt;

	public int size = 0;

	public double totalClusteredNumber;

	public void addMiliSeconds(long elapsed)
	{
		size++;
		totalMiliseconds += elapsed;
	}

	public double getAvgMiliSeconds()
	{
		return totalMiliseconds / size;
	}
}
