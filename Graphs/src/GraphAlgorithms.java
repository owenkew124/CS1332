import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Owen Kew
 * @version 1.0
 * @userid okew3
 * @GTID 903592179
 *
 * Collaborators:
 *
 * Resources:
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start vertex is not in the graph");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        HashSet<Vertex<T>> vs = new HashSet<>();
        LinkedList<Vertex<T>> q = new LinkedList<>();
        LinkedList<Vertex<T>> list = new LinkedList<>();
        vs.add(start);
        list.add(start);
        q.addLast(start);
        while (!(q.isEmpty())) {
            Vertex<T> v = q.removeFirst();
            List<VertexDistance<T>> adjV = adj.get(v);
            for (VertexDistance<T> next : adjV) {
                if (!(vs.contains(next.getVertex()))) {
                    vs.add(next.getVertex());
                    list.add(next.getVertex());
                    q.addLast(next.getVertex());
                }
            }
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start vertex is not in the graph");
        }
        LinkedList<Vertex<T>> list = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        HashSet<Vertex<T>> vs = new HashSet<>();
        vs.add(start);
        list.add(start);
        list = (LinkedList<Vertex<T>>) dfsHelper(start, vs, list, adj);
        return list;
    }

    /**
     * Helper Method for the depth first search method
     * @param start the starting index for this iteration of the search
     * @param vs the visited set of what has already been searched
     * @param list the list of vertices in visited order that were visted in previous iterations
     * @param adj the adjacency list for the graph
     * @param <T> the generic typing of the data
     * @return the list of vertices in visited order at the time of the method running
     */
    public static <T> List<Vertex<T>> dfsHelper(Vertex<T> start, HashSet<Vertex<T>> vs,
                                                List<Vertex<T>> list, Map<Vertex<T>, List<VertexDistance<T>>> adj) {
        List<VertexDistance<T>> adjV = adj.get(start);
        for (VertexDistance<T> next : adjV) {
            if (!(vs.contains(next.getVertex()))) {
                vs.add(next.getVertex());
                list.add(next.getVertex());
                list = dfsHelper(next.getVertex(), vs, list, adj);
            }
        }
        return list;
    }
    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The start vertex is not in the graph");
        }
        LinkedList<Vertex<T>> vs = new LinkedList<>();
        HashMap<Vertex<T>, Integer> dm = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        pq.add(new VertexDistance<>(start, 0));
        while (!(pq.isEmpty())) {
            VertexDistance<T> next = pq.poll();
            if (!(vs.contains(next.getVertex()))) {
                vs.add(next.getVertex());
                dm.put(next.getVertex(), next.getDistance());
                List<VertexDistance<T>> adjV = adj.get(next.getVertex());
                for (VertexDistance<T> tVertexDistance : adjV) {
                    pq.add(new VertexDistance<>(tVertexDistance.getVertex(),
                            next.getDistance() + tVertexDistance.getDistance()));
                }
            }
        }
        return dm;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        HashSet<Edge<T>> edgeSet = new HashSet<>();
        Set<Vertex<T>> vertices = graph.getVertices();
        Set<Edge<T>> edges = graph.getEdges();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(edges);
        for (Vertex<T> vert : vertices) {
            disjointSet.find(vert);
        }
        while (!(pq.isEmpty()) && edgeSet.size() < 2 * (vertices.size() - 1)) {
            Edge<T> next = pq.poll();
            Vertex<T> u = next.getU();
            Vertex<T> v = next.getV();
            Vertex<T> uPar = disjointSet.find(u);
            Vertex<T> vPar = disjointSet.find(v);
            if (uPar != vPar) {
                disjointSet.union(uPar, vPar);
                edgeSet.add(next);
                Edge<T> newEdge = new Edge<>(v, u, next.getWeight());
                edgeSet.add(newEdge);
            }
        }
        if (edgeSet.size() != 2 * (vertices.size() - 1)) {
            return null;
        }
        return edgeSet;
    }
}
