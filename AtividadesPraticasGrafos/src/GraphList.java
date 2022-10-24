import java.util.ArrayList;
import java.util.Stack;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class GraphList {

  private int countNodes;
  private int countEdges;
  private ArrayList<ArrayList<Edge>> adjList;

  public GraphList(int countNodes) {
    this.countNodes = countNodes;
    adjList = new ArrayList<>(this.countNodes);
    for (int i = 0; i < this.countNodes; ++i) {
      adjList.add(new ArrayList<Edge>());
    }
  }

  public GraphList(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    // Create and fill adjList with read edges
    adjList = new ArrayList<>(this.countNodes);
    for (int i = 0; i < this.countNodes; ++i) {
      adjList.add(new ArrayList<Edge>());
    }
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdge(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < this.adjList.size(); ++i) {
      str += i + ": ";
      for (int j = 0; j < this.adjList.get(i).size(); ++j) {
        str += this.adjList.get(i).get(j) + "\t";
      }
      str += "\n";
    }
    return str;
  }

  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source > this.countNodes - 1 ||
        sink < 0 || sink > this.countNodes - 1 ||
        weight <= 0) {
      System.err.println("Invalid edge: [" + source + "]->[" + sink + "] = " + weight);
      return;
    } else {
      adjList.get(source).add(new Edge(source, sink, weight));
      this.countEdges++;
    }
  }

  public void addEdgeUnoriented(int source, int sink, int weight) {
    addEdge(source, sink, weight);
    addEdge(sink, source, weight);
  }

  public int degree(int node) {
    if (node < 0 || node > this.countNodes - 1)
      System.err.println("Nó inválido: " + node);
    return this.adjList.get(node).size();
  }

  public int highestDegree() {
    int highestDegree = 0;
    int degree = 0;
    for (int i = 0; i < this.adjList.size(); ++i) {
      degree = this.degree(i);
      if (degree > highestDegree)
        highestDegree = degree;
    }
    return highestDegree;
  }

  public int lowestDegree() {
    int lowestDegree = this.countNodes;
    for (int i = 1; i < this.adjList.size(); ++i) {
      int degree = this.degree(i);
      if (degree < lowestDegree)
        lowestDegree = degree;
    }
    return lowestDegree;
  }

  public GraphList complement() {//INCOMPLETA
    GraphList g = new GraphList(this.countNodes);
    for (int i = 0; i < this.adjList.size(); i++) {
      if(this.adjList.size()>0)
        for (int j = 0; j < this.adjList.size(); j++)
          if (this.adjList.get(i).get(j)!=null)
            g.addEdge(i,j,1);
    }
    return g;
  }

  // public GraphMatrix complement() {
  // GraphMatrix g = new GraphMatrix(this.countNodes);
  // for (int i = 0; i < this.adjMatrix.length; i++) {
  // for (int j = 0; j < this.adjMatrix[i].length; j++) {
  // if (i != j) {
  // if (this.adjMatrix[i][j] == 0) {
  // g.addEdges(i, j, 1);
  // }
  // }
  // }
  // }
  // return g;
  // }
  //
  // public boolean subGraph(GraphMatrix g2) {
  //
  // if (g2.getCountNodes() > this.countNodes) {
  // return false;
  // }
  ///
  // int[][] matriz = g2.getAdjMatrix();
  // for (int i = 0; i < this.adjMatrix.length; i++) {
  // for (int j = 0; j < this.adjMatrix[i].length; j++) {
  // if (matriz[i][j] == 1 && this.adjMatrix[i][j] == 0) {
  ///
  // return false;
  // }
  // }
  // }
  // return true;
  // }

  public int getCountNodes() {
    return countNodes;
  }

  public void setCountNodes(int countNodes) {
    this.countNodes = countNodes;
  }

  public int getCountEdges() {
    return countEdges;
  }

  public void setCountEdges(int countEdges) {
    this.countEdges = countEdges;
  }

  public ArrayList<ArrayList<Edge>> getAdjList() {
    return adjList;
  }

  public void setAdjList(ArrayList<ArrayList<Edge>> adjList) {
    this.adjList = adjList;
  }

}