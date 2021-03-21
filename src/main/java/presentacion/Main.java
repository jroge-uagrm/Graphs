package presentacion;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import negocio.Grafo;

public class Main {

    private static Scanner myObj = new Scanner(System.in);
    private static Grafo G = new Grafo();

    public static void main(String[] args) {
        String opcion = "1";
        do {
            limpiarPantalla();
            mostrarLn("PROYECTO GRAFOS");
            mostrarLn("");
            G.printListas();
            mostrarMenu();
            opcion = myObj.nextLine();
            try {
                analizarOpcion(opcion);
            } catch (Exception e) {
                mostrarLn("ERROR");
                mostrarLn(e.getMessage());
                break;
            }
        } while (esOpcionValida(opcion));
        limpiarPantalla();
        mostrarLn("Saliendo...");
        mostrarLn("Adios!");
    }

    private static boolean esOpcionValida(String opcion) {
        return opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4");
    }

    private static void mostrarLn(String mensaje) {
        System.out.println(mensaje);
    }

    private static void mostrarMenu() {
        mostrarLn("");
        mostrarLn("Seleccione una opcion");
        mostrarLn("   1: Añadir ciudad");
        mostrarLn("   2: Añadir ruta");
        mostrarLn("   3: Ver camino mas corto");
        mostrarLn("   4: Ver camino mas barato");
        mostrarLn("   *: Salir");
        mostrarLn("");
        mostrarLn("Opcion:");
    }

    private static void limpiarPantalla() {
        for (int i = 1; i <= 30; i++) {
            mostrarLn("");
        }
    }

    private static void analizarOpcion(String opcion) {
        limpiarPantalla();
        switch (opcion) {
            case "1":
                añadirCiudad();
                break;
            case "2":
                añadirRuta();
                break;
            case "3":
                mostrarCaminoMasCorto();
                break;
            case "4":
                mostrarCaminoMasBarato();
                break;
        }
    }

    private static void stop() {
        myObj.nextLine();
    }

    private static void añadirCiudad() {
        mostrarLn("Ingresa el nombre de la ciudad:");
        String nombre = myObj.nextLine();
        if (nombre.length() == 0) {
            mostrarLn("Nombre no puede ser vacio");
            stop();
        } else {
            G.addVerticeConNombre(nombre);
        }
    }

    private static void añadirRuta() {
        G.printListas();
        mostrarLn("");
        mostrarLn("Ingresa el ID de la ciudad origen:");
        int origen = Integer.parseInt(myObj.nextLine());
        mostrarLn("Ingresa el ID de la ciudad destino:");
        int destino = Integer.parseInt(myObj.nextLine());
        mostrarLn("Ingresa el costo de la ruta (Bs.):");
        double costo = Double.parseDouble(myObj.nextLine());
        mostrarLn("Ingresa la distacia de la ruta (Km.):");
        double distancia = Double.parseDouble(myObj.nextLine());
        G.addArista(origen, costo, distancia, destino);
    }

    private static void mostrarCaminoMasCorto() {
        limpiarPantalla();
        G.printListas();
        mostrarLn("");
        mostrarLn("Ingresa el ID de la ciudad origen:");
        int origen = Integer.parseInt(myObj.nextLine());
        mostrarLn("Ingresa el ID de la ciudad destino:");
        int destino = Integer.parseInt(myObj.nextLine());
        mostrarLn("El camino mas corto en Kms. es:");
        List<Integer> camino = G.shortestPathRecorridoDistancia(origen, destino);
        mostrarLn(G.getNombre(camino.get(0)) + "->" + G.getNombre(camino.get(1)) + " - Kms." + G.getDistancia(camino.get(0), camino.get(1)));
        for (int i = 2; i <= camino.size() - 1; i++) {
            int idOrigen = camino.get(i - 1);
            int idDestino = camino.get(i);
            mostrarLn(G.getNombre(idOrigen) + "->" + G.getNombre(idDestino) + " - Kms." + G.getDistancia(idOrigen, idDestino));
        }
        mostrarLn("");
        mostrarLn("TOTAL: Kms:" + Float.toString(G.shortestPathDistancia(origen, destino)));
        stop();
    }

    private static void mostrarCaminoMasBarato() {
        limpiarPantalla();
        G.printListas();
        mostrarLn("");
        mostrarLn("Ingresa el ID de la ciudad origen:");
        int origen = Integer.parseInt(myObj.nextLine());
        mostrarLn("Ingresa el ID de la ciudad destino:");
        int destino = Integer.parseInt(myObj.nextLine());
        mostrarLn("El camino mas barato en Bs. es:");
        List<Integer> camino = G.shortestPathRecorridoCosto(origen, destino);
        mostrarLn(G.getNombre(camino.get(0)) + "->" + G.getNombre(camino.get(1)) + " - Bs." + G.getDistancia(camino.get(0), camino.get(1)));
        for (int i = 2; i <= camino.size() - 1; i++) {
            int idOrigen = camino.get(i - 1);
            int idDestino = camino.get(i);
            mostrarLn(G.getNombre(idOrigen) + "->" + G.getNombre(idDestino) + " - Bs." + G.getCosto(idOrigen, idDestino));
        }
        mostrarLn("");
        mostrarLn("TOTAL: Bs:" + Float.toString(G.shortestPathCosto(origen, destino)));
        stop();
    }
}
