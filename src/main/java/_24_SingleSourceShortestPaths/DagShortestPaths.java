package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import _22_ElementaryGraphAlgorithms.TopologicalSort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class DagShortestPaths
{
    public static <T> Map<Vertex<T>, Integer> dagShortestPathsDistance(Graph<T> graph, Vertex<T> root) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.vertices(), root);
        List<Vertex<T>> vertices = TopologicalSort.sort(graph);

        for (Vertex<T> v : vertices) {
            for (Edge<T> e : graph.adjacentEdgesOf(v))
                Util.relax(map, e);
        }

        return map;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> dagShortestPathsPath(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.vertices(), src);
        List<Vertex<T>> vertices = TopologicalSort.sort(graph);
        Map<Vertex<T>, Vertex<T>> result = new HashMap<>();

        for (Vertex<T> v : vertices) {
            for (Edge<T> e : graph.adjacentEdgesOf(v))
                if (Util.relax(map, e))
                    result.put(e.getDest(), e.getSrc());
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(true);

            Character[] V = {'r', 's', 't', 'x', 'y', 'z'};
            Character[][] E = {
                    {'r', 's'}, {'r', 't'}, {'s', 'x'}, {'s', 't'}, {'t', 'x'},
                    {'t', 'y'}, {'t', 'z'}, {'x', 'z'}, {'x', 'y'}, {'y', 'z'}
            };
            Integer[] W = {5, 3, 6, 2, 7, 4, 2, 1, -1, -2};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println("Shortest paths from root 's': ");
            System.out.println(DagShortestPaths.dagShortestPathsDistance(graph, new Vertex<>('s')));

            System.out.println("\nShortest paths from root 's': ");
            Map<Vertex<Character>, Vertex<Character>> paths = DagShortestPaths.dagShortestPathsPath(graph, new Vertex<>('s'));
            System.out.println(paths);

            System.out.println("\nShortest path from 's' to 'z': ");
            System.out.println(Util.getShortestPath(paths, new Vertex<>('s'), new Vertex<>('z')));
        }
    }
}