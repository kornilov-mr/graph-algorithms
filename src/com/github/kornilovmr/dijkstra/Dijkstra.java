package com.github.kornilovmr.dijkstra;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Dijkstra {
    // n m (n - number of nodes, m - number of edges)
    // a b s
    // ...
    // start
    public static TestData readGraph(String name) {
        try (FileInputStream stream = new FileInputStream(name)) {
            Scanner scanner = new Scanner(stream);
            Map<Integer, List<Edge>> graph = new HashMap<>();
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            for (int i = 0; i < m; i++) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int s = scanner.nextInt();
                addChild(a, b, s, graph);
                addChild(b, a, s, graph);
            }
            int start = scanner.nextInt();

            return new TestData(graph, start);

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        TestData testData = readGraph("input.txt");


        Map<Integer, Result> shortestDistances = dijkstra(testData.start, testData.graph);


        for (Map.Entry<Integer, Result> entry : shortestDistances.entrySet()) {
            Result result = entry.getValue();
            System.out.println(entry.getKey() + ". Distance:" + result.shortestDistance + " path: " + buildPath(shortestDistances, entry.getKey()));
        }


    }

    private static List<Integer> buildPath(Map<Integer, Result> shortestDistances, int node) {
        int prNode = shortestDistances.get(node).previousNode;
        if (prNode == -1) return new ArrayList<>();
        List<Integer> path = buildPath(shortestDistances, prNode);
        path.add(prNode);
        return path;
    }

    private static void addChild(int a, int b, int s, Map<Integer, List<Edge>> graph) {
        List<Edge> value = graph.get(a);
        if (value == null) {
            value = new ArrayList<>();
            graph.put(a, value);
            value.add(new Edge(b, s));

        } else {
            value.add(new Edge(b, s));

        }
    }

    public static Map<Integer, Result> dijkstra(int start, Map<Integer, List<Edge>> graph) {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));

        Map<Integer, Result> shortestDistances = new HashMap<>();
        for (Integer nodeId : graph.keySet()) {
            shortestDistances.put(nodeId, new Result(-1, Integer.MAX_VALUE));
        }
        shortestDistances.put(start, new Result(-1, 0));
        Set<Integer> checked = new HashSet<>();
        while (!queue.isEmpty()) {
            doStep(graph, queue, shortestDistances, checked);
        }
        return shortestDistances;
    }

    private static void doStep(Map<Integer, List<Edge>> graph, PriorityQueue<Node> queue, Map<Integer, Result> shortestDistances, Set<Integer> checked) {
        Node other = queue.poll();
        assert other != null;
        checked.add(other.nodeId);
        int shortestDistancesForStep = other.shortestDistance;
        List<Edge> edges = graph.get(other.nodeId);
        for (Edge edge : edges) {
            if (checked.contains(edge.childId)) continue;
            int childDistance = shortestDistancesForStep + edge.length;
            Result oldDistance = shortestDistances.get(edge.childId);
            if (childDistance < oldDistance.shortestDistance) {
                queue.remove(new Node(edge.childId, oldDistance.shortestDistance));
                shortestDistances.put(edge.childId, new Result(other.nodeId, childDistance));
                queue.add(new Node(edge.childId, childDistance));
            }
        }
    }


    static class TestData {
        final Map<Integer, List<Edge>> graph;
        final int start;

        TestData(Map<Integer, List<Edge>> graph, int start) {
            this.graph = graph;
            this.start = start;
        }
    }

    public static class Result {
        final int previousNode;
        final int shortestDistance;

        Result(int previousNode, int shortestDistance) {
            this.previousNode = previousNode;
            this.shortestDistance = shortestDistance;
        }

        @Override
        public String toString() {
            return "(distance: " + shortestDistance + ", previousNode: " + previousNode + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Result)) return false;
            return this.previousNode == ((Result) obj).previousNode && this.shortestDistance == ((Result) obj).shortestDistance;
        }
    }


}
