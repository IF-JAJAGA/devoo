/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.util.List;

/**
 *
 * @author Jérôme
 */
public class LireLivraisonsCommand implements Command{
    private ZoneGeographique zoneAvant ;
    private ZoneGeographique zone ;
    private ZoneGeographique zoneApres ;
    private final String fichierLivraison;

    /**
     * La lecture d'un fichier de livraison par le parseur modifie chez la zone la liste des noeuds et les plages.
     * @param zone
     * @param fichierLivraison 
     */
    public LireLivraisonsCommand(String fichierLivraison) {
        this.zone = new ZoneGeographique(Controleur.getInstance().getZoneVierge());
        this.fichierLivraison = fichierLivraison;
    }

    @Override
    public void execute(){
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            List<PlageHoraire> plages;
            try{
                plages = Parseur.lireLivraison(fichierLivraison, zone);
            }
            catch(ParseurException pe) {
                return;
            }
            
            zone.getTournee().setPlagesHoraire(plages);
            zoneApres = new ZoneGeographique(zone);
            
            System.out.println("zone.getNoeuds().size() : " + zone.getNoeuds().size() + "zoneApres.getNoeuds().size() : " + zoneApres.getNoeuds().size());
            for(int i=0, len=zone.getNoeuds().size() ; i<len ; i++){
                if(zone.getNoeuds().get(i) instanceof Livraison){
                    System.out.println("Livraison : " + zoneApres.getNoeuds().get(i));
                }
            }
        }
        else {
            zone = zoneApres;
        }
        
    }

    @Override
    public void undo() {
        zone = zoneAvant;
    }

    public ZoneGeographique getZone() {
        return zone;
    }
    
    
}
