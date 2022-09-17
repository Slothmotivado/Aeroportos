package MyJDBC;

import static MyJDBC.Djikstra.inf;
import static MyJDBC.Djikstra.num_Vertices;

public class Matriz {
    double[][] matriz = new double[num_Vertices][num_Vertices];
    int orig, dest = 0;
    double origdest = 0;
    double destorig = 0;
    public Matriz(double[][] matriz, int orig, int dest){
        this.matriz = matriz;
        this.orig = orig;
        this.dest = dest;
        this.origdest = matriz[orig][dest];
        this.destorig = matriz[dest][orig];
    }

    public double[][] removerAresta(){
        matriz[orig][dest] = inf;
        matriz[dest][orig] = inf;
        return matriz;
    }

    public double[][] inserirAresta(){
        matriz[orig][dest] = origdest;
        matriz[dest][orig] = destorig;
        return matriz;
    }

}