package com.github.kornilovmr.dijkstra;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DijkstraTest {

    @Test
    public void testSimple() {
        tester("simple");
    }

    public void tester(String nameTest) {
        Dijkstra.TestData test = Dijkstra.readGraph("testData/" + nameTest + ".txt");
        Map<Integer, Integer> shortestDistances = Dijkstra.dijkstra(test.start, test.graph);
        try (FileInputStream stream = new FileInputStream("testData/" + nameTest + ".expected.txt")) {
            Scanner scanner = new Scanner(stream);
            Map<Integer, Integer> expectedShortestDistance = new HashMap<>();
            int n = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                int distance = scanner.nextInt();
                expectedShortestDistance.put(id, distance);
            }
            Assert.assertEquals(expectedShortestDistance, shortestDistances);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}