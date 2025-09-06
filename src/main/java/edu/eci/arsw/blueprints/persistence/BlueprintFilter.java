package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Interfaz que define el contrato para los filtros de planos.
 * 
 * Los filtros tienen como propósito reducir o transformar la lista de
 * puntos de un {@link Blueprint}, ya sea eliminando redundancias,
 * submuestreando o aplicando cualquier otro criterio de optimización.
 */
public interface BlueprintFilter {

    /**
     * Aplica el filtro al plano dado.
     *
     * @param bp el plano a filtrar
     * @return un nuevo {@link Blueprint} con los puntos filtrados
     */
    Blueprint filter(Blueprint bp);
}
