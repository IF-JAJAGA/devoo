/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;
import java.util.List;

/**
 *
 * @author Jérôme
 */
public class LireLivraisonsCommand implements Command{
    private List<PlageHoraire> plages;
    private List<PlageHoraire> plagesAvant;
    private List<PlageHoraire> plagesApres;
    private final ZoneGeographique zone;
    private final String fichierLivraison;

    public LireLivraisonsCommand(List<PlageHoraire> plages, ZoneGeographique zone, String fichierLivraison) {
        this.plages = plages;
        this.zone = zone;
        this.fichierLivraison = fichierLivraison;
    }

    @Override
    public void execute() {
        if(plagesAvant == null){
            plagesAvant = plages;
            plages = Parseur.lireLivraison(fichierLivraison, zone);
            plagesApres = plages;
        }
        else {
            plages = plagesApres;
        }
        
    }

    @Override
    public void undo() {
        plages = plagesAvant;
    }

    public List<PlageHoraire> getPlages() {
        return plages;
    }

    public ZoneGeographique getZone() {
        return zone;
    }
    
    
}
