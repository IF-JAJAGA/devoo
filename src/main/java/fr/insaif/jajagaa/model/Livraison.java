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
public class Livraison {
/* TODO Définir comment ces attributs doivent être mis en place
    protected String raisonRetard;
*/

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
    protected Date heureLivraison;

    /**
     * Constructeur à partir de l'identifiant et de la plage horaire de déroulement souhaitée
     */
    public Livraison(Noeud pointLivraison) {
        this.setPointLivraison(pointLivraison);
    }

    /**
     * @return Heure exacte de la livraison
     */
    public Date getHeureLivraison() {
        return heureLivraison;
    }

    /**
     * Modifie l'heure exacte de la livraison
     * @param heureLivraison La nouvelle heure de fin
     */
    public void setHeureLivraison(Date heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

    /**
     * Modifie l'heure exacte de la livraison
     * @param heureLivraison La nouvelle heure exacte sous forme de chaîne
     */
    public void setHeureLivraison(String heureLivraison) throws ParseException {
        try {
            this.heureLivraison = this.simpleDateFormat.parse(heureLivraison);
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
            throw e;
        }
    }

    /**
     * Nœud auquel il faut livrer la livraison
     * @return Nœud auquel il faut livrer la livraison
     */
    public Noeud getPointLivraison() {
        return pointLivraison;
    }

    /**
     * Modifie le nœud auquel il faut livrer la livraison
     * @param pointLivraison Nœud auquel il faut livrer la livraison
     */
    public void setPointLivraison(Noeud pointLivraison) {
        this.pointLivraison = pointLivraison;
    }
    
    /**
     * Calcule l'heure de d�part pour la livraison.
     */
    public Date getHeureFin(){
    	return new Date(this.heureLivraison.getTime() + 10*60000);
    }
}
