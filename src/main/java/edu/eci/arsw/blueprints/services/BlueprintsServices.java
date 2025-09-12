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

@Service
public class BlueprintsServices {

    @Autowired
    private BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("redundancyFilter")
    private BlueprintFilter filter;

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints().stream()
                .map(filter::filter)
                .collect(Collectors.toSet());
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return filter.filter(bpp.getBlueprint(author, name));
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author).stream()
                .map(filter::filter)
                .collect(Collectors.toSet());
    }

    public void updateBlueprint(String author, String name, Blueprint bp) throws BlueprintNotFoundException {
        bpp.updateBlueprint(author, name, bp);
    }
}
