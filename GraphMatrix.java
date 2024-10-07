/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Berkay
 */
public class GraphMatrix {


    int edges[][]; // can be anything, but int vertices handy
    // can be double if there are double weigths
    int numV;
    int numE;
  String[]GotNames =new String[1000];
    /**
     * @param V
     */
    public GraphMatrix(int V) {
        this.numV = V;
        edges = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                edges[i][j] = 0;
            }
        }
    }

    public void addEdge(String from, String to, int weight) {
      int ix = hash1(from);
        int iy = hash1(to);
       System.out.println(from+"-->"+ix+to+"-->"+iy);
        
        edges[ix][iy] = weight;
    }
    
  public  int hash1(String t) {
      
        return ((t.hashCode() & 0x7fffffff) % numV); 
    }
    

    public boolean isAdjacent(int v1, int v2) {
        return (edges[v1][v2] != 0);
    }

    public int degree(int v) {
        int degree = 0;
        for (int i = 0; i < numV; i++) {
            degree += edges[v][i];
        }
        return degree;
    }
    
  @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                s.append(edges[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
  public int noOfVerticesInComponent(String start1) {
      int a=hash1(start1); 
    boolean[] visited = new boolean[numV];
    int count = DFSUtil(a, visited);
    System.out.println("Number of vertices in component: " + count);
    return count;
}

private int DFSUtil(int current, boolean[] visited) {////We count the vertices with the dfs 
    visited[current] = true;
    int count = 1;

    for (int i = 0; i < numV; i++) {
        if (isAdjacent(current, i) && !visited[i]) {
            count += DFSUtil(i, visited);
        }
    }
    return count;
}
public void DFS(String start1, String end1) {
//first we hash our codes because graph has an integer value.  

int start=hash1(start1);
        int end=hash1(end1);
    boolean[] visited = new boolean[numV];
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(start);
    visited[start] = true;

    while (!stack.isEmpty()) {
        int current = stack.pop();
         System.out.print(GotNames[current]+ " ");
        if (current == end) {
        
            return;
        }
        for (int i = 0; i < numV; i++) {
            if (edges[current][i] != 0 && !visited[i]) {
                stack.push(i);
                visited[i] = true;
            }
        }
    }

}
public int ShortestPathLength(String start1, String end1) {
    
//I collect the lengths of all paths as weight, then I take the smallest one,
//for this I wrote a method called minDistance.
    int start=hash1(start1);
    int end=hash1(end1);
int[] dist = new int[numV];
boolean[] visited = new boolean[numV];
for (int i = 0; i < numV; i++) {
dist[i] = Integer.MAX_VALUE;//It returns the max value in an array, I used it in an array.Max sayıyı  bulma mantığı
visited[i] = false;
}
dist[start] = 0;
for (int i = 0; i < numV-1; i++) {
int u = minDistance(dist, visited);
visited[u] = true;
for (int v = 0; v < numV; v++) {
if (!visited[v] && edges[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u]+edges[u][v] < dist[v]) {
dist[v] = dist[u] + edges[u][v];
}
}
}
return dist[end];
}
public void allPathsShorterThanEqualTo(int pathLength,int VertexNo,String start) {
   int start1=hash1(start);
    ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();
    boolean[] visited = new boolean[numV];
    dfs(start1, start1, pathLength, 0, visited, allPaths, new ArrayList<>(), VertexNo);
    for (List<Integer> path : allPaths) {
        for (int vertex : path) {
            System.out.print(GotNames[vertex] + " ");
        }
        System.out.println();
    }
}

private void dfs(int current, int start1, int pathLength, int pathWeight, boolean[] visited, ArrayList<ArrayList<Integer>> allPaths, List<Integer> path,int VertexNo) {
   //I take the paths as a list, and then I place them into an array list.
    
    
    visited[current] = true;
    path.add(current);

    if (pathWeight > pathLength) {
        visited[current] = false;
        path.remove(path.size() - 1);
        return;
    }

    if ( path.size() == VertexNo&& pathWeight <= pathLength) {//Here, I put the desired values ​​into the arraylist called allpaths.
        allPaths.add(new ArrayList<>(path));
    } else {
        for (int i = 0; i < numV; i++) {
            if (edges[current][i]!=0 && !visited[i]) {
                int weight = edges[current][i];
                dfs(i, start1, pathLength, pathWeight + weight, visited, allPaths, path,VertexNo);
            }
        }
    }

    visited[current] = false;
    path.remove(path.size() - 1);
}


private int minDistance(int[] dist, boolean[] visited) {
int min = Integer.MAX_VALUE, minIndex = -1;
for (int v = 0; v < numV; v++) {
if (visited[v] == false && dist[v] <= min) {
min = dist[v];
minIndex = v;
}
}
return minIndex;
}  

    public boolean IsThereAPath(String  start1, String end1) {
    int start = hash1(start1);
    int end = hash1(end1);
   //Same as Dfs, if one vertex can reach the other vertex, there is a path between them.

    boolean[] visited = new boolean[numV];
    Queue<Integer> queue = new LinkedList<Integer>();
    visited[start] = true;
    queue.add(start);

    while (!queue.isEmpty()) {
       int current= queue.remove();
        if (current == end) {
            // v2 is reachable from v1
            return true;
        }

       for (int i = 0; i < numV; i++) {
            if (isAdjacent(current, i) && !visited[i]) {
                visited[i] = true;
                queue.add(i);
            }
        }
    }

    // v2 is not reachable from v1, so they are not in the same component
    return false;
}
    public int NoOfPathsFromTo(String start1, String end1) {
        int start=hash1(start1);
        int end =hash1(end1);
int pathCount = 0;
boolean[] visited = new boolean[numV];
pathCount = dfs(start, end, visited);
return pathCount;
}
private int dfs(int current, int end, boolean[] visited) {
int count = 0;
visited[current] = true;
if (current == end) {
count++;
//we count if reachable like isThereAPath
} else {
for (int i = 0; i < numV; i++) {
if (edges[current][i] != 0 && !visited[i]) {

count += dfs(i, end, visited);
}
}
}
visited[current] = false;
return count;
}

public void BFS(String start1, String end1) {//DijkstraBFS
   int start=hash1(start1);
     int end=hash1(end1);
int[] dist = new int[numV]; // distance from start to each vertex
boolean[] visited = new boolean[numV];
int[] prev = new int[numV]; // keep track of previous vertex on shortest path
PriorityQueue<Integer> queue = new PriorityQueue<>(numV, new DistanceComparator(dist)); // priority queue to select next vertex

//Weight i search for the lowest path and add it to queue
    for (int i = 0; i < numV; i++) {
        dist[i] = Integer.MAX_VALUE; // set initial distance to infinity
        prev[i] = -1; // set previous vertex to -1
    }
    dist[start] = 0; // distance from start to start is 0
    queue.add(start); // add start to queue

    while (!queue.isEmpty()) {
        int current = queue.poll();
        visited[current] = true;
        if (current == end) {
            break; // stop when we reach the end
        }

        // Visit all adjacent vertices
        for (int i = 0; i < numV; i++) {
            if (edges[current][i] != 0 && !visited[i]) {
                int newDist = dist[current] + edges[current][i];
                if (newDist < dist[i]) {
                    dist[i] = newDist;
                    prev[i] = current;
                    queue.add(i);
                }
            }
        }
    }
    

    // Print the sequence of vertices from start to end
    String path = end1 + " ";
    
    int current = end;
    while (current != start) {
        current = prev[current];
        path = GotNames[current] + " " + path;
    }
    System.out.println("Sequence of vertices: " + path);
}
private class DistanceComparator implements Comparator<Integer> {
int[] dist;

    public DistanceComparator(int[] dist) {
        this.dist = dist;
    }

    @Override
    public int compare(Integer i, Integer j) {
        return dist[i] - dist[j];
}
}


}
