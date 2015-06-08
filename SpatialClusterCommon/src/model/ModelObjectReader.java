package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ModelObjectReader
{
	public List<? extends ModelObject> read(File file) throws IOException;
}
