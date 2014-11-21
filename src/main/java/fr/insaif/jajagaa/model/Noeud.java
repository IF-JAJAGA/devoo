package fr.insaif.jajagaa.model;

/**
 * Point défini où il est possible de s'arrêter sur la carte d'une zone géographique,
 * il comporte au moins un tronçon (entrant) le desservant et un tronçon (sortant) desservant un autre point.
 * @author gustavemonod
 */
public class Noeud {
/* TODO Définir comment ces attributs doivent être mis en place
    protected List<Troncon> entrants;
    protected List<Troncon> sortants;
    protected Collection<Livraison> livraisons;
*/

    /**
     * L'adresse à laquelle se trouve le noeud
     * Correspond à l'id dans le document XML.
     */
    private String adresse;
    private int x;
    private int y;

    /**
     * Constructeur du noeud à partir de l'adresse
     * @param adresse Adresse à laquelle se trouve le noeud
     */
    public Noeud(String adresse) {
        this.setAdresse(adresse);
    }

    /**
     * @return L'adresse à laquelle se trouve le noeud
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Modifie l'adresse à laquelle se trouve le noeud
     * @param adresse La nouvelle adresse à laquelle se trouve le noeud
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
