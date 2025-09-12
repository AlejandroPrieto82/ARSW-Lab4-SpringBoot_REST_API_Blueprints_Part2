package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints;

    public InMemoryBlueprintPersistence() {
        this.blueprints = new ConcurrentHashMap<>();
        // Inicializar con al menos 3 planos; 2 del mismo autor
        Blueprint bp1 = new Blueprint("juan", "plano1", new Point[]{new Point(1,1), new Point(2,2)});
        Blueprint bp2 = new Blueprint("juan", "plano2", new Point[]{new Point(3,3), new Point(4,4)});
        Blueprint bp3 = new Blueprint("maria", "carro", new Point[]{new Point(5,5), new Point(6,6)});
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        Blueprint previous = blueprints.putIfAbsent(key, bp);
        if (previous != null) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + ", " + bprintname);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> result = blueprints.values().stream()
                .filter(bp -> bp.getAuthor().equals(author))
                .collect(Collectors.toSet());
        if (result.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return result;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return Set.copyOf(blueprints.values());
    }

    @Override
    public void updateBlueprint(String author, String name, Blueprint bp) throws BlueprintNotFoundException {
        Tuple<String, String> key = new Tuple<>(author, name);
        Blueprint prev = blueprints.replace(key, bp); // atomic replace
        if (prev == null) {
            throw new BlueprintNotFoundException("Cannot update non-existent blueprint: " + author + ", " + name);
        }
    }
}
