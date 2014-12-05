package fr.insaif.jajagaa.control.Commands;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Classe du design pattern Command permettant de supprimer une Livraison
 * @author alicia
 */
public class SuppressionLivraisonCommande implements Command {
    
    /**
     * Booléen indiquant si la suppression a été réalisée.
     */
    private boolean suppressionOk = false;
    
    /**
     * ZoneGeographique servant de sauvegarde.
     */
    private ZoneGeographique zoneAvant ;
    
    /**
     * ZoneGeographique permettant la suppression de la Livraison sélectionnée.
     */
    private ZoneGeographique zone ;
    
    /**
     * ZoneGeographique obtenu après suppression.
     */
    private ZoneGeographique zoneApres ;
    
    /**
     * Noeud (ou plutôt Livraison) sélectionné pour être supprimé.
     */
    private final Noeud noeudASup;

    /**
     * Constructeur de la suppression de Livraison du design pattern Command
     * @param zone ZoneGeographique à laquelle la Livraison à supprimer appartient
     * @param noeudASup Livraison à supprimer
     */
    public SuppressionLivraisonCommande(ZoneGeographique zone, Noeud noeudASup) {
        this.zone = zone;
        this.noeudASup = noeudASup;
    }
    
    /**
     * Méthode d'exécution de la suppression de Livraison du design pattern Command.
     */
    @Override
    public void execute(){
        suppressionOk = false;
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            try {
                suppressionOk = zone.getTournee().supprimerPointLivraison(noeudASup);
            } catch (HorsPlageException ex) {
                Controleur.getInstance().notifyError(ex);
                return;
            }
            
            zoneApres = new ZoneGeographique(zone);
        }else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    /**
     * Méthode UNDO de la suppression de Livraison du design pattern Command.
     */
    @Override
    public void undo() {
        zone = new ZoneGeographique(zoneAvant);
    }

    /**
     * Accesseur de la ZoneGeographique de l'instance de la classe
     * @return une ZoneGeographique
     */
    public ZoneGeographique getZone() {
        return zone;
    }
}
