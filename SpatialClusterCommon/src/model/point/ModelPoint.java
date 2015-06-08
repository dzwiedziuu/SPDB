package model.point;

import model.ModelObject;

public class ModelPoint implements ModelObject
{
	private int id, x, y;
	private Integer clusterId;

	public ModelPoint(int id, int clusterId, int x, int y)
	{
		super();
		this.id = id;
		this.clusterId = clusterId;
		this.x = x;
		this.y = y;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Integer getClusterId()
	{
		return clusterId;
	}

	public void setClusterId(Integer colorId)
	{
		this.clusterId = colorId;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

}
