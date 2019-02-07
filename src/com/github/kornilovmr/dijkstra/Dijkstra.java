package com.github.kornilovmr.dijkstra;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Dijkstra {
    // n m (n - number of nodes, m - number of edges)
    // a b s
    // ...
    // start
    public static void main(String[] args) {
        try (FileInputStream stream = new FileInputStream("input.txt")) {
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
            Map<Integer, Integer> shortestDistances = dijkstra(start, graph);
            for (Map.Entry<Integer, Integer> entry : shortestDistances.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static Map<Integer, Integer> dijkstra(int start, Map<Integer, List<Edge>> graph) {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));

        Map<Integer, Integer> shortestDistances = new HashMap<>();
        for (Integer nodeId : graph.keySet()) {
            shortestDistances.put(nodeId, Integer.MAX_VALUE);
        }
        shortestDistances.put(start, 0);
        Set<Integer> checked = new HashSet<>();
        while (!queue.isEmpty()) {
            dostep(graph, queue, shortestDistances, checked);
        }
        return shortestDistances;
    }

    private static void dostep(Map<Integer, List<Edge>> graph, PriorityQueue<Node> queue, Map<Integer, Integer> shortestDistances, Set<Integer> checked) {
        Node other = queue.poll();
        assert other != null;
        checked.add(other.nodeId);
        int shortestDistancesForStep = other.shortestDistance;
        List<Edge> edges = graph.get(other.nodeId);
        for (Edge edge : edges) {
            if (checked.contains(edge.childId)) continue;
            int childDistence = shortestDistancesForStep + edge.length;
            int oldDistance = shortestDistances.get(edge.childId);
            if (childDistence < oldDistance) {
                queue.remove(new Node(edge.childId, oldDistance));
                shortestDistances.put(edge.childId, childDistence);
                queue.add(new Node(edge.childId, childDistence));
            }
        }
    }



}
