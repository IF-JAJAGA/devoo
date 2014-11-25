package fr.insaif.jajagaa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Acte de livrer un colis à un point de livraison.
 * Une livraison a un intervalle horaire précis, et peut être prévue sans retard, en retard ou faite
 * @author gustavemonod
 */
public class Livraison {
/* TODO Définir comment ces attributs doivent être mis en place
    protected String raisonRetard;
    protected Noeud pointLivraison;
*/
	/**
     * Formatter/parser de dates
     */
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");
    
    /**
     * Identifiant de la livraison
     */
    protected String id;
 
    /**
     * Plage horaire pendant laquelle devrait se dérouler la livraison
     */
    protected PlageHoraire horaireDeroulement;
    
    /**
     * 
     */
    protected Date heureLivraison;
    
    /**
     * Constructeur à partir de l'identifiant et de la plage horaire de déroulement souhaitée
     * @param id Identifiant de la livraison
     * @param horaireDeroulement Plage horaire pendant laquelle devrait se dérouler la livraison
     */
    public Livraison(String id, PlageHoraire horaireDeroulement) {
        this.setId(id);
        this.setHoraireDeroulement(horaireDeroulement);
    }

    /**
     * @return Identifiant de la livraison
     */
    public String getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la livraison
     * @param id Le nouvel identifiant de la livraison
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Plage horaire pendant laquelle devrait se dérouler la livraison
     */
    public PlageHoraire getHoraireDeroulement() {
        return horaireDeroulement;
    }

    /**
     * Modifie la plage horaire pendant laquelle devrait se dérouler la livraison
     * @param horaireDeroulement Nouvelle plage horaire pendant laquelle devrait se dérouler la livraison
     */
    public void setHoraireDeroulement(PlageHoraire horaireDeroulement) {
        this.horaireDeroulement = horaireDeroulement;
    }
    
    /**
     * @return Heure de fin de la livraison
     */
    public Date getHeureLivraison() {
        return heureLivraison;
    }

    /**
     * Modifie l'heure de fin de la livraison
     * @param heureFin La nouvelle heure de fin
     */
    public void setHeureLivraison(Date heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

    /**
     * Modifie l'heure de fin de la livraison
     * @param heureFin La nouvelle heure de fin sous forme de chaîne
     */
    public void setHeureLivraison(String heureLivraison) {
        try {
            this.heureLivraison = this.simpleDateFormat.parse(heureLivraison);
        } catch (ParseException e) {
            System.err.println("Impossible de parser la date: pas de modification");
        }
    }
    
}
