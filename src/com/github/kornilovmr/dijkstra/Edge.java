package com.github.kornilovmr.dijkstra;

class Edge {
    final int childId;
    final int length;

    Edge(int childId, int length) {
        this.childId = childId;
        this.length = length;
    }
}
