package MyJDBC;

import java.util.ArrayList;
import java.util.List;

public class Djikstra {
    //como os pesos que usaremos são double, usaremos como "infinito" o valor 100000000
    //que é muito maior que as distancias trabalhadas
    static final double inf = 100000000;
    static final int num_Vertices = 40;  //numero de vertices do grafo (sao 40 aeroportos)

    // função que encontra o vertice com menor distancia
    int minDistance(double path_array[], Boolean visitados[])   {
        // Inicializa valores
        double min = inf; //compara com o infinito
        int min_index = -1;
        for (int v = 0; v < num_Vertices; v++)
            if (visitados[v] == false && path_array[v] <= min) { //roda todos procurando o menor e "anotando"
                min = path_array[v];
                min_index = v;
            }
        return min_index;
    }

    //funcao que determina os passos do caminho de menor peso para um certo destino
    ArrayList caminho(int pais[], int dest_node){
        int b = dest_node;
        List<Integer> nos = new ArrayList<>();
        while(b != pais[b]){
            nos.add(b);
            b = pais[b];
        }
        nos.add(b);
        return (ArrayList) nos;
    }

    ArrayList algo_dijkstra(double[][] graph, int src_node, int dest_node) {
        double path_array[] = new double[num_Vertices]; // array com os pesos de cada caminho, de src até o no de indice i

        Boolean visitados[] = new Boolean[num_Vertices]; // contem os nos que foram visitados (true se ja foi visitado)

        int pais[] = new int[num_Vertices]; // array com os pais dos vertices (vertice imediatamente anterior no path)

        // Inicialmente todas as distancias sao infinitas e nenhum no foi visitado. Alem disso, ninguem tem pai ainda
        for (int i = 0; i < num_Vertices; i++) {
            path_array[i] = inf;
            visitados[i] = false;
            pais[i] = -1;
        }
        path_array[src_node] = 0; // Para o no source, o caminho tem peso 0 (distancia zero)
        pais[src_node] = src_node; // O pai do src_node é ele mesmo, e apenas o src tem essa propriedade

        // loop para achar a distancia para todos os outros vertices
        for (int count = 0; count < num_Vertices - 1; count++) { //roda para os n-1 vertices que sobraram serem analisados
            int u = minDistance(path_array, visitados);     //acha o no com minima distancia
            visitados[u] = true; //visita ele (o primeiro visitado e src, q tem peso 0. A partir dele sao atualizados os valores

            for (int v = 0; v < num_Vertices; v++) { // processa nos adjacentes e atualiza os custos, mudando os pais dos nos
                if (!visitados[v] && graph[u][v] != 0 && path_array[u] != inf && path_array[u] + graph[u][v] < path_array[v]) {
                    path_array[v] = path_array[u] + graph[u][v];
                    pais[v] = u;
                }
            }
        }
        List<Integer> retorno = caminho(pais, dest_node);
        System.out.println("A distancia até o destino é: " + path_array[dest_node] + " km");
        return (ArrayList) retorno;
    }

}


//Fonte: https://www.softwaretestinghelp.com/dijkstras-algorithm-in-java/
// recuperar caminho -> implementação propria
