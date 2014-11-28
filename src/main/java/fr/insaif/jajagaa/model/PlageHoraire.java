package fr.insaif.jajagaa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Heure précise à laquelle est annoncée l'arrivée du livreur au client et est prévue la livraison.
 * Est contenue dans une seule plage horaire.
 * @author gustavemonod
 */
public class PlageHoraire implements Comparable<PlageHoraire> {
    /**
     * Formatter/parser de dates
     */
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");

    /**
     * Heure de début de la livraison
     */
    protected Date heureDebut;

    /**
     * Heure de fin de la livraison
     */
    protected Date heureFin;

    /**
     * TODO
     */
    protected List<Livraison> livraisons;

    /**
     * Construit une plage horaire avec un début et une fin
     * @param heureDebut Heure de début de la plage horaire
     * @param heureFin Heure de fin de la plage horaire
     */
    public PlageHoraire(Date heureDebut, Date heureFin) {
        this.setHeureDebut(heureDebut);
        this.setHeureFin(heureFin);
        this.livraisons = new LinkedList<Livraison>();
    }

    /**
     * Construit une plage horaire avec un début et une fin sous forme de chaînes
     * @param heureDebut Heure de début de la plage horaire sous forme de chaîne
     * @param heureFin Heure de fin de la plage horaire sous forme de chaîne
     */
    public PlageHoraire(String heureDebut, String heureFin) {
        this.setHeureDebut(heureDebut);
        this.setHeureFin(heureFin);
    }

    /**
     * @return Heure de début de la livraison
     */
    public Date getHeureDebut() {
        return heureDebut;
    }


    /**
     * Modifie l'heure de début de la livraison
     * @param heureDebut La nouvelle heure de début
     */
    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * Modifie l'heure de début de la livraison
     * @param heureDebut La nouvelle heure de début sous forme de chaîne
     */
    public void setHeureDebut(String heureDebut) {
        try {
            this.heureDebut = this.simpleDateFormat.parse(heureDebut);
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
        }
    }

    /**
     * @return Heure de fin de la livraison
     */
    public Date getHeureFin() {
        return heureFin;
    }

    /**
     * Modifie l'heure de fin de la livraison
     * @param heureFin La nouvelle heure de fin
     */
    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Modifie l'heure de fin de la livraison
     * @param heureFin La nouvelle heure de fin sous forme de chaîne
     */
    public void setHeureFin(String heureFin) {
        try {
            this.heureFin = this.simpleDateFormat.parse(heureFin);
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
        }
    }
    
    /**
     * mutateur de PlageHoraire pour muter une liste de livraisons
     * @param listLiv 
     */
    public void setLivraisons (List<Livraison> listLiv) {
        this.livraisons = listLiv;
    }
    
    /**
     * Accesseur de la liste de livraisons de la PlageHoraire
     * @return List<Livraison> 
     */
    public List<Livraison> getLivraisons() {
        return livraisons;
    }
    
    

    public int compareTo(PlageHoraire t) {
        if(this.heureFin.before(t.getHeureDebut()) || this.heureFin.equals(t.getHeureDebut())) {
            return -1;
        }
        else if(this.heureDebut.after(t.getHeureFin()) || this.heureDebut.equals(t.getHeureFin())) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
