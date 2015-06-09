package domain;

import model.ModelObject;

/*
 * interfejs u¿ywany w algorytmie GDBSCAN
 */
public interface DomainObject extends ModelObject
{
	public enum Status
	{
		VISITED, NOISE, UNKNOWN;
	}

	public Status getStatus();

	public void setStatus(Status status);
}
