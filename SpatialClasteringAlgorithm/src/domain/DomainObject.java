package domain;

public interface DomainObject
{
	public enum Status
	{
		VISITED, NOISE, UNKNOWN;
	}

	public Status getStatus();

	public void setStatus(Status status);

	public void setClusterId(Integer cluster);

	public Integer getClusterId();

	public int getId();
}
