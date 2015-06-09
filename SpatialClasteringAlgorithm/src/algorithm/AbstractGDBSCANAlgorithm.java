package algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.ModelObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import predicate.DensePredicate;
import predicate.NeighbourhoodPredicate;
import domain.DomainObject.Status;

/*
 * klasa zawieraj¹ca kod algorytmu
 */
public abstract class AbstractGDBSCANAlgorithm
{
	private Logger logger = LoggerFactory.getLogger(AbstractGDBSCANAlgorithm.class);

	// private List<ModelObjectWrapper> list;

	private Map<Integer, ModelObjectWrapper> map;

	/*
	 * zmienna uzywana w klasach pochodnych jako wartosc do predykatu DENSE
	 */
	protected Integer densepredicatevalue;

	public AbstractGDBSCANAlgorithm(Integer densepredicatevalue)
	{
		this.densepredicatevalue = densepredicatevalue;
	}

	/*
	 * zaladuj liste danych wejsciowych
	 */
	public AbstractGDBSCANAlgorithm loadList(List<? extends ModelObject> list)
	{
		// this.list = createWrapperList(list);
		this.map = new HashMap<Integer, ModelObjectWrapper>();
		for (ModelObject mp : list)
			map.put(mp.getId(), new ModelObjectWrapper(mp));
		// return map;
		return this;
	}

	private List<ModelObjectWrapper> createWrapperList(List<? extends ModelObject> list)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObject mp : list)
			result.add(new ModelObjectWrapper(mp));
		return result;
	}

	/*
	 * g³ówny kod algorytmu GDBSCAN
	 */
	public void setClusters()
	{
		int nextClusterId = 1;
		for (ModelObjectWrapper t : map.values())
		{
			// je¿eli obiekt ju¿ by³ ma przypisany klaster - nie przetwarzaj go
			if (t.getStatus().equals(Status.VISITED))
				continue;
			t.setStatus(Status.VISITED);
			logger.trace("Przetwarzanie id: " + t.getId() + "[" + t.toString() + "]");
			// znajdz s¹siadów danego obiektu
			List<ModelObjectWrapper> neighbours = getNeighbours(t, map);
			logger.trace("Sasiedzi " + t.getId() + ": " + neighbours.stream().map(tt -> "" + tt.getId()).collect(Collectors.joining(",")));
			// sprawdz czy s¹siedzi spe³niaj¹ ¿¹dany warunek
			if (!getDensePredicate().isDenseEnough(neighbours))
				t.setStatus(Status.NOISE);
			else
				// je¿eli spe³niaj¹, to spróbuj rozszerzyæ klaster o s¹siadów
				// s¹siadów...
				expandCluster(t, neighbours, nextClusterId++);
		}
	}

	/*
	 * funkcja rozszerzaj¹ca istniej¹cy klaster o s¹siadów w liœcie
	 */
	private void expandCluster(ModelObjectWrapper point, List<ModelObjectWrapper> neighbours, Integer cluster)
	{
		point.setClusterId(cluster);
		// stworz liste sasiadow przetwarzanych sasiadow (przetwarzani w
		// nastepnym kroku rekurencji)
		List<ModelObjectWrapper> neighboursOfNeighbours = new LinkedList<ModelObjectWrapper>();
		// przetwarzaj ka¿dego s¹siada w liœcie
		for (ModelObjectWrapper neighbour : neighbours)
		{
			// sprawdz czy sasiad jeszcze nie byl przetwarzany
			if (!neighbour.getStatus().equals(Status.VISITED))
			{
				neighbour.setStatus(Status.VISITED);
				// znajdz sasiedztwo i sprawdz warunek gêstoœciowy, jezeli
				// spelniony to przetwarzaj takze sasiadow w nastepnym poziomie
				// rekurencji
				List<ModelObjectWrapper> neighboursOfNeighbour = getNeighbours(neighbour, map);
				if (getDensePredicate().isDenseEnough(neighboursOfNeighbour))
					neighboursOfNeighbours.addAll(neighboursOfNeighbour);
			}
			if (neighbour.getClusterId() == null)
				neighbour.setClusterId(cluster);
		}
		// usun sasiadow, ktorzy byli wczesniej dodani na nizszym poziomie
		// rekurencji
		neighboursOfNeighbours = removeVisited(neighboursOfNeighbours, cluster);
		// jezeli istnieja sasiedzi sasiadow, ktorzy sa nieprzetworzeni, to
		// przejdz do nastepnego kroku rekurencyjnego
		if (!neighboursOfNeighbours.isEmpty())
			expandCluster(point, neighboursOfNeighbours, cluster);
	}

	/*
	 * usun z listy na wejsciu obiekty odwiedzone w trakcie rozszerzania klastra
	 * clusterId
	 */
	private List<ModelObjectWrapper> removeVisited(List<ModelObjectWrapper> neighboursOfNeighbours, Integer clusterId)
	{
		List<ModelObjectWrapper> result = new LinkedList<ModelObjectWrapper>();
		for (ModelObjectWrapper p : neighboursOfNeighbours)
			if (!p.getStatus().equals(Status.VISITED) || p.getClusterId() != clusterId)
				result.add(p);
		return result;
	}

	/*
	 * znajdz liste sasiadow (zgodnie z predykatem sasiedztwa) dodajac punkt
	 * przetwarzany
	 */
	protected abstract List<ModelObjectWrapper> getNeighbours(ModelObjectWrapper point, Map<Integer, ModelObjectWrapper> map2);

	/*
	 * predykat gestosci, definiowany w klasach pochodnych
	 */
	protected abstract DensePredicate getDensePredicate();

	/*
	 * predykat sasiedztwa, definiowany w klasach pochodnych
	 */
	protected abstract NeighbourhoodPredicate getNeighbourhoodPredicate();

	public Object getResult()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
