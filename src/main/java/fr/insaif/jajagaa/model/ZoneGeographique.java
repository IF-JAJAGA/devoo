package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Zone regroupant un certain nombre de points de livraison, de vueNoeuds et de tronçons reliants ces vueNoeuds.
 * @author H4201
 */
public class ZoneGeographique {

    /**
     * Tournee appartenant à la ZoneGeographique
     */
    protected Tournee tournee;
   
    /**
     * Noeud de la liste qui est l'entrepôt
     */
    protected Noeud entrepot;

    /**
     * Liste des noeuds qui composent la zone géographique
     */
    protected List<Noeud> noeuds = new ArrayList<>();

    /**
     * Constructeur de la zone géographique à partir de la liste des Noeuds.
     * Cette liste doit être non vide
     * @param noeuds La liste des Noeuds qui sont dans la zone (contenant l'entrepôt, en première place par défaut)
     */
    public ZoneGeographique(List<Noeud> noeuds) {
        tournee = new Tournee(this);
        this.setNoeuds(noeuds);
    }

    /**
     * Constructeur par copie
     * @param zone 
     */
    public ZoneGeographique(ZoneGeographique zone) {
        entrepot = (zone.entrepot==null) ? null : new Noeud(zone.entrepot);
        noeuds = new ArrayList<>();
        for (Noeud n : zone.noeuds){
            if (n instanceof Noeud){
                noeuds.add(n);
            }
            else if (n instanceof Livraison){
                noeuds.add((Livraison)n);
            }
        }
        tournee = new Tournee(zone, this);
        
        //On remet la correspondance entre les plages horaire des objets 
        //livraison et de l'objet tournée.
        List<PlageHoraire> plages = new ArrayList<>();
        for (Noeud n : zone.noeuds){
            if(n instanceof Livraison){
                Livraison lv = (Livraison)n;
                if(!plages.contains(lv.getPlage()))     plages.add(lv.getPlage());
            }
        }
        tournee.setPlagesHoraire(plages);
    }
    
    /**
     * Accesseur du Nœud dont l'ID est passé en paramètre
     * @param id L'id du nœud à renvoyer
     * @return Le nœud d'id donné en paramètre
     */
    public Noeud getNoeudById(int id){
        for(Noeud n : noeuds){
            if(n.getId() == id) return n;
        }
        return null;
    }

    /**
     * Accesseur du noeud désigné comme Entrepôt
     * @return Entrepôt de la zone
     */
    public Noeud getEntrepot() {
        return this.entrepot;
    }

    /**
     * Accesseur de la Tournee appartenant à cette ZoneGeographique
     * @return la Tournee appartenant à la zone
     */
    public Tournee getTournee() {
        return tournee;
    }
    

    /**
     * Mutateur de l'Entrepôt à partir de son indice
     * @param indice Indice où se trouve l'entrepôt dans la liste
     */
    public void setEntrepot(int indice) {
        this.setEntrepot(this.getNoeuds().get(indice));
    }

    /**
     * Mutateur de l'Entrepôt à partir d'un Noeud
     * @param entrepot Le nouvel entrepôt de la zone (qui doit déjà être présent dans la liste des Noeuds)
     */
    public void setEntrepot(Noeud entrepot) {
        assert this.getNoeuds().contains(entrepot);
        this.entrepot = entrepot;
    }

    /**
     * Accesseur de la liste des Noeuds de la zone
     * @return Liste des Noeuds composant la ZoneGeographique
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }

    /**
     * Mutateur de la liste des Noeuds de la zone
     * @param noeuds Liste des Noeuds à muter dans la ZoneGeographique
     */
    public void setNoeuds(List<Noeud> noeuds) {
        assert !noeuds.isEmpty();
        this.noeuds = noeuds;
    }
    
    /**
     * Supprime le Noeud qui possède l'ID idNoeud et ajoute la Livraison l à la place.
     * La livraison à ajouter et le Noeud à supprimer doivent avoir le même id de Noeud.
     * @param idNoeud identifiant du Noeud à supprimer
     * @param l Livraison à ajouter
     */
    public void modifierNoeudEnLivraison(int idNoeud, Livraison l){
        this.noeuds.remove(idNoeud);
        this.noeuds.add(l.id, l);
    }
    
    
    /**
     * Supprime la Livraison qui possède l'ID idLivraison et ajoute le noeud n à la place.
     * La livraison à supprimer et le Noeud à ajouter doivent avoir le même id de Noeud.
     * @param idLivraison identifiant de la Livraison à supprimer
     * @param n Noeud à ajouter
     */
    public void modifierLivraisonEnNoeud(int idLivraison, Noeud n){
        this.noeuds.remove(idLivraison);
        this.noeuds.add(n.id,n);
    }
    
    /**
     * Mutateur de la Tournee de la zone
     * @param tournee 
     */
    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }
}

