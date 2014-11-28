package fr.insaif.jajagaa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Acte de livrer un colis à un point de livraison.
 * Une livraison a un intervalle horaire précis, et peut être prévue sans retard, en retard ou faite
 * @author gustavemonod
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
     * Nœud auquel il faut livrer la livraison
     */
    protected Noeud pointLivraison;

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
     * Id de la livraison
     */
    protected int idLivraison;

    /**
     * Constructeur d'une livraison à partir du noeud qui la concerne
     */
    public Livraison(Noeud n, int idLiv, int idClientLiv) {
        super(n.id, n.xMetre, n.yMetre, n.sortants, n.entrants);
        this.idLivraison = idLiv;
        this.idClient = idClientLiv;
        // Date de livraison par défaut: heure de création
        this.heureLivraison = Calendar.getInstance();
    }

    /**
     * @return Heure exacte de la livraison
     */
    public Date getHeureLivraison() {
        return this.heureLivraison.getTime();
    }

    /**
     * Modifie l'heure exacte de la livraison
     * @param heureLivraison La nouvelle heure de fin
     */
    public void setHeureLivraison(Date heureLivraison) {
        this.heureLivraison.setTime(heureLivraison);
    }

    /**
     * Modifie l'heure exacte de la livraison
     * @param heureLivraison La nouvelle heure exacte sous forme de chaîne
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
     * Décale la livraison d'un cran dans le planning (avance l'heure de livraison de TPS_LIVRAISON_MIN minutes)
     */
    public void decalerHeureLivraison() {
        this.heureLivraison.add(Calendar.MINUTE, TPS_LIVRAISON_MIN);
    }
    
    /**
     * Calcule l'heure de départ pour la livraison.
     */
    public Date getHeureFin(){
        Calendar heureFin = ((Calendar) this.heureLivraison.clone());
        heureFin.add(Calendar.MINUTE, TPS_LIVRAISON_MIN);
    	return heureFin.getTime();
    }
}
