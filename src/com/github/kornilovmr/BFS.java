package com.github.kornilovmr;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class BFS {
    public static void main(String[] args) {
        try (FileInputStream stream = new FileInputStream("input.txt")) {
            Scanner scanner = new Scanner(stream);
            Map<Integer, Set<Integer>> tree = new HashMap<>();
            int counter = scanner.nextInt();
            int root = scanner.nextInt();
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
            bfs(tree, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bfs(Map<Integer, Set<Integer>> tree, int root) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int node = queue.remove();
            System.out.println(node);
            Set<Integer> children = tree.get(node);
            if (children == null) {
                continue;
            }
            for (Integer child : children) {
                queue.add(child);

            }
        }
    }
}
