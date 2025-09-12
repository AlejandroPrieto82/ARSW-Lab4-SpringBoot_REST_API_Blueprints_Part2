package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {

    @Autowired
    private BlueprintsServices blueprintServices;

    private static final Logger LOG = Logger.getLogger(BlueprintAPIController.class.getName());

    @GetMapping
    public ResponseEntity<?> getAllBlueprints() {
        try {
            Set<Blueprint> all = blueprintServices.getAllBlueprints();
            return new ResponseEntity<>(all, HttpStatus.ACCEPTED); // 202 según enunciado
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error retrieving blueprints", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> bps = blueprintServices.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(bps, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint bp = blueprintServices.getBlueprint(author, bpname);
            return new ResponseEntity<>(bp, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Blueprint not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        } catch (BlueprintPersistenceException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error creating blueprint", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(@PathVariable String author,
                                             @PathVariable String bpname,
                                             @RequestBody Blueprint blueprint) {
        try {
            blueprintServices.updateBlueprint(author, bpname, blueprint);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error updating blueprint", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
