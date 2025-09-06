package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del sistema de persistencia de planos.
 *
 * Esta clase utiliza un {@link ConcurrentHashMap} para almacenar los planos
 * en memoria, identificados de manera única por una tupla {@link Tuple}
 * compuesta por autor y nombre del plano.
 *
 * Es un componente de Spring gestionado mediante anotaciones (@Component).
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    /** Almacén de planos, indexados por (autor, nombre). */
    private final Map<Tuple<String, String>, Blueprint> blueprints;

    /**
     * Crea una nueva instancia de persistencia en memoria,
     * inicializando la estructura de almacenamiento.
     */
    public InMemoryBlueprintPersistence() {
        this.blueprints = new ConcurrentHashMap<>();
    }

    /**
     * Guarda un nuevo plano en el sistema de persistencia.
     *
     * @param bp el plano a guardar
     * @throws BlueprintPersistenceException si el plano ya existe en memoria
     */
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        if (blueprints.containsKey(key)) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        }
        blueprints.put(key, bp);
    }

    /**
     * Recupera un plano específico a partir de su autor y nombre.
     *
     * @param author autor del plano
     * @param bprintname nombre del plano
     * @return el plano correspondiente
     * @throws BlueprintNotFoundException si no existe un plano con el autor y nombre indicados
     */
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + ", " + bprintname);
        }
        return bp;
    }

    /**
     * Recupera todos los planos de un autor específico.
     *
     * @param author autor cuyos planos se quieren consultar
     * @return conjunto de planos pertenecientes al autor
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) {
        return blueprints.values().stream()
                .filter(bp -> bp.getAuthor().equals(author))
                .collect(Collectors.toSet());
    }

    /**
     * Recupera todos los planos almacenados en memoria.
     *
     * @return conjunto de todos los planos registrados
     */
    @Override
    public Set<Blueprint> getAllBlueprints() {
        return Set.copyOf(blueprints.values());
    }
}
