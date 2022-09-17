package MyJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static MyJDBC.Djikstra.num_Vertices;

public class MyJDBC {
    static void Consulta(String origem, String destino, String escala) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_airport", "root", "qwezxcad");
            Statement statement = connection.createStatement();
            String accessDatabase = "INSERT INTO consultas" + " VALUES('"+ origem +"','"+destino+"','"+escala+"') ";
            statement.executeUpdate(accessDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static int Voltar(){
        int volta = 0;
        while(volta != 1 && volta != 2){
            System.out.println("Voltar para o menu? [1-Sim, 2-Não]");
            Scanner input = new Scanner(System.in);
            volta = input.nextInt();
            if(volta == 2)
                System.out.println("Obrigado pela consulta!");
        }
        return volta - 1;
    }
    static String ExecutarAlgoritmo(double[][] matriz, int orig, int dest, HashMap<Integer, Aeroporto> mapaSiglas){
        String escala = "";
        Matriz m = new Matriz(matriz, orig, dest);
        Djikstra g = new Djikstra();
        List<Integer> caminho = g.algo_dijkstra(m.removerAresta(), orig, dest);
        m.inserirAresta();
        System.out.println("A escala no caminho é em: ");
        for(int i=1; i< caminho.size() - 1; i++){
            System.out.println(mapaSiglas.get(caminho.get(i)+1).sigla + " (" + mapaSiglas.get(caminho.get(i)+1).municipio + ")");
            escala += mapaSiglas.get(caminho.get(i)+1).sigla += " ";
        }
        return escala;
    }
    public static void main(String[] args) {
        String Origem= "Origem";
        String Destino = "Destino";
        int orig = 0;
        int dest = 0;
        String estado;
        Scanner input = new Scanner(System.in).useDelimiter("\n");

        HashMap<Integer, Aeroporto> mapaSiglas = new HashMap<Integer, Aeroporto>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_airport", "root", "qwezxcad");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from aeroportos");
            while (resultSet.next()) {
                Aeroporto aer = new Aeroporto(resultSet.getString("sigla"),resultSet.getString("siglaEstado"),
                resultSet.getString("estado"),resultSet.getString("municipio"),
                resultSet.getDouble("latitude"),resultSet.getDouble("longitude"));
                mapaSiglas.put(resultSet.getInt("id"),aer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // gerando matriz de adjacencia a ser usada no programa
        double[][] matriz = new double[num_Vertices][num_Vertices];
        for(int i=0; i<num_Vertices; i++){
            for(int j=0; j<num_Vertices; j++){
                double lat1 = mapaSiglas.get(i+1).latitude;
                double long1 = mapaSiglas.get(i+1).longitude;
                double lat2 = mapaSiglas.get(j+1).latitude;
                double long2 = mapaSiglas.get(j+1).longitude;
                matriz[i][j] = new Haversine(lat1,long1,lat2,long2).distancia();
            }
        }
        //menu

        int menu = 0;
        while(menu == 0){
            int opcao = 0;
            while(opcao == 0){
                System.out.println("Bem-vindo ao programa dos aeroportos. Escolha uma opcao:");
                System.out.println("[1] - Para consultar a lista de aeroportos e escolher destino e origem.");
                System.out.println("[2] - Para inserir destino e origem diretamente.");
                System.out.println("[-1] - Para sair.");
                opcao = input.nextInt();
                if(opcao == 1){
                    System.out.println("Escolha o estado do aeroporto de origem para consultar, escrevendo seu nome ou sigla: ");
                    estado = input.next();
                    int origemCheck = 0, destinoCheck = 0;
                    for (int i=0; i< num_Vertices; i++){
                        if (estado.equals(mapaSiglas.get(i+1).siglaEstado) || estado.equals(mapaSiglas.get(i+1).estado)) {
                            System.out.println(mapaSiglas.get(i+1));
                            origemCheck = 1;
                        }
                    }
                    if(origemCheck == 0){
                        System.out.println("Estado invalido, comece novamente");
                        break;
                    } else if (origemCheck == 1){
                        System.out.println("Escolha a origem, digitando o codigo de 3 letras do aeroporto: ");
                        Origem = input.next();
                    }
                    System.out.println("Escolha o estado do aeroporto de destino para consultar, escrevendo seu nome ou sigla: ");
                    estado = input.next();
                    for (int i=0; i< num_Vertices; i++){
                        if (estado.equals(mapaSiglas.get(i+1).siglaEstado) || estado.equals(mapaSiglas.get(i+1).estado)) {
                            System.out.println(mapaSiglas.get(i+1));
                            destinoCheck = 1;
                        }
                    }
                    if(destinoCheck == 0){
                        System.out.println("Estado invalido, comece novamente");
                        break;
                    } else if (destinoCheck == 1){
                        System.out.println("Escolha o destino, digitando o codigo de 3 letras do aeroporto: ");
                        Destino = input.next();
                    }
                    for (int i=0; i< num_Vertices; i++) {
                        if (Origem.equals(mapaSiglas.get(i + 1).sigla))
                            orig = i;
                        if (Destino.equals(mapaSiglas.get(i + 1).sigla))
                            dest = i;
                    }
                    String escala = ExecutarAlgoritmo(matriz, orig, dest, mapaSiglas);
                    Consulta(Origem, Destino, escala);
                    menu = Voltar();
                }
                else if(opcao == 2){
                    System.out.println("Escolha a origem, digitando o codigo de 3 letras do aeroporto: ");
                    Origem = input.next();
                    System.out.println("Escolha o destino, digitando o codigo de 3 letras do aeroporto: ");
                    Destino = input.next();
                    for (int i=0; i< num_Vertices; i++){
                        if (Origem.equals(mapaSiglas.get(i+1).sigla))
                            orig = i;
                        if(Destino.equals(mapaSiglas.get(i+1).sigla))
                            dest = i;
                    }
                    String escala = ExecutarAlgoritmo(matriz, orig, dest, mapaSiglas);
                    Consulta(Origem, Destino, escala);
                    menu = Voltar();
                }
                else if(opcao == -1){
                    System.out.println("Obrigado pela consulta!");
                    menu = 1;
                }
                else{
                    System.out.println("Opcao invalida! Escolha um valor valido, por favor");
                    opcao = 0;
                }
            }
        }
    }
}