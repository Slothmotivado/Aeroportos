package MyJDBC;

public class Principal {
    public static void main(String[] args) {
        double graph[][] = new double[][] {
                { 0, 2, 1, 0, 0, 0},
                { 2, 0, 7, 0, 8, 4},
                { 1, 7, 0, 7, 0, 3},
                { 0, 0, 7, 0, 8, 4},
                { 0, 8, 0, 8, 0, 5},
                { 0, 4, 3, 4, 5, 0} };
        Djikstra g = new Djikstra();
        g.algo_dijkstra(graph, 4, 0);
    }
}
