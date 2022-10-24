import java.util.ArrayList;
import java.util.Stack;

//import com.oracle.truffle.api.dsl.AOTSupport;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class GraphMatrix {
  private int countNodes;
  private int countEdges;
  private int[][] adjMatrix;

  public GraphMatrix(int countNodes) {
    this.countNodes = countNodes;
    this.adjMatrix = new int[countNodes][countNodes];
  }

  public GraphMatrix(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    // Create and fill adjMatrix with read edges
    this.adjMatrix = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdges(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  public int getCountNodes() {
    return this.countNodes;
  }

  public int getCountEdges() {
    return this.countEdges;
  }

  public int[][] getAdjMatrix() {
    return this.adjMatrix;
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        str += this.adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }
    return str;
  }

  public void addEdges(int source, int sink, int weight) {
    if (source < 0 || source > this.countNodes - 1 ||
        sink < 0 || sink > this.countNodes - 1 ||
        weight <= 0) {
      System.err.println("Invalid edge: [" + source + "]->[" + sink + "] = " + weight);
      return;
    } else {
      this.adjMatrix[source][sink] = weight;
      this.countEdges++;
    }
  }

  public int degree(int node) {
    int countDegree = 0;
    if (node < 0 || node > this.countNodes - 1) {
      System.err.println("Invalid node: " + node);
    }
    for (int i = 0; i < this.countNodes; i++) {
      if (this.adjMatrix[node][i] != 0) {
        countDegree++;
      }
    }
    return countDegree;
  }

  public int highestNode() {
    int highestDegree = 0;
    int highestNode = 0;
    int degree = 0;
    for (int i = 0; i < this.adjMatrix.length; ++i) {
      degree = this.degree(i);
      if (degree > highestDegree) {
        highestDegree = degree;
        highestNode = i;
      }
    }
    return highestNode;
  }

  public int highestDegree() {
    int highestDegree = 0;
    int degree = 0;
    for (int i = 0; i < this.adjMatrix.length; ++i) {
      degree = this.degree(i);
      if (degree > highestDegree)
        highestDegree = degree;
    }
    return highestDegree;
  }

  public int lowestDegree() {
    int lowestDegree = this.countNodes;
    for (int i = 1; i < this.adjMatrix.length; ++i) {
      int degree = this.degree(i);
      if (degree < lowestDegree)
        lowestDegree = degree;
    }
    return lowestDegree;
  }

  public GraphMatrix complement() {
    GraphMatrix g = new GraphMatrix(this.countNodes);
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if (i != j) {
          if (this.adjMatrix[i][j] == 0) {
            g.addEdges(i, j, 1);
          }
        }
      }
    }
    return g;
  }

  // 15/8/22
  public float density() {
    float density;
    float edges = this.countEdges;
    float nodes = this.countNodes;
    density = (edges) / (nodes * (nodes - 1));
    return density;
  }

  public boolean subGraph(GraphMatrix g2) {

    if (g2.getCountNodes() > this.countNodes) {
      return false;
    }
    int[][] matriz = g2.getAdjMatrix();
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if (matriz[i][j] == 1 && this.adjMatrix[i][j] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public ArrayList<Integer> buscaLargura(int s) {

    int[] desc = new int[this.countNodes];

    ArrayList<Integer> q = new ArrayList<>();
    ArrayList<Integer> r = new ArrayList<>();
    q.add(s);
    r.add(s);
    desc[s] = 1;

    while (q.size() > 0) {
      int u = q.remove(0);
      for (int v = 0; v < this.adjMatrix[u].length; v++) {
        if (this.adjMatrix[u][v] != 0) {
          if (desc[v] == 0) {
            q.add(v);
            r.add(v);
            desc[v] = 1;
          }

        }
      }
    }
    return r;
  }

  public boolean connected() {
    return this.buscaLargura(0).size() == this.countNodes;
  }

  // 24/8 - depth-first-search | DFS
  public ArrayList<Integer> buscaProfundidade(int s) {

    int[] desc = new int[this.countNodes];

    Stack<Integer> stack = new Stack<>();
    ArrayList<Integer> r = new ArrayList<>();
    stack.add(s);
    r.add(s);
    desc[s] = 1;

    while (stack.size() != 0) {
      int u = stack.get(stack.size() - 1);
      boolean desempilhar = true;
      for (int v = 0; v < this.adjMatrix[u].length; v++) {
        if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
          stack.add(v);
          r.add(v);
          desc[v] = 1;
          desempilhar = false;
          break;
        }
      }
      if (desempilhar)
        stack.remove(stack.size() - 1);
    }
    return r;
  }

  public boolean nonOriented() {
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = i + 1; j < this.adjMatrix[i].length; j++) {
        if (this.adjMatrix[i][j] == this.adjMatrix[j][i] && this.adjMatrix[i][j] == 1)
          return true;
      }
    }
    return false;
  }

  public ArrayList<Integer> profundidadeRec(int s) {
    int[] desc = new int[this.countNodes];
    ArrayList<Integer> r = new ArrayList<>();
    this.profundidadeRecAuxiliar(s, desc, r);
    return r;
  }

  public void profundidadeRecAuxiliar(int u, int[] desc, ArrayList<Integer> r) {
    desc[u] = 1;
    r.add(u);
    for (int v = 0; v < this.adjMatrix[u].length; v++)
      if (this.adjMatrix[u][v] != 0 && desc[v] == 0)
        this.profundidadeRecAuxiliar(v, desc, r);
  }

  /**
   * _Dijkstra
   * > origem unica
   * > algoritmo guloso
   * - escolhe sempre a opcao que parece a melhor no momento -- toma uma decisao e
   * mantem essa decisao até o final independente do proximo passo
   * > a cada passo busca caminhos melhores a partir de um nó u
   * 
   * algoritmo
   * dijkstra(G=(V,E,w), int s) // agora o grafo e ponderado
   * para cada v em V
   * dist[v] = infinito
   * pred[v]=null
   * dist[s]=0
   * Q=V
   * enquanto q != vazio
   * u = elemento de menor dist de Q
   * remova u de Q
   * para cada v adjacente a u
   * if(dist[v]>dist[u]+w(u,v))
   * dist[v]=dist[u]+w(u,v)
   * pred[u]=v
   * 
   * public int[] Dijkstra(int s) {
   * int[] dist = new int[this.countNodes];
   * int[] pred = new int[this.countNodes];
   * for (int i = 0; i < this.adjMatrix.length; i++) {
   * dist[i] = Integer.MAX_VALUE;
   * // pred[i]=null;
   * }
   * dist[s] = 0;
   * ArrayList<Integer> Q = new ArrayList<>();
   * int u;
   * for (int j = 0; j < this.adjMatrix.length; j++)
   * Q.add(j);
   * while (Q.size() != 0) {
   * 
   * }
   * 
   * }
   */

  /*
   * public int[] dijkstra(int s){
   * int[] dist = new int[this.countNodes];
   * int[] pred = new int[this.countNodes];
   * for(int i=0;i<this.adjMatrix.length;i++){
   * dist[i] = Integer.MAX_VALUE; //Vetor que armazena a distˆancia de s a cada
   * v´ertice
   * //pred[v] = null; . Vetor que armazena o predecessor de cada v´ertice
   * }
   * dist[s] = 0;
   * //Q = V; . Q: //lista dos v´ertices a serem processados
   * while (q.size() > 0) {
   * //u = i|min{dist[i], ∀i ∈ Q}; . u: V´ertice de menor distˆancia em Q
   * //Q = Q − {u}; . Remover u de Q
   * for (int v = 0; v < this.adjMatrix[u].length; v++) {
   * if(dist[v] > dist[u] + w(u, v)){
   * dist[v] = dist[u] + w(u, v);
   * pred[v] = u;
   * }
   * }
   * 
   * }
   */

  /*
   * https://moodlepresencial.ufop.br/pluginfile.php/1269620/mod_resource/content/
   * 0/A05%20Problema%20do%20Caminho%20M%C3%ADnimo.pdf
   * BellmanFord(G(V,E,w),s){
   * int[] dist = new int[this.countNodes];
   * int[] pred = new int[this.countNodes];
   * for(i=0;i<this.countNodes;i++){
   * dist[v]=Integer.MAX_VALUE;
   * pred[v]=null;
   * }
   * dist[s]=0;
   * for(i=0;i<this.adjMatrix.length;i++){
   * para cada (u,v) ∈ E{
   * if(dist[v]>dist[u]+w(u,v)){
   * dist[v]=dist[u]+w(u,v)
   * pred[v]=u
   * }
   * }
   * }
   * }
   * 
   * 
   */

  public void FloydWarshall() {
    int[][] dist = new int[this.countNodes][this.countNodes];
    int[][] pred = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        if (i == j) {
          dist[i][j] = 0;
        } else if (this.adjMatrix[i][j] != 0) {
          dist[i][j] = this.adjMatrix[i][j];
          pred[i][j] = i;
        } else {
          dist[i][j] = 9999;
        }
      }
    }
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        for (int k = 0; k < this.adjMatrix.length; k++) {
          if (dist[i][j] > (dist[i][k] + dist[k][j])) {
            dist[i][j] = dist[i][k] + dist[k][j];
            pred[i][j] = pred[k][j];
          }
        }
      }
    }
    System.out.println("Dist:\n ");
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        System.out.print("[" + dist[i][j] + "] ");
      }
      System.out.println("\n");
    }
    System.out.println("Pred:\n ");
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        System.out.print("[" + pred[i][j] + "] ");
      }
      System.out.println("\n");
    }
  }
}