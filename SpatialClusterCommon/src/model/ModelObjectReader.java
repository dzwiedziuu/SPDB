package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 * interfejs klasy wczytuj�cej obiekty dziedziny z pliku
 */
public interface ModelObjectReader
{
	public List<? extends ModelObject> read(File file) throws IOException;
}
