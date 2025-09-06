package edu.eci.arsw.blueprints.persistence;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

/**
 * Implementación del filtro de redundancia.
 *
 * Este filtro elimina los puntos consecutivos duplicados en un plano.
 */
@Component("redundancyFilter")
public class RedundancyFilter implements BlueprintFilter {

    /**
     * Aplica el filtro de redundancia al plano dado.
     * 
     * @param bp el plano original
     * @return un nuevo plano sin puntos consecutivos repetidos
     */
    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filtered = new ArrayList<>();
        Point prev = null;
        for (Point p : bp.getPoints()) {
            if (prev == null || !(p.getX() == prev.getX() && p.getY() == prev.getY())) {
                filtered.add(p);
            }
            prev = p;
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered.toArray(new Point[0]));
    }
}
