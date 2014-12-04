package fr.insaif.jajagaa.control.Commands;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Classe faisant parti du design pattern Command
 * Elle concerne l'ajout manuel d'une livraison directement dans le graphe
 * @author alicia
 */
public class AjoutLivraisonCommande implements Command {
    
    /**
     * Booléen indiquant si l'ajout est possible.
     */
    private boolean ajoutOk = false;
    
    /**
     * Noeud (ou plutôt Livraison) précédent la livraison que l'on veut rajouter.
     */
    private Noeud noeudAvant;
    
    /**
     * Noeud désigné pour devenir une nouvelle Livraison.
     */
    private Noeud noeudALivrer;
    
    /**
     * 
     */
    private ZoneGeographique zoneAvant;
    
    /**
     * ZoneGeographique dans laquelle la Livraison devra être ajoutée.
     */
    private ZoneGeographique zone;
    
    /**
     * 
     */
    private ZoneGeographique zoneApres;
    
    /**
     * Int désignant l'ID du client qui devra être livré avec cette nouvelle Livraison.
     */
    private final int idClient;
    
    /**
     * Constructeur de l'ajout de livraison dans le design pattern Command
     * @param zone où la Livraison sera ajoutée
     * @param idClient qui sera associé à la Livraison
     * @param noeudAvant Livraison qui précédera la nouvelle Livraison
     * @param noeudALivrer attribué à la nouvelle Livraison
     */
    public AjoutLivraisonCommande(ZoneGeographique zone, int idClient, Noeud noeudAvant, Noeud noeudALivrer){
        this.zone = zone;
        this.noeudAvant = noeudAvant;
        this.noeudALivrer = noeudALivrer;
        this.idClient = idClient;
    }
    
    /**
     * Méthode permettant l'exécution de l'ajout d'une Livraison dans le design
     * pattern Command.
     */
    @Override
    public void execute(){
        ajoutOk = false;
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            try {
                ajoutOk = zone.getTournee().ajouterPointDeLivraison(noeudALivrer, this.idClient, (Livraison) noeudAvant);
            } catch (HorsPlageException ex) {
                Controleur.getInstance().notifyError(ex);
                return;
            }            
            zoneApres = new ZoneGeographique(zone);
        }
        else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    /**
     * Méthode UNDO de l'ajout d'une livraison suivant le design pattern Command.
     */
    @Override
    public void undo() {
        zone = new ZoneGeographique(zoneAvant);
    }

    /**
     * Accesseur de la ZoneGeographique associé à l'instance de la classe
     * @return ZoneGeographique
     */
    public ZoneGeographique getZone() {
        return zone;
    }
    
}
