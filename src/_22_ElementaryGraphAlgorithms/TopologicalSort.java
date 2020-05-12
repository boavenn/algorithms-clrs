package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class TopologicalSort
{
    public static <T> List<Vertex<T>> topologicalSort(Graph<T> graph) {
        if (!graph.isDirected())
            throw new IllegalArgumentException("Graph is not directed");

        // true means permanent mark, false - temporary
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();

        for (Vertex<T> v : graph.getVertices()) {
            if (!visited.containsKey(v))
                visit(graph, v, visited, stack);
        }

        List<Vertex<T>> temp = new LinkedList<>();
        while (!stack.isEmpty())
            temp.add(stack.pop());
        return temp;
    }

    private static <T> void visit(Graph<T> graph, Vertex<T> vertex, Map<Vertex<T>, Boolean> visited, Stack<Vertex<T>> stack) {
        if (visited.containsKey(vertex) && visited.get(vertex)) // permanent mark
            return;
        if (visited.containsKey(vertex) && !visited.get(vertex)) // temporary mark
            throw new IllegalArgumentException("Graph is not a DAG");

        // temporary mark
        visited.put(vertex, false);

        for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex))
            visit(graph, v, visited, stack);

        // permanent mark
        visited.put(vertex, true);
        stack.push(vertex);
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Integer> graph = new Graph<>(true);
            Integer[] V1 = {0, 1, 2, 3, 4, 5};
            Integer[][] E1 = {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {3, 1}, {2, 3}};

            for (Integer i : V1)
                graph.addVertex(i);
            for (Integer[] i : E1)
                graph.addEdge(i[0], i[1]);

            System.out.println(graph);
            System.out.println("Topological sort: ");
            System.out.println(TopologicalSort.topologicalSort(graph));
        }
    }
}
