package algorithm;

import model.ModelObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.DomainObject;

/*
 * klasa opakowuj¹ca obiekt z modelu dziedziny
 */
public class ModelObjectWrapper implements ModelObject, DomainObject
{
	private Logger logger = LoggerFactory.getLogger(ModelObjectWrapper.class);

	private ModelObject modelPoint;

	public Status pointStatus = Status.UNKNOWN;

	public ModelObjectWrapper(ModelObject modelPoint)
	{
		this.modelPoint = modelPoint;
		modelPoint.setClusterId(null);
	}

	@Override
	public int getId()
	{
		return modelPoint.getId();
	}

	@Override
	public Integer getClusterId()
	{
		return modelPoint.getClusterId();
	}

	public ModelObject getWrappedInstance()
	{
		return modelPoint;
	}

	@Override
	public void setClusterId(Integer colorId)
	{
		modelPoint.setClusterId(colorId);
		logger.debug("Ustawiam klaster: " + colorId + " - id " + getId());
	}

	@Override
	public Status getStatus()
	{
		return pointStatus;
	}

	@Override
	public void setStatus(Status status)
	{
		this.pointStatus = status;
	}

	@Override
	public String toString()
	{
		return modelPoint.toString();
	}
}
