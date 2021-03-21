package negocio;

import java.util.LinkedList;
import java.util.List;

public class Grafo { // Grafo dirigido y con peso

    private static final int MAXVERTEX = 49; // Máximo índice de V[]

    private Lista V[];
    private String nombres[];
    private int n; // "Dimensión" de V[]

    public Grafo() {
        V = new Lista[MAXVERTEX + 1]; // V[0..MAXVERTEX]
        n = -1;
        nombres = new String[MAXVERTEX + 1];
        marca = new boolean[MAXVERTEX + 1]; // Iniciar la ED para el marcado de los vértices.
    }

    public void addVerticeConNombre(String nombre) {
        if (n == MAXVERTEX) {
            System.err.println("Grafo.addVertice: Demasiados vértices (solo se permiten " + (MAXVERTEX + 1) + ")");
            return;
        }
        n++;
        V[n] = new Lista(); // Crear un nuevo vértice sin adyacentes (o sea con su Lista de adyacencia
        // vacía)
        nombres[n] = nombre;
    }

    public void addVertice() {
        if (n == MAXVERTEX) {
            System.err.println("Grafo.addVertice: Demasiados vértices (solo se permiten " + (MAXVERTEX + 1) + ")");
            return;
        }
        n++;
        V[n] = new Lista(); // Crear un nuevo vértice sin adyacentes (o sea con su Lista de adyacencia
        // vacía)
    }

    public int cantVertices() {
        return n + 1;
    }

    public boolean isVerticeValido(int v) {
        return (0 <= v && v <= n);
    }

    // grafo pesado
    // crea la arista con su peso
    public void addArista(int u, double peso, double distancia, int v) { // Crea la arista u-->v
        if (peso <= 0) { // validando que el peso no sea negativo
            System.err.println("Grafo.addArista:  El peso debe ser mayor que cero");
            return;
        }

        // validando los vertices
        String metodo = "addArista";
        if (!isVerticeValido(u, metodo) || !isVerticeValido(v, metodo)) {
            return; // No existe el vertice u o el vertice v.
        }
        V[u].add(v, peso, distancia); // Adicionar (data, peso) a la lista V[u]
    }

    public void delArista(int u, int v) { // Elimina la arista u-->v por el peso no por el vertice
        String metodo = "delArista";
        if (!isVerticeValido(u, metodo) || !isVerticeValido(v, metodo)) {
            return; // No existe el vertice u o el vertice v.
        }
        V[u].del(v); // Quitar v a la lista V[u]
    }

    public double costo(int u, int v) { // Devuelve el peso de la arista u-->v. Si esa arista no existe, devuelve 0
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }

        return V[u].getPeso(v);// getPeso es de la class Lista
    }

    public double distancia(int u, int v) { // Devuelve el peso de la arista u-->v. Si esa arista no existe, devuelve 0
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }

        return V[u].getDistancia(v);// getPeso es de la class Lista
    }

    public void dfs(int v) { // Recorrido Depth-First Search (en profundidad).
        if (!isVerticeValido(v, "dfs")) {
            return; // Validación. v no existe en el Grafo.
        }
        desmarcarTodos();
        System.out.print("DFS:");
        dfs1(v);
        System.out.println();
    }

    private void dfs1(int v) { // mask-function de void dfs(int)
        System.out.print(" " + v);
        marcar(v);

        for (int i = 0; i < V[v].length(); i++) { // for (cada w adyacente a v)
            int w = V[v].get(i);

            if (!isMarcado(w)) {
                dfs1(w);
            }
        }
    }

    public void bfs(int u) { // Recorrido Breadth-First Search (en anchura).
        if (!isVerticeValido(u, "bfs")) {
            return; // Validación. u no existe en el Grafo.
        }
        desmarcarTodos();
        LinkedList<Integer> cola = new LinkedList<>(); // "cola" = (vacía) = (empty)
        cola.add(u); // Insertar u a la "cola" (u se inserta al final de la lista).
        marcar(u);

        System.out.print("BFS:");
        do {
            int v = cola.pop(); // Obtener el 1er elemento de la "cola".
            System.out.print(" " + v);

            for (int i = 0; i < V[v].length(); i++) { // for (cada w adyacente a v)
                int w = V[v].get(i);

                if (!isMarcado(w)) {
                    cola.add(w);
                    marcar(w);
                }
            }
        } while (!cola.isEmpty());

        System.out.println();
    }

    public void printListas() { // Muestra las listas del Grafo. Util para el programador de esta class
        if (cantVertices() == 0) {
            System.out.println("(Grafo Vacío)");
        } else {
            System.out.println("ID-CIUDAD\n\t->DESTINOS\n");
            for (int i = 0; i <= n; i++) {
                System.out.println(i + " -" + nombres[i]);
                for (int j = 0; j < V[i].length(); j++) {
                    System.out.println("\t->" + nombres[V[i].get(j)] + "-Bs:" + V[i].getPeso(V[i].get(j)) + "-Kms:"
                            + V[i].getDistancia(V[i].get(j)));
                }
            }
        }
    }

    private boolean isVerticeValido(int v, String metodo) {
        boolean b = isVerticeValido(v);
        if (!b) {
            System.err.println("Grafo." + metodo + ": " + v + " no es un vértice del Grafo " + getIndicacion());
        }

        return b;
    }

    private String getIndicacion() { // Corrutina de boolean isVerticeValido(int, String)
        switch (cantVertices()) {
            case 0:
                return "(el grafo no tiene vértices). ";
            case 1:
                return "(el 0 es el único vértice). ";
            case 2:
                return "(0 y 1 son los únicos vértices). ";
            default:
                return "(los vértices van de 0 a " + (cantVertices() - 1) + "). ";
        }
    }

    public boolean hayCamino(int a, int z) {
        dfs(a);
        return isMarcado(z);
    }

    @Override
    public String toString() {
        if (cantVertices() == 0) {
            return "(Grafo Vacío)";
        }

        final String SEPARADOR = ", ";

        // Mostrar las aristas u-->v del grafo como (u, peso, v)
        desmarcarTodos();
        String S = "[";
        String coma = "";
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= n; k++) {
                double peso = costo(i, k);
                if (peso > 0) { // si hay peso hay arista
                    String arista = "(" + i + "," + peso + "," + k + ")";
                    S += coma + arista;
                    coma = SEPARADOR;
                    marcar(i); // porque No marca K???
                }
            }

            if (!isMarcado(i)) { // El vertice i no tiene aristas
                S += coma + i;
                coma = SEPARADOR;
            }
        }

        return S + "]";
    }

    // ********* Para el marcado de los vértices
    private boolean marca[];

    private void desmarcarTodos() {
        for (int i = 0; i <= n; i++) { /// con lo de la condicion de <=
            marca[i] = false;
        }
    }

    private void marcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = true;
        }
    }

    private void desmarcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = false;
        }
    }

    private boolean isMarcado(int u) { // Devuelve true sii el vertice u está marcado.
        return marca[u];
    }

    private int getNoMarcado() { // devuelve el vertice no marcado
        for (int i = 0; i < n; i++) {
            if (!isMarcado(i))// si hay un vertice no marcado
            {
                return i;
            }
        }
        return -1; // Todos estan marcados
    }

    // private int getVerticeMenor(double peso[],int z){
    // /* Devuelve el vertice del(indice de peso[]) No marcado que
    // tiene el peso menor*/
    // double menor = Integer.MAX_VALUE;
    // int verticeMenor = 0;
    //
    // for (int i = 0; i < peso.length; i++) {
    // if ((peso[i]< menor)&&(hayCamino(i, z))) {
    // menor = peso[i];
    // verticeMenor = i;
    // }
    // }
    // return verticeMenor;
    // }
    // public double shortestPath(int a, int z){
    // desmarcarTodos();
    // double peso[] = new double[n+1]; //hasta que casilla toy usando
    // //inicialisamos
    // for (int i = 0; i <= n; i++) {
    // peso[i] = Integer.MAX_VALUE;//iniciamos a todos los pesos de los vertices con
    // MasInfinito excepto al vertice a
    //
    // }
    // peso[a]=0;//excepto al vertice a que le aignamos cero (0)
    // while (!isMarcado(z)) {
    // int u = getVerticeMenor(peso, z);//obetenemos el menor peso del vertice
    // marcar(u);// marcamos el vertice elegido
    // int cantidad = V[u].length();
    // for (int k = 0; k < cantidad ; k++) {
    // int i = V[u].get(k);
    //
    // if (!isMarcado(i)) {
    // double p = peso[u] + costo(u,i);//costo me devuelve el peso entre u y v
    // if ((p < peso[i])) {
    // peso[i] = p;// actualizo el peso por el menor
    // }
    // }
    // }
    //
    // }
    //
    // return peso[z];
    // }
    //
    public float shortestPathCosto(int a, int z) {
        desmarcarTodos();
        float peso[] = new float[n + 1];
        for (int i = 0; i < peso.length; i++) {
            peso[i] = Integer.MAX_VALUE;
        }
        peso[a] = 0;
        while (!isMarcado(z)) {
            int u = verticeMenor(peso);
            marcar(u);
            for (int i = 0; i < V[u].length(); i++) {
                int ii = V[u].get(i);
                if (!isMarcado(ii)) {
                    peso[ii] = Float.min(peso[ii], (float) (peso[u] + costo(u, ii)));

                }
            }

        }
        return peso[z];
    }

    public float shortestPathDistancia(int a, int z) {
        desmarcarTodos();
        float peso[] = new float[n + 1];
        for (int i = 0; i < peso.length; i++) {
            peso[i] = Integer.MAX_VALUE;
        }
        peso[a] = 0;
        while (!isMarcado(z)) {
            int u = verticeMenor(peso);
            marcar(u);
            for (int i = 0; i < V[u].length(); i++) {
                int ii = V[u].get(i);
                if (!isMarcado(ii)) {
                    peso[ii] = Float.min(peso[ii], (float) (peso[u] + distancia(u, ii)));
                }
            }
        }
        return peso[z];
    }

    public List<Integer> shortestPathRecorridoDistancia(int a, int z) {
        desmarcarTodos();
        float peso[] = new float[n + 1];
        List<Integer> caminos[] = new LinkedList[n + 1];
        for (int i = 0; i < peso.length; i++) {
            peso[i] = Integer.MAX_VALUE;
            caminos[i] = new LinkedList<>();
            caminos[i].add(a);
        }
        peso[a] = 0;
        while (!isMarcado(z)) {
            int u = verticeMenor(peso);
            marcar(u);
            for (int i = 0; i < V[u].length(); i++) {
                int ii = V[u].get(i);
                if (!isMarcado(ii)) {
                    if (peso[ii] < (float) (peso[u] + distancia(u, ii))) {
                        peso[ii] = peso[ii];
                    } else {
                        peso[ii] = (float) (peso[u] + distancia(u, ii));
                        caminos[ii] = new LinkedList<>();
                        for (int x : caminos[u]) {
                            caminos[ii].add(x);
                        }
                        caminos[ii].add(ii);
                    }
                }
            }
        }
        return caminos[z];
    }

    public static void main(String[] args) {
        Grafo g = new Grafo();
        g.addVertice();
        g.addVertice();
        g.addVertice();
        g.addVertice();
        //g.addArista(0, peso, distancia, 1);
        g.addArista(0, 1, 5, 1);
        g.addArista(0, 2, 20, 2);
        g.addArista(0, 3, 30, 3);
        g.addArista(1, 12, 5, 2);
        g.addArista(1, 13, 130, 3);
        g.addArista(2, 23, 5, 3);
        g.printListas();

        int a = 0;
        int d = 3;
        System.out.println(g.shortestPathRecorridoDistancia(a, d).toString());
        System.out.println(g.shortestPathDistancia(a, d));
        System.out.println(g.shortestPathRecorridoCosto(a, d).toString());
        System.out.println(g.shortestPathCosto(a, d));
    }

    public List<Integer> shortestPathRecorridoCosto(int a, int z) {
        desmarcarTodos();
        float peso[] = new float[n + 1];
        List<Integer> caminos[] = new LinkedList[n + 1];
        for (int i = 0; i < peso.length; i++) {
            peso[i] = Integer.MAX_VALUE;
            caminos[i] = new LinkedList<>();
            caminos[i].add(a);
        }
        peso[a] = 0;
        while (!isMarcado(z)) {
            int u = verticeMenor(peso);
            marcar(u);
            for (int i = 0; i < V[u].length(); i++) {
                int ii = V[u].get(i);
                if (!isMarcado(ii)) {
                    if (peso[ii] < (float) (peso[u] + costo(u, ii))) {
                        peso[ii] = peso[ii];
                    } else {
                        peso[ii] = (float) (peso[u] + costo(u, ii));
                        caminos[ii] = new LinkedList<>();
                        for (int x : caminos[u]) {
                            caminos[ii].add(x);
                        }
                        caminos[ii].add(ii);
                    }
                }
            }
        }
        return caminos[z];
    }

    private int verticeMenor(float peso[]) {
        float pos = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < peso.length; i++) {
            int temp = (int) peso[i];
            if (temp < min && (!isMarcado(i))) {
                pos = i;
                min = temp;
            }
        }
        return (int) pos;
    }

    public String getNombre(int i) {
        return nombres[i];
    }

    public double getDistancia(int u, int v) {
        return V[u].getDistancia(v);
    }

    public double getCosto(int u, int v) {
        return V[u].getPeso(v);
    }

}// END
