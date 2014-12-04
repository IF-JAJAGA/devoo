/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.ZoneGeographique;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alicia
 */
public class AjoutLivraisonCommande implements Command {
    private boolean ajoutOk = false;
    
    private Noeud noeudAvant;
    private Noeud noeudALivrer;
    
    private ZoneGeographique zoneAvant;
    private ZoneGeographique zone;
    private ZoneGeographique zoneApres;
    private int idClient;
    
    
    public AjoutLivraisonCommande(ZoneGeographique zone, int idClient, Noeud noeudAvant, Noeud noeudALivrer){
        this.zone = zone;
        this.noeudAvant = noeudAvant;
        this.noeudALivrer = noeudALivrer;
        this.noeudAvant  = noeudAvant;
        this.noeudALivrer = noeudALivrer;
        this.idClient = idClient;
    }
    
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
            System.out.println("zone.getTournee().getCheminsResultats().size() : " + zone.getTournee().getCheminsResultats().size());
            
            zoneApres = new ZoneGeographique(zone);
        }
        else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    @Override
    public void undo() {
        zone = new ZoneGeographique(zoneAvant);
    }

    public ZoneGeographique getZone() {
        return zone;
    }
    
}
