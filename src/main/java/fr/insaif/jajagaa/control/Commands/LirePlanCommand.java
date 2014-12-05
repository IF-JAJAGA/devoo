package fr.insaif.jajagaa.control.Commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Classe du design pattern Command permettant de lire le plan
 * @author H4201
 */
public class LirePlanCommand implements Command{
    
    /**
     * ZoneGeographique utilisée dans le design pattern.
     */
    private ZoneGeographique zone;
    
    /**
     * ZoneGeographique servant de sauvegarde.
     */
    private ZoneGeographique zoneApres;
    
    /**
     * ZoneGeographique obtenu après nouvelle lecture.
     */
    private ZoneGeographique zoneAvant;
    
    /**
     * String menant au fichier XML qui permettra de récupérer le plan.
     */
    private String fichierPlan;
    

    /**
     * Constructeur de la lecture de plan dans le design pattern Command
     * @param zone affectée par la lecture
     * @param fichierPlan String menant au fichier XML de lecture
     */
    public LirePlanCommand(ZoneGeographique zone, String fichierPlan) {
        this.zone = zone;
        this.fichierPlan = fichierPlan;
    }

    /**
     * Méthode d'exécution de la lecture du plan dans le design pattern Command.
     */
    @Override
    public void execute() {
        if(zoneAvant == null){
            zoneAvant = zone;
            
            try { 
                zone = Parseur.lirePlan(fichierPlan);
            }
            catch ( ParseurException pe) {
                Controleur.getInstance().notifyError(pe);
                return;
            }
            zoneApres = zone;
            Controleur.getInstance().setZoneVierge(zoneApres);

        }else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    /**
     * Méthode UNDO de lecture du plan dans le design pattern de Command.
     */
    @Override
    public void undo() {
        zone = (zoneAvant==null) ? null : new ZoneGeographique(zoneAvant);
    }

    /**
     * Accesseur de la ZoneGeographique associée à l'instance de la classe
     * @return ZoneGeographique
     */
    public ZoneGeographique getZone() {
        return zone;
    }
    
}
