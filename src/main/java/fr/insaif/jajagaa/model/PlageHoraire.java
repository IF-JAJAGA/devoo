package fr.insaif.jajagaa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Heure précise à laquelle est annoncée l'arrivée du livreur au client et est prévue la livraison.
 * Est contenue dans une seule plage horaire.
 * @author H4201
 */
public class PlageHoraire implements Comparable<PlageHoraire> {
    
    /**
     * Formatter/parser de dates
     */
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");

    /**
     * Heure de début de la plage horaire
     */
    protected Date heureDebut;

    /**
     * Heure de fin de la plage horaire
     */
    protected Date heureFin;

    /**
     * Attribut List<Livraison> de PlageHoraire
     */
    protected List<Livraison> livraisons = new LinkedList<>();

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
     * Constructeur par copie de PlageHoraire
     * @param PH PlageHoraire à copier 
     */
    public PlageHoraire(PlageHoraire PH){
        heureDebut = new Date(PH.heureDebut.getTime());
        heureFin = new Date(PH.heureFin.getTime());
        livraisons = new LinkedList<>();
        for(Livraison LV : PH.getLivraisons()){
            livraisons.add(new Livraison(LV));
        }
    }

    /**
     * Accesseur de l'heure de début de la plage horaire
     * @return Heure de début de la livraison
     */
    public Date getHeureDebut() {
        return heureDebut;
    }


    /**
     * Mutateur de l'heure de début de la plage horaire à partir d'une Date
     * @param heureDebut La nouvelle heure de début
     */
    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * Mutateur de l'heure de début de la plage horaire à partir d'une String
     * @param heureDebut La nouvelle heure de début sous forme de chaîne
     */
    @SuppressWarnings("deprecation")
	public void setHeureDebut(String heureDebut) {
        try {
            this.heureDebut = this.simpleDateFormat.parse(heureDebut);
            if(heureDebut.startsWith("12")){
            	this.heureDebut.setHours(12);
            }
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
        }
    }

    /**
     * Accesseur de l'heure de fin de la plage horaire
     * @return Heure de fin de la livraison
     */
    public Date getHeureFin() {
        return heureFin;
    }

    /**
     * Mutateur de l'heure de fin de la plage horaire à partir d'une Date
     * @param heureFin La nouvelle heure de fin
     */
    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Modifie l'heure de fin de la livraison à partir d'une String 
     * @param heureFin La nouvelle heure de fin sous forme de chaîne
     */
    @SuppressWarnings("deprecation")
	public void setHeureFin(String heureFin) {
        try {
            this.heureFin = this.simpleDateFormat.parse(heureFin);
            if(heureFin.startsWith("12")){
            	this.heureFin.setHours(12);
            }
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
    
    /**
     * Compare les horaires de début et de fin de deux PlageHoraire
     * @param t : PlageHoraire avec lequel on va comparer la PlageHoraire this
     * @return -1 si l'heure de début de t est supérieure ou égale à l'heure de début de this
     *          1 si l'heure de fin de t est inférieure ou égale à l'heure de début de this
     *          0 sinon.
     */
    @Override
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
