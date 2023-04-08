import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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
                for (int i = 1; i < tokens.length; i++) { //ignore the first "int [space]" its not needed
                    String[] secondSplit = tokens[i].split(":"); //Split the int:int
                    int adj = Integer.parseInt(secondSplit[0]);
                    int weight = Integer.parseInt(secondSplit[1]);
                    Node addNode = new Node(adj, weight);
                    tempArrayList.get(location).add(addNode);
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
        }
        return tempArrayList;
    }

    static int fordFulkerson(int[][] graph, int source, int sink, int count) {
        return 0;
    }

    static int augmentFordFulk(int forward, int count, List<Integer> p) {
        return 0;
    }
}
