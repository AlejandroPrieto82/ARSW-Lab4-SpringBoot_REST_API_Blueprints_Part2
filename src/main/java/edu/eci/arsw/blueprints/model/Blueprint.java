package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representa un plano (Blueprint), compuesto por un autor, un nombre
 * y una lista de puntos {@link Point} que definen su geometría.
 *
 * Esta clase permite crear planos con puntos iniciales,
 * agregar nuevos puntos y comparar planos por autor, nombre y puntos.
 */
public class Blueprint {

    /** Nombre del autor del plano. */
    private String author = null;

    /** Lista de puntos que conforman el plano. */
    private List<Point> points = null;

    /** Nombre o identificador del plano. */
    private String name = null;

    /**
     * Construye un plano con autor, nombre y un conjunto de puntos.
     *
     * @param author nombre del autor
     * @param name nombre del plano
     * @param pnts conjunto de puntos iniciales del plano
     */
    public Blueprint(String author, String name, Point[] pnts) {
        this.author = author;
        this.name = name;
        points = Arrays.asList(pnts);
    }

    /**
     * Construye un plano vacío con un autor y un nombre, pero sin puntos iniciales.
     *
     * @param author nombre del autor
     * @param name nombre del plano
     */
    public Blueprint(String author, String name) {
        this.name = name;
        points = new ArrayList<>();
    }

    /**
     * Constructor por defecto.
     * Crea un plano vacío sin autor, nombre ni puntos iniciales.
     */
    public Blueprint() {
    }

    /**
     * Retorna el nombre del plano.
     *
     * @return nombre del plano
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna el autor del plano.
     *
     * @return nombre del autor
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Retorna la lista de puntos que componen el plano.
     *
     * @return lista de puntos
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * Agrega un nuevo punto al plano.
     *
     * @param p punto a agregar
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    /**
     * Retorna una representación en cadena del plano, mostrando
     * el autor y el nombre.
     *
     * @return cadena descriptiva del plano
     */
    @Override
    public String toString() {
        return "Blueprint{" + "author=" + author + ", name=" + name + '}';
    }

    /**
     * Retorna un código hash para el plano.
     * Actualmente devuelve un valor constante.
     *
     * @return código hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Compara este plano con otro, verificando igualdad
     * en autor, nombre y lista de puntos.
     *
     * @param obj objeto a comparar
     * @return true si ambos planos son equivalentes; false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blueprint other = (Blueprint) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.points.size() != other.points.size()) {
            return false;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i) != other.points.get(i)) {
                return false;
            }
        }
        return true;
    }
}
