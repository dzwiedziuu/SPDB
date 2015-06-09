package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import model.polygon.Edge;
import model.polygon.Vertex;
import rectangle.Rectangle;

public class RectangleGenerator extends PolygonGenerator
{
	public List<Rectangle> rectangles = new LinkedList<Rectangle>();

	@Override
	public void generate(String oFile, int n, int xmax, int ymax) throws IOException
	{
		createRectangle(new Vertex(0, 0), new Vertex(xmax, ymax));
		for (int i = 1; i < n; i += 3)
		{
			Vertex splitVertex = null;
			Rectangle insideRect = null;
			do
			{
				splitVertex = Vertex.generateRandom(xmax, ymax);
			} while ((insideRect = findRect(splitVertex)) == null);
			splitVertex = insideRect.getLegalPoint(splitVertex);
			splitRectangle(insideRect, splitVertex);
		}
		normalizeEdges();
		writeStateToFile(oFile);
	}

	public void normalizeEdges()
	{
		List<Edge> sorted = sortEdges(new Comparator<Edge>()
		{
			@Override
			public int compare(Edge arg0, Edge arg1)
			{
				return Double.compare(arg0.getLength(), arg1.getLength());
			}
		});

		List<Edge> newEdgeList = new LinkedList<Edge>();
		outer: for (Edge mainEdge : sorted)
		{
			for (Edge otherEdge : newEdgeList)
			{
				if (mainEdge.contains(otherEdge) && mainEdge.touch(otherEdge) && otherEdge.getLength() < mainEdge.getLength())
				{
					List<Edge> edgeList = findCompomentEdgeList(mainEdge, otherEdge, newEdgeList);
					replaceEdge(mainEdge, edgeList);
					continue outer;
				}
			}
			newEdgeList.add(mainEdge);
		}
		edges = newEdgeList;

		edges = sortEdges(new Comparator<Edge>()
		{
			@Override
			public int compare(Edge arg0, Edge arg1)
			{
				return Double.compare(arg0.edgeId, arg1.edgeId);
			}
		});
	}

	private void replaceEdge(Edge mainEdge, List<Edge> edgeList)
	{
		for (Rectangle r : mainEdge.rectangles)
		{
			r.edges.remove(mainEdge);
			r.edges.addAll(edgeList);
			for (Edge e : edgeList)
				e.rectangles.add(r);
		}
	}

	private List<Edge> findCompomentEdgeList(Edge mainEdge, Edge startingEdge, List<Edge> newEdgeList)
	{
		Vertex finishVertex = startingEdge.containsVertex(mainEdge.getFirstEnd()) ? mainEdge.getSecondEnd() : mainEdge.getFirstEnd();
		Vertex startingVertex = mainEdge.containsVertex(startingEdge.getFirstEnd()) ? startingEdge.getSecondEnd() : startingEdge.getFirstEnd();

		List<Edge> result = new LinkedList<Edge>(Arrays.asList(startingEdge));
		outer: while (!startingVertex.equals(finishVertex))
		{
			for (Edge e : newEdgeList)
				if (e.containsVertex(startingVertex) && mainEdge.contains(e) && !result.contains(e))
				{
					result.add(e);
					startingVertex = e.getOtherVertex(startingVertex);
					continue outer;
				}
			Edge closestEdge = findNextStartingVertex(startingVertex, finishVertex, newEdgeList);
			result.add(closestEdge);
			startingVertex = closestEdge.getOtherVertex(startingVertex);
		}
		return result;
	}

	private Edge findNextStartingVertex(Vertex startingVertex, Vertex finishVertex, List<Edge> newEdgeList)
	{
		Edge tempEdge = new Edge(Arrays.asList(startingVertex, finishVertex));
		double minDist = tempEdge.getLength();
		Edge result = tempEdge;
		for (Edge e : newEdgeList)
			if (tempEdge.contains(e))
			{
				Vertex v = e.getClosestVertex(startingVertex);
				double dist = startingVertex.getDistanceNotSquaredFrom(v);
				if (dist < minDist)
				{
					minDist = dist;
					result = e;
				}
			}
		if (tempEdge == result)
			newEdgeList.add(tempEdge);
		return result;
	}

	private List<Edge> sortEdges(Comparator<Edge> comp)
	{
		List<Edge> sorted = new LinkedList<Edge>(edges);
		Collections.sort(sorted, comp);
		return sorted;
	}

	private Rectangle findRect(Vertex vertex)
	{
		for (Rectangle r : rectangles)
			if (r.isInside(vertex) && r.isBigEnoughRect())
				return r;
		return null;
	}

	private void splitRectangle(Rectangle rectangle, Vertex vertex)
	{
		dropRectangle(rectangle);
		for (Vertex v : rectangle.vertices)
			createRectangle(v, vertex);
	}

	private void dropRectangle(Rectangle rectangle)
	{
		rectangles.remove(rectangle);
		for (Edge e : rectangle.edges)
			e.rectangles.remove(rectangle);
	}

	private Rectangle createRectangle(Vertex v1, Vertex v2)
	{
		Rectangle r = new Rectangle(Arrays.asList(v1, new Vertex(v1.x, v2.y), v2, new Vertex(v2.x, v1.y)));
		for (List<Vertex> pair : splitToNeighbourPairs(r.vertices))
		{
			Edge e = findOrCreateEdge(pair);
			r.edges.add(e);
			e.rectangles.add(r);
		}
		rectangles.add(r);
		return r;
	}

	private void writeStateToFile(String oFile) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(oFile)));
		for (Edge e : edges)
			bw.write(e.edgeId + " " + e.getFirstEnd().x + " " + e.getFirstEnd().y + " " + e.getSecondEnd().x + " " + e.getSecondEnd().y + "\n");
		bw.write("\n");
		for (Rectangle r : rectangles)
		{
			bw.write("" + r.polygonId + " " + 0);
			for (Edge e : r.edges)
				bw.write(" " + e.edgeId);
			bw.write("\n");
		}
		bw.close();
	}
}
