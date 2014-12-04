package fr.insaif.jajagaa.control.Commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.util.List;

/**
 * Classe du design pattern Command permettant de lire les Livraisons
 * @author Jérôme
 */
public class LireLivraisonsCommand implements Command{
    
    /**
     * 
     */
    private ZoneGeographique zoneAvant ;
    
    /**
     * ZoneGeographique impliquée dans la lecture des Livraisons.
     */
    private ZoneGeographique zone ;
    
    /**
     * 
     */
    private ZoneGeographique zoneApres ;
    
    /**
     * String menant au fichier XML où on récupère les Livraisons
     */
    private final String fichierLivraison;

    /**
     * La lecture d'un fichier de livraison par le parseur modifie chez la zone la liste des noeuds et les plages.
     * @param zone où se trouveront les Livraisons à récupérer
     * @param fichierLivraison contenant les Livraisons
     */
    public LireLivraisonsCommand(ZoneGeographique zone, String fichierLivraison) {
        this.zone = zone;
        this.fichierLivraison = fichierLivraison;
    }

    /**
     * Méthode permettant l'exécution de la lecture de fichier XML pour récupérer
     * les Livraisons dans le design pattern Command
     */
    @Override
    public void execute(){
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            zone = new ZoneGeographique(Controleur.getInstance().getZoneVierge());
            List<PlageHoraire> plages = null;
            try{
                plages = Parseur.lireLivraison(fichierLivraison, zone);
            }
            catch(ParseurException pe) {
                Controleur.getInstance().notifyError(pe);
                return;
            }
            
            zone.getTournee().setPlagesHoraire(plages);
            zoneApres = new ZoneGeographique(zone);
        }
        else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    /**
     * Méthode UNDO de la lecture des Livraisons du design pattern Command.
     */
    @Override
    public void undo() {
        zone = new ZoneGeographique(zoneAvant);
    }

    /**
     * Accesseur de la ZoneGeographique associée à l'instance de la classe
     * @return ZoneGeographique
     */
    public ZoneGeographique getZone() {
        return zone;
    }
    
    
}
