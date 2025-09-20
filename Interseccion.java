public class Interseccion {
    public static void main(String[] args) {
        Punto p1 = new Punto(0, 0);
        Punto p2 = new Punto(4, 4);
        Punto p3 = new Punto(0, 4);
        Punto p4 = new Punto(4, 0);

        Lado lado1 = new Lado(p1, p2, "L1");
        Lado lado2 = new Lado(p3, p4, "L2");

        boolean resultado = lado1.seInterseca(lado2);

        if (resultado) {
            System.out.println("Los lados NO se intersectan");
        } else {
            System.out.println("Los lados se intersectan");
        }
    }
}

class Punto {
    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double obtenerX() {
        return x;
    }

    public double obtenerY() {
        return y;
    }
}

class Lado {
    private Punto extremo1;
    private Punto extremo2;
    private String nombre;

    public Lado(Punto extremo1, Punto extremo2, String nombre) {
        this.extremo1 = extremo1;
        this.extremo2 = extremo2;
        this.nombre = nombre;
    }

    public Punto obtenerExtremo1() {
        return extremo1;
    }

    public Punto obtenerExtremo2() {
        return extremo2;
    }

    public boolean contiene(Punto p) {
        double x1 = extremo1.obtenerX();
        double x2 = extremo2.obtenerX();
        double y1 = extremo1.obtenerY();
        double y2 = extremo2.obtenerY();
        double px = p.obtenerX();
        double py = p.obtenerY();

        double minX = Math.min(x1, x2) - 1e-9;
        double maxX = Math.max(x1, x2) + 1e-9;
        double minY = Math.min(y1, y2) - 1e-9;
        double maxY = Math.max(y1, y2) + 1e-9;

        return (px >= minX && px <= maxX) && (py >= minY && py <= maxY);
    }

    public boolean seInterseca(Lado otroLado) {
        Recta r1 = new Recta(this);
        Recta r2 = new Recta(otroLado);

        Punto interseccion = r1.interseccion(r2);
        if (interseccion == null) {
            return false;
        }

        return this.contiene(interseccion) && otroLado.contiene(interseccion);
    }

    public String toString() {
        return "Lado: " + nombre + " [" + extremo1 + " - " + extremo2 + "]";
    }
}

class Recta {
    private double a, b, c;

    public Recta(Lado segmento) {
        a = segmento.obtenerExtremo1().obtenerY() - segmento.obtenerExtremo2().obtenerY();
        b = segmento.obtenerExtremo2().obtenerX() - segmento.obtenerExtremo1().obtenerX();
        c = segmento.obtenerExtremo1().obtenerX() * segmento.obtenerExtremo2().obtenerY()
          - segmento.obtenerExtremo2().obtenerX() * segmento.obtenerExtremo1().obtenerY();
    }

    public Punto interseccion(Recta r) {
        double a1 = r.a, b1 = r.b, c1 = r.c;
        double a2 = this.a, b2 = this.b, c2 = this.c;

        double D = a1 * b2 - a2 * b1;
        if (D < 0) D = -D;
        if (D < 1e-9) return null;

        double x = (b1 * (-c2) - b2 * (-c1)) / D;
        double y = (a2 * (-c1) - a1 * (-c2)) / D;

        return new Punto(x, y);
    }
}
