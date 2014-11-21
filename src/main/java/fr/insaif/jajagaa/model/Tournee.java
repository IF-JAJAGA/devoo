package fr.insaif.jajagaa.model;

import java.util.List;

/**
 * Itinéraire d'un livreur dans une seule zone géographique, partant du dépôt et revenant au dépôt,
 * elle est représentée par une liste ordonnée de chemins
 * @author gustavemonod
 */
public class Tournee {
    protected ZoneGeographique zone;
    protected Camion camion;
    protected List<PlageHoraire> horaires;
    protected List<Chemin> chemins;
}
