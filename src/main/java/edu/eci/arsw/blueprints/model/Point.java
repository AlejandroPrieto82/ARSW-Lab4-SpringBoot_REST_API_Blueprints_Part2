package edu.eci.arsw.blueprints.model;

/**
 * Representa un punto en el plano cartesiano bidimensional.
 * Cada punto se define por sus coordenadas {@code x} y {@code y}.
 */
public class Point {

    /** Coordenada X del punto. */
    private int x;

    /** Coordenada Y del punto. */
    private int y;

    /**
     * Construye un punto en la posición (x, y).
     *
     * @param x coordenada en el eje X
     * @param y coordenada en el eje Y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor por defecto.
     * Crea un punto en la posición (0, 0).
     */
    public Point() {
    }

    /**
     * Retorna la coordenada X del punto.
     *
     * @return coordenada X
     */
    public int getX() {
        return x;
    }

    /**
     * Establece un nuevo valor para la coordenada X del punto.
     *
     * @param x nueva coordenada X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna la coordenada Y del punto.
     *
     * @return coordenada Y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece un nuevo valor para la coordenada Y del punto.
     *
     * @param y nueva coordenada Y
     */
    public void setY(int y) {
        this.y = y;
    }
}
