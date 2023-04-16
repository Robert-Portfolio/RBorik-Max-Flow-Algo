import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Please Enter File Name: ");
        String fileName = scnr.nextLine();
        scnr.close();
        List<List<Node>> inputGraph;
        inputGraph = readfile(fileName);
    }

    public static List<List<Node>> readfile(String fileName) {
        List<List<Node>> tempArrayList = new ArrayList<List<Node>>();
        try {
            File inputFile = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split(" "); //delimiter at every space
                int location = Integer.parseInt(tokens[0]);
                List<Node> singleListTemp = new ArrayList<Node>();
                for (int i = 1; i < tokens.length; i++) { //ignore the first "int [space]" its not needed
                    String[] secondSplit = tokens[i].split(":"); //Split the int:int
                    int adj = Integer.parseInt(secondSplit[0]);
                    int weight = Integer.parseInt(secondSplit[1]);
                    Node addNode = new Node(adj, weight);
                    singleListTemp.add(addNode);
                }
                tempArrayList.add(location, singleListTemp);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
        }
        return tempArrayList;
    }

    public static int[] fordFulkerson(int[][] graph, int source, int sink) {
        int numVertices = graph.length;
        int[][] residualGraph = new int[numVertices][numVertices];
    
        // Initialize residualGraph to be the same as the input graph
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
    
        int[] parent = new int[numVertices];
    
        int maxFlow = 0;
    
        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
    
            // Find the bottleneck capacity along the path
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
    
            // Update the residual capacities of the edges and their reverse edges
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
    
            // Add the path flow to the total flow
            maxFlow += pathFlow;
        }
    
        // Return the max flow
        return new int[]{ maxFlow };
    }

    public static boolean bfs(int[][] graph, int source, int sink, int[] parent) {
        int numVertices = graph.length;
    
        // Create a visited array to keep track of visited vertices
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
    
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
    
        // Perform BFS
        while (!queue.isEmpty()) {
            int u = queue.poll();
    
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    queue.add(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }
    
        // Return true if we found a path from the source to the sink
        return visited[sink];
    }

    // Returns an augmenting path in graph from source to sink using BFS

    public static int[][] augment(int[][] f, int[][] c, ArrayList<Integer> P) {
        int b = bottleneck(P, c, f);  // Find bottleneck capacity of path P
        
        for (int i = 0; i < P.size() - 1; i++) {
            int u = P.get(i);
            int v = P.get(i + 1);
            if (c[u][v] > 0) {  // edge (u, v) is in E
                f[u][v] += b;
            } else {  // edge (v, u) is in E
                f[v][u] -= b;
            }
        }
        
        return f;
    }
    
    public static int bottleneck(ArrayList<Integer> P, int[][] c, int[][] f) {
        int b = Integer.MAX_VALUE;
        for (int i = 0; i < P.size() - 1; i++) {
            int u = P.get(i);
            int v = P.get(i + 1);
            int residualCapacity = c[u][v] - f[u][v];
            b = Math.min(b, residualCapacity);
        }
        return b;
    }
    
}
