package edu.eci.arsw.blueprints.services;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

/**
 * Servicio principal para la gestión de planos.
 *
 * Provee operaciones de alto nivel para:
 * - Registrar nuevos planos.
 * - Consultar planos individuales.
 * - Consultar planos por autor.
 * - Consultar todos los planos disponibles.
 *
 * Además, antes de retornar un plano, aplica un {@link BlueprintFilter}
 * para reducir o transformar sus puntos según el criterio de filtrado configurado.
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    private BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("redundancyFilter") // Cambiar a "subsamplingFilter" para probar otro filtro
    private BlueprintFilter filter;

    /**
     * Registra un nuevo plano en el sistema.
     *
     * @param bp el plano a registrar
     * @throws BlueprintPersistenceException si ya existe un plano con el mismo nombre y autor,
     *                                       o si ocurre un error de persistencia.
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /**
     * Obtiene todos los planos almacenados, aplicando el filtro configurado.
     *
     * @return un conjunto de planos filtrados
     */
    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints().stream()
                .map(filter::filter)
                .collect(Collectors.toSet());
    }

    /**
     * Obtiene un plano específico, aplicando el filtro configurado.
     *
     * @param author autor del plano
     * @param name nombre del plano
     * @return el plano filtrado
     * @throws BlueprintNotFoundException si no se encuentra un plano con ese autor y nombre
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return filter.filter(bpp.getBlueprint(author, name));
    }

    /**
     * Obtiene todos los planos de un autor específico, aplicando el filtro configurado.
     *
     * @param author autor cuyos planos se quieren consultar
     * @return un conjunto de planos filtrados del autor
     * @throws BlueprintNotFoundException si el autor no tiene planos registrados
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author).stream()
                .map(filter::filter)
                .collect(Collectors.toSet());
    }
}
