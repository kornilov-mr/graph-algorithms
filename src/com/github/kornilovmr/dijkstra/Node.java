package com.github.kornilovmr.dijkstra;


class Node implements Comparable<Node> {
    final int nodeId;
    final int shortestDistance;

    Node(int nodeId, int shortestDistance) {
        this.nodeId = nodeId;
        this.shortestDistance = shortestDistance;
    }

    @Override
    public int compareTo(Node o) {
        // 0 - equal
        // 1 - this > o
        // -1 - this < 0
        if (shortestDistance > o.shortestDistance) {
            return 1;
        } else if (shortestDistance < o.shortestDistance) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        if (this.nodeId == other.nodeId && this.shortestDistance == other.shortestDistance) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Node(" + nodeId + ", " + shortestDistance + ")";
    }
}


