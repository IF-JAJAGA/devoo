package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Zone regroupant un certain nombre de points de livraison, de vueNoeuds et de tronçons reliants ces vueNoeuds.
 * @author gustavemonod
 */
public class ZoneGeographique {

    protected Tournee tournee = new Tournee(this);
   
    /**
     * Noeud de la liste qui est l'entrepôt
     */
    protected Noeud entrepot;

    /**
     * Liste des noeuds qui composent la zone géographique.
     */
    protected List<Noeud> noeuds;

    /**
     * Constructeur de la zone géographique à partir de la liste des vueNoeuds (non vide)
     * @param noeuds La liste des vueNoeuds qui sont dans la zone (contenant l'entrepôt, en première place par défaut)
     */
    public ZoneGeographique(List<Noeud> noeuds) {
        this.setNoeuds(noeuds);
        this.setEntrepot(0);
    }

    public ZoneGeographique(ZoneGeographique zone) {
        tournee = new Tournee(zone.tournee, this);
        entrepot = new Noeud(zone.entrepot);
        noeuds = new ArrayList<>(zone.noeuds);
    }
    
    

    /**
     * Renvoie le nœud d'id donné en paramètre
     * @param id L'id du nœud à renvoyer
     * @return Le nœud d'id donné en paramètre
     */
    public Noeud getNoeudId(int id) {
        assert this.noeuds.get(id).getId() == id;
        return this.noeuds.get(id);
    }

    /**
     * Entrepôt de la zone
     * @return Entrepôt de la zone
     */
    public Noeud getEntrepot() {
        return this.entrepot;
    }

    public Tournee getTournee() {
        return tournee;
    }
    

    /**
     * Modifie la valeur de l'entrepôt (en choisissant un indice la liste)
     * @param indice Indice où se trouve l'entrepôt dans la liste
     */
    public void setEntrepot(int indice) {
        this.setEntrepot(this.getNoeuds().get(indice));
    }

    /**
     * Modifie l'entrepôt de la zone
     * @param entrepot Le nouvel entrepôt de la zone (qui doit déjà être présent dans la liste des vueNoeuds)
     */
    public void setEntrepot(Noeud entrepot) {
        assert this.getNoeuds().contains(entrepot);
        this.entrepot = entrepot;
    }

    /**
     * Liste des vueNoeuds qui forme une zone géographique (carte)
     * @return Liste des vueNoeuds qui forme une zone géographique (carte)
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }

    /**
     * Modifie la liste des vueNoeuds qui forme une zone géographique (carte)
     * @param noeuds Liste des vueNoeuds (non vide) qui forme une zone géographique (carte)
     */
    public void setNoeuds(List<Noeud> noeuds) {
        assert !noeuds.isEmpty();
        this.noeuds = noeuds;
    }
    
    /**
     * Supprime le Noeud qui possède l'id idNoeud et ajoute la livraison l à la place.
     * La livraison à ajouter et le Noeud à supprimer doivent avoir le même id de Noeud.
     * @param idNoeud
     * @param l 
     */
    public void modifierNoeudEnLivraison(int idNoeud, Livraison l){
        this.noeuds.remove(idNoeud);
        this.noeuds.add(l.id, l);
    }
}
