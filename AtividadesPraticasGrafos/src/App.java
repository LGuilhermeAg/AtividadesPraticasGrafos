import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        /*GraphList g = new GraphList("lib/Anexo/cm/toy.txt");
        System.out.println(g);

        System.out.println(g.isOriented());
        System.out.println(g.prim());
        GraphList g1 = new GraphList(5);
        g1.addEdge(0, 1, 1);
        g1.addEdge(0, 2, 1);
        g1.addEdge(1, 4, 1);
        g1.addEdge(2, 3, 1);
        // GraphList g1 = new GraphList("lib/Anexo/cm/toy.txt");
        System.out.println(g1);
        GraphList g2 = g1.complement();
        System.out.println(g2);
        System.out.println(g1.subgraph(g2));
        System.out.println(g1.dfsRec(0));

        GraphMatrix g3 = new GraphMatrix("lib/Anexo/cm/toy.txt");
        g3.floydWarshall(0, 3);

        GraphMatrix g8 = new GraphMatrix(10);
        g8.addEdgeUnoriented(7, 5, 1);
        g8.addEdgeUnoriented(7, 1, 1);
        g8.addEdgeUnoriented(7, 2, 1);
        g8.addEdgeUnoriented(1, 0, 1);
        g8.addEdgeUnoriented(1, 4, 1);
        g8.addEdgeUnoriented(2, 3, 1);
        g8.addEdgeUnoriented(5, 6, 1);
        g8.addEdgeUnoriented(6, 8, 1);
        System.out.println(g8.bfs(7));
        System.out.println(g8.connected());
        GraphMatrix g4 = new GraphMatrix("lib/Anexo/cm/toy.txt");
        System.out.println(g4);

        GraphMatrix g5 = new GraphMatrix(4);
        g5.addEdge(0, 1, 1);
        g5.addEdge(1, 0, 1);
        g5.addEdge(0, 3, 1);
        g5.addEdge(3, 0, 1);
        System.out.println("=== g5 ===");
        System.out.println(g5);
        System.out.println("degree(0): " + g5.degree(0)); // 2
        System.out.println("degree(1): " + g5.degree(1)); // 1
        System.out.println("degree(2): " + g5.degree(2)); // 0
        System.out.println("degree(3): " + g5.degree(3)); // 1
        System.out.println("highestDegree: " + g5.highestDegree()); // 2
        System.out.println("lowestDegree: " + g5.lowestDegree()); // 0
        System.out.println("density: " + g5.density());
        System.out.println("\n=== g5 complement ===");
        System.out.println(g5.complement());
        GraphMatrix g6 = new GraphMatrix(3);
        g6.addEdge(0, 1, 1);
        g6.addEdge(1, 0, 1);
        // System.out.println("g6 is subGraph? " + g1.subGraph(g6)); // true
        // GraphMatrix g3 = new GraphMatrix(4);
        // g3.addEdge(1, 3, 1);
        // g3.addEdge(3, 1, 1);
        // System.out.println("g3 is subGraph? " + g1.subGraph(g3)); // false*/
        System.out.println("\n\n\n\n\n");
        GraphMatrix g7 = new GraphMatrix("lib/Anexo/cm/toy.txt");
        System.out.println(g7);
        System.out.println(g7.getCountNodes());
        System.out.println(g7.dfs(0));
        System.out.println(g7.connected());
        GraphList g9 = new GraphList("lib/Anexo/cm/toy.txt");
        System.out.println(g9);
        System.out.println(g9.getCountNodes());
        System.out.println(g9.dfs(0));
        System.out.println(g9.connected());
    }
}
