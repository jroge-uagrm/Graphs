package negocio;

public class Lista { // ADT Lista (Ordenada por Data y sin duplicados).
    private Nodo L;
    private int n;

    public Lista() {
        L = null;
        n = 0;
    }

    public void add(int data, double peso, double distancia) { // Inserta data a la Lista.
        Nodo Ant = null;
        Nodo p = L;

        while (p != null && data >= p.getData()) {
            Ant = p;
            p = p.getLink();
        }

        Nodo nuevo;
        if (Ant == null) { // x es menor a todos los que están en la Lista (o L==null)
            nuevo = new Nodo(data, peso, distancia);
            nuevo.setLink(L);
            L = nuevo;
            n++;
        } else if (Ant.getData() != data) { // x no está en la lista. Insertarlo entre Ant y p
            nuevo = new Nodo(data, peso, distancia);
            Ant.setLink(nuevo);
            nuevo.setLink(p);
            n++;
        }
    }

    public void del(int data) { // Elimina el nodo con Data=data, si existe.
        Nodo Ant = null;
        Nodo p = L;

        while (p != null && data > p.getData()) {
            Ant = p;
            p = p.getLink();
        }

        if (p != null && p.getData() == data) { // data existe en la Lista
            if (Ant == null)
                L = L.getLink(); // data era el primero de la Lista
            else
                Ant.setLink(p.getLink());

            p.setLink(null);
            n--;
        }
    }

    public boolean existe(int data) { // Devuelve true sii el data especificado está en la lista.
        return (exist(data) != null);
    }

    public int get(int k) { // Devuelve el k-ésimo elemento de la lista k=0, 1, ..., length()-1
        Nodo p = L;
        int i = 0;
        while (p != null) {
            if (i == k)
                return p.getData();

            p = p.getLink();
            i++;
        }

        System.err.println("Lista.get: Fuera de rango");
        return -1;
    }

    // modificacion y adicionando metodos a la class Lista para reutilizarlo en
    // grafos pesados
    public double getPeso(int data) {// Devuelve el Peso que acompaña al data. Si data no existe, devuelve 0.
        Nodo p = exist(data);
        if (p != null)
            return p.getPeso();

        return 0;
    }

    public double getDistancia(int data) {// Devuelve el Peso que acompaña al data. Si data no existe, devuelve 0.
        Nodo p = exist(data);
        if (p != null)
            return p.getDistancia();
        return 0;
    }

    /////////////////////////////////////////////////////////////////

    public int length() {
        return n;
    }

    @Override
    public String toString() {
        String S = "[";
        String coma = "";

        Nodo p = L;
        while (p != null) {
            S += coma + p.getData() + "/" + doubleToStr(p.getPeso());
            coma = ", ";
            p = p.getLink();
        }

        return S + "]";
    }

    private String doubleToStr(double d) { // Devuelve d sin el pto decimal innecesario.
        String s = "" + d;
        int posPto = s.indexOf('.');
        for (int i = posPto + 1; i < s.length(); i++) { // Ver si después del '.' todos son ceros.
            if (s.charAt(i) != '0')
                return s;
        }

        return s.substring(0, posPto);
    }

    private Nodo exist(int data) { // Devuelve el puntero al Nodo donde se encuentra data.
        Nodo p = L;

        while (p != null && data > p.getData()) {
            p = p.getLink();
        }

        if (p != null && p.getData() == data)
            return p;

        return null; // Devolver null, si data no existe en la lista.
    }
}
