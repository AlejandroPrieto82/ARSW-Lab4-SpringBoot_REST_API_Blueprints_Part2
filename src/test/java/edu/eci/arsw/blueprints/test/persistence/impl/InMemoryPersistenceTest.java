package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para InMemoryBlueprintPersistence (JUnit 4)
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.",
                ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",
                bp, ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
    }

    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and author");
        } catch (BlueprintPersistenceException ex) {
            // OK, se esperaba la excepción
        }
    }

    @Test
    public void getNonExistingBlueprintThrowsException() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        try {
            ibpp.getBlueprint("noauthor", "noname");
            fail("Expected BlueprintNotFoundException when loading a non-existing blueprint.");
        } catch (BlueprintNotFoundException e) {
            // Se esperaba esta excepción
        }
    }

    @Test
    public void getBlueprintsByAuthorReturnsAll() throws Exception {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts1 = new Point[]{new Point(1, 1), new Point(2, 2)};
        Point[] pts2 = new Point[]{new Point(3, 3), new Point(4, 4)};

        Blueprint bp1 = new Blueprint("alice", "house", pts1);
        Blueprint bp2 = new Blueprint("alice", "car", pts2);

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);

        Set<Blueprint> aliceBps = ibpp.getBlueprintsByAuthor("alice");

        assertEquals("Expected 2 blueprints for author 'alice'", 2, aliceBps.size());
        assertTrue("Expected to contain blueprint 'house'", aliceBps.contains(bp1));
        assertTrue("Expected to contain blueprint 'car'", aliceBps.contains(bp2));
    }

    @Test
    public void getAllBlueprintsReturnsAll() throws Exception {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts1 = new Point[]{new Point(1, 1), new Point(2, 2)};
        Point[] pts2 = new Point[]{new Point(3, 3), new Point(4, 4)};
        Point[] pts3 = new Point[]{new Point(5, 5), new Point(6, 6)};

        Blueprint bp1 = new Blueprint("bob", "house", pts1);
        Blueprint bp2 = new Blueprint("alice", "car", pts2);
        Blueprint bp3 = new Blueprint("john", "plane", pts3);

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        ibpp.saveBlueprint(bp3);

        Set<Blueprint> all = ibpp.getAllBlueprints();

        assertEquals("Expected 3 blueprints in total", 3, all.size());
        assertTrue(all.contains(bp1));
        assertTrue(all.contains(bp2));
        assertTrue(all.contains(bp3));
    }
}
