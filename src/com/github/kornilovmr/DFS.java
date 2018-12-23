package com.github.kornilovmr;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DFS {
    public static void main(String[] args) {
        try (FileInputStream stream = new FileInputStream("input.txt")) {
            Scanner scanner = new Scanner(stream);
            Set<Integer> visitedNodes = new HashSet<>();
            Map<Integer, Set<Integer>> tree = new HashMap<>();
            int counter = scanner.nextInt();
            for (int i = 0; i < counter; i++) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();

                Set<Integer> value = tree.get(a);
                if (value == null) {
                    value = new HashSet<>();
                    value.add(b);
                    tree.put(a, value);
                } else {
                    value.add(b);
                }
            }
            int need = scanner.nextInt();
            dfs(need, tree, visitedNodes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // depth first search
    private static void dfs(int node, Map<Integer, Set<Integer>> tree, Set<Integer> visitedNodes) {
        System.out.println(node);
        Set<Integer> children = tree.get(node);
        if (children == null) {
            return;
        }
        if (visitedNodes.contains(node)) {
            return;
        }
        visitedNodes.add(node);
        for (Integer child : children) {
            dfs(child, tree, visitedNodes);
        }
    }
}
