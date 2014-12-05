package fr.insaif.jajagaa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Acte de livrer un colis à un point de livraison.
 * Une livraison a un intervalle horaire précis, et peut être prévue sans retard, en retard ou faite
 * @author H4201
 */
public class Livraison extends Noeud {
/* TODO Définir comment ces attributs doivent être mis en place
    protected String raisonRetard;
*/

    /**
     * Temps pris pour faire une livraison
     */
    public static final int TPS_LIVRAISON_MIN = 10;

    /**
     * Formatter/parser de dates
     */
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");

    /**
     * Heure exacte (prévue) de la livraison (établie une fois la tournée calculée)
     */
    protected Calendar heureLivraison;
    
    /**
     * Client à qui on doit faire la livraison
     */
    protected int idClient;
    
    /**
     * Plage dans laquelle se déroule la livraison
     */
    protected PlageHoraire plageHoraire;
    
    /**
     * Id de la livraison
     */
    protected int idLivraison;

    /**
     * Constructeur d'une livraison à partir du noeud qui la concerne
     * @param n Noeud concernant la Livraison
     * @param idLiv ID de la Livraison en question
     * @param idClientLiv ID du client concerné par la Livraison
     * @param plageHoraire PlageHoraire dans laquelle se trouve la Livraison
     */
    public Livraison(Noeud n, int idLiv, int idClientLiv, PlageHoraire plageHoraire) {
        super(n.id, n.xMetre, n.yMetre, n.sortants);
        this.idLivraison = idLiv;
        this.idClient = idClientLiv;
        this.plageHoraire = plageHoraire;
        // Date de livraison par défaut: heure de création
        this.heureLivraison = Calendar.getInstance();
        this.setEtatLivraison(EtatNoeud.LIVRAISON);
    }
    
    /**
     * Constructeur de copie.
     * @param livraison
     */
    public Livraison(Livraison livraison){
        super((Noeud)livraison);
        heureLivraison = Calendar.getInstance();
        heureLivraison.setTime(livraison.heureLivraison.getTime());
        idClient = livraison.idClient;
        idLivraison = livraison.idLivraison;
    }

    /**
     * Accesseur de l'heure prévue de la Livraison
     * @return Heure exacte de la livraison
     */
    public Date getHeureLivraison() {
        return this.heureLivraison.getTime();
    }

    /**
     * Mutateur de l'heure exacte de la livraison à partir d'une Date
     * @param heureLivraison La nouvelle heure de fin
     */
    public void setHeureLivraison(Date heureLivraison) {
        this.heureLivraison.setTime(heureLivraison);
    }

    /**
     * 
     * Mutateur de l'heure exacte de la livraison à partir d'un String
     * @param heureLivraison La nouvelle heure exacte sous forme de chaîne
     * @throws ParseException 
     */
    public void setHeureLivraison(String heureLivraison) throws ParseException {
        try {
            this.heureLivraison.setTime(this.simpleDateFormat.parse(heureLivraison));
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
            throw e;
        }
    }
    
    /**
     * Accesseur de l'ID du client de la Livraison
     * @return Id du client à livrer
     */
    public int getIdClient() {
        return this.idClient;
    }

    /**
     * Mutateur de l'ID du client à livrer
     * @param idClient du client à livrer
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    
    /**
     * Décale la livraison d'un cran dans le planning.
     * Avance l'heure de livraison de TPS_LIVRAISON_MIN minutes
     */
    public void decalerHeureLivraison() {
        this.heureLivraison.add(Calendar.MINUTE, TPS_LIVRAISON_MIN);
    }
    
    /**
     * Calcule l'heure de départ pour la livraison.
     * @return Date correspondant à l'heure de fin de la Livraison
     */
    public Date getHeureFin(){
        Calendar heureFin = ((Calendar) this.heureLivraison.clone());
        heureFin.add(Calendar.MINUTE, TPS_LIVRAISON_MIN);
    	return heureFin.getTime();
    }
    
    /**
     * Accesseur de la PlageHoraire à laquelle appartient la Livraison
     * @return la Plage dans laquelle se déroule la livraison
     */
    public PlageHoraire getPlage() {
    	return plageHoraire;
    }
    
    /**
     * Mutateur de la PlageHoraire dans laquelle se trouve la Livraison
     * @param plageHoraire nouvelle plage dans laquelle se déroule la livraison
     */
    public void setPlage(PlageHoraire plageHoraire) {
    	this.plageHoraire = plageHoraire;
    }
}
