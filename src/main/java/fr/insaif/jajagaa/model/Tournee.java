package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;

/**
 * Itinéraire d'un livreur dans une seule zone géographique, partant du dépôt et revenant au dépôt,
 * elle est représentée par une liste ordonnée de cheminsResultats
 *
 * @author gustavemonod
 */
public class Tournee {
    /**
     * Zone Géographique (unique) dans laquelle se déroule la tournée.
     */
    protected ZoneGeographique zone;

    /**
     * Liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     */
    protected List<PlageHoraire> plagesHoraire = new ArrayList<>();

    /**
     * Liste ordonnée des cheminsResultats à parcourir au cours de la tournée.
     * Le premier chemin doit partir de l'entrepôt de la zone géographique.
     * Le dernier chemin doit arriver à l'entrepôt.
     * La fin d'un chemin doit être le début du chemin de l'autre.
     */
    protected List<Chemin> cheminsResultats = new LinkedList<>();

    /**
     * Résolveur de contraintes pour un graphe
     */
    protected TSP tsp;

    /**
     * Graphe des livraisons (transformé pour prendre en compte les plages horaires)
     */
    protected LivraisonGraph graph;

    /**
     * Création d'une tournée à partir d'une zone et d'une demande de livraisons (List<PlageHoraire>)
     */
    public Tournee(ZoneGeographique zone) {
        this.zone = zone;
    }

    /**
     * Constructeur par copie.
     * @param zoneCopiee
     * @param zone 
     */
    public Tournee(ZoneGeographique zoneCopiee, ZoneGeographique zone) {
        this.zone = zone;
        plagesHoraire = new ArrayList<>();
        for(PlageHoraire PH : zoneCopiee.tournee.plagesHoraire){
            plagesHoraire.add(new PlageHoraire(PH));
        }
        cheminsResultats = new LinkedList<>();
        for(Chemin Chr : zoneCopiee.tournee.cheminsResultats){
            cheminsResultats.add(new Chemin(Chr));
        }
        graph = new LivraisonGraph(this.getCheminsPossibles());
        tsp = new TSP(graph);
    }

    /**
     * Liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     * @return Liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     */
    public List<PlageHoraire> getPlagesHoraire() {
    	return this.plagesHoraire;
    }

    /**
     * Modifie la liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     * @param plagesHoraire Liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     */
    public void setPlagesHoraire(List<PlageHoraire> plagesHoraire) {
        this.plagesHoraire = plagesHoraire;
        this.graph = new LivraisonGraph(this.getCheminsPossibles());
        this.tsp = new TSP(graph);
    }

    /**
     * Liste ordonnée des cheminsResultats à parcourir au cours de la tournée.
     * Le premier chemin doit partir de l'entrepôt de la zone géographique.
     * Le dernier chemin doit arriver à l'entrepôt.
     * La fin d'un chemin doit être le début du chemin de l'autre.
     * @return Liste ordonnée des cheminsResultats à parcourir au cours de la tournée.
     */
    protected List<Chemin> getCheminsPossibles() {
        List<Chemin> cheminsPossibles = new ArrayList<Chemin>();
        if(plagesHoraire.isEmpty()) return cheminsPossibles;
        
        LivraisonGraphVertex entrepot = new LivraisonGraphVertex(this.zone.getEntrepot().getId(), true);
        Map<Integer, List<LivraisonGraphVertex>> graphVertexPerPlage = new HashMap<Integer, List<LivraisonGraphVertex>>();
        
        int taille = this.plagesHoraire.size();
        for(int i=0; i<taille; i++) {
            graphVertexPerPlage.put(i, new ArrayList<LivraisonGraphVertex>());
            List<Livraison> livs = this.plagesHoraire.get(i).getLivraisons();
            int nbLivraisons = livs.size();
            for (Livraison liv : livs) {
                graphVertexPerPlage.get(i).add(new LivraisonGraphVertex(liv.getId(), false));
            }
        }

        // Chemins les plus courts de l'entrepôt vers tous les nœuds de la première plage horaire
        //ICI
        for (LivraisonGraphVertex vertex: graphVertexPerPlage.get(0)) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, entrepot, vertex);
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }
        
        // Tous les chemins entre les nœuds d'une même plage horaire
        for (int i = 0; i < taille; i++) {
            List<LivraisonGraphVertex> vertices = graphVertexPerPlage.get(i);
            int size = vertices.size();
            for (int j = 0; j < size; j++) {
                for (LivraisonGraphVertex vertice : vertices) {
                    LivraisonGraphVertex vJ = vertices.get(j);
                    // On ne crée le chemin que pour deux sommets différents
                    if (vJ.getIdNoeud()!= vertice.getIdNoeud()) {
                        Chemin plusCourt = Dijkstra.plusCourtChemin(zone, vJ, vertice);
                        if (plusCourt != null) {
                            cheminsPossibles.add(plusCourt);
                        }
                    }
                }
            }
        }

        // Tous les chemins possibles de tous les nœuds de la plage horaire n vers la plage horaire n + 1
        for (int i = 1; i < taille; ++i) {
            List<LivraisonGraphVertex> verticesD = graphVertexPerPlage.get(i-1);
            List<LivraisonGraphVertex> verticesA = graphVertexPerPlage.get(i);
            for (LivraisonGraphVertex depart: verticesD) {
                for (LivraisonGraphVertex arrivee: verticesA) {
                    Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, depart, arrivee);
                    if (plusCourt != null) {
                        cheminsPossibles.add(plusCourt);
                    }
                }
            }
        }

        // Chemins les plus courts de tous les nœuds de la dernière plage horaire vers l'entrepôt
        for (LivraisonGraphVertex vertex: graphVertexPerPlage.get(taille - 1)) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, vertex, entrepot);
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }

        return cheminsPossibles;
    }

    /**
     * Résoud le problème de tournée la plus courte avec les plages horaires
     * @param TIME_LIMIT_MS Temps limite donné pour résoudre le problème
     * @return L'état de réponse (trouvé, pas trouvé, optimal, etc.) après le temps utilisé
     */
    public SolutionState solve(int TIME_LIMIT_MS) throws HorsPlageException {
        SolutionState state = this.tsp.solve(TIME_LIMIT_MS, this.graph.getNbVertices() * this.graph.getMaxArcCost() + 1);
        if (state == SolutionState.OPTIMAL_SOLUTION_FOUND || state == SolutionState.SOLUTION_FOUND) {
            this.getCheminsResultats().clear();
            int[] next = this.tsp.getNext();
            int indexEntrepot = this.graph.getIndexNoeudById(this.zone.getEntrepot().getId());
            List<LivraisonGraphVertex> vertices = this.graph.getNoeuds();
            List<LivraisonGraphVertex> orderedVertices = new ArrayList<LivraisonGraphVertex>();
            if(next.length > 0) {

                // On ajoute l'entrepôt
                orderedVertices.add(vertices.get(indexEntrepot));

                PlageHoraire currentPlage = null;
                Calendar currentTime = Calendar.getInstance();

                // Pour tous les points de livraison suivants, on ajoute à la liste ordonnée en mettant à jour l'horaire
                for (int i = next[indexEntrepot]; i != indexEntrepot; i = next[i]) {
                    LivraisonGraphVertex currentVertex = vertices.get(i);
                    Livraison currentLivraison = ((Livraison) zone.getNoeudById(currentVertex.getIdNoeud()));

                    // Recherche de la plage horaire d'une livraison
                    for (PlageHoraire plage : this.getPlagesHoraire()) {
                        if (plage.getLivraisons().contains(currentLivraison)) {

                            // Si c'est une nouvelle plage horaire, on remet le temps courant au départ
                            if (plage != currentPlage) {
                                currentPlage = plage;
                                currentTime.setTime(currentPlage.getHeureDebut());
                            }
                            break;
                        }
                    }

                    // Ajout de la Livraison à la liste ordonnée
                    orderedVertices.add(currentVertex);
                }

                this.buildCheminResultat(orderedVertices);
            }
        }
        return state;
    }

    /**
     * TODO
     * @param vertices
     */
    protected void buildCheminResultat(List<LivraisonGraphVertex> vertices) throws HorsPlageException{
        int i = 0;
        int tempsSecondes;
        LivraisonGraphVertex depart, arrivee;
        List<Troncon> listTroncons;
        PlageHoraire plage = null;
        Calendar heureDebut = Calendar.getInstance();
        while(i < (vertices.size()-1)) {
            depart = vertices.get(i);
            arrivee = vertices.get(i+1);

            Chemin chemin = depart.getSortantByDest(arrivee);
            if(chemin == null) throw new NullPointerException("Noeud sortant non trouvé");
            this.addCheminResultat(chemin);
            
            //TODO faire ici les horaires
            listTroncons = chemin.getTroncons();
            tempsSecondes = 0;
            for(Troncon troncon : listTroncons) {
            	tempsSecondes += troncon.getLongueurMetre()/troncon.getVitesse();
            }
            Noeud noeudDest = zone.getNoeudById(chemin.getDestination().getIdNoeud());
            if(noeudDest.getId() != zone.getEntrepot().getId()){
                Livraison livraisonDest = (Livraison) noeudDest;
                if(plage == null){
                    plage = livraisonDest.getPlage();
                    heureDebut.setTime(plage.getHeureDebut());
                }
                heureDebut.add(Calendar.SECOND, tempsSecondes);
                if (heureDebut.getTime().after(plage.getHeureFin())){
                    throw new HorsPlageException();
                } else if (heureDebut.getTime().before((livraisonDest.getPlage().getHeureDebut()))) {
                	heureDebut.setTime(livraisonDest.getPlage().getHeureDebut());
                }
                livraisonDest.setHeureLivraison(heureDebut.getTime());
                heureDebut.add(Calendar.MINUTE, Livraison.TPS_LIVRAISON_MIN);
            }
            i++;
        }
    }

    /**
     * L'état de résolution du problème actuel (après le dernier appel à solve)
     * @return L'état de résolution du problème actuel (après le dernier appel à solve)
     */
    public SolutionState getSolutionState() {
        return this.tsp.getSolutionState();
    }

    /**
     * Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @return Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     */
    public List<Chemin> getCheminsResultats() {
        return cheminsResultats;
    }

    /**
     * Ajoute un chemin à la liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @param chemin Chemin à ajouter
     */
    public void addCheminResultat(Chemin chemin) {
        this.cheminsResultats.add(chemin);
    }

    /**
     * Modifie la liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @param cheminsResultats Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     */
    public void setCheminsResultats(List<Chemin> cheminsResultats) {
        this.cheminsResultats = cheminsResultats;
    }

    /**
     * Ajoute un point de livraison dans la tournée, en recalculant localement le meilleur chemin et les horaires
     * @param noeudALivrer noeud à ajouter à la tournée
     * @param livraisonAvant livraison après laquelle on doit ajouter noeudALivrer
     * @return la tournée une fois qu'elle a été modifiée.
     */
    public boolean ajouterPointDeLivraison(Noeud noeudALivrer, int idClient, Livraison livraisonAvant) {
        // Recherche de la plage horaire de precedent
        PlageHoraire plageInsertion = null;
        int maxId = 0;
        for (PlageHoraire plage : this.getPlagesHoraire()) {
            for (Livraison livraison : plage.getLivraisons()) {
                if (livraison == livraisonAvant) {
                    plageInsertion = plage;
                }
                maxId = livraison.getId() > maxId ? livraison.getId() : maxId;
            }
        }

        this.zone.modifierNoeudEnLivraison(noeudALivrer.getId(), new Livraison(noeudALivrer, ++maxId, idClient, plageInsertion));
        Livraison aAjouterLivraison = (Livraison) this.zone.getNoeudId(noeudALivrer.getId());
        plageInsertion.getLivraisons().add(aAjouterLivraison);

        
        System.out.println("début de ajouterPointDeLivraison");
        boolean trouveChemin = false;
        int i;
        for (i=0; i<cheminsResultats.size();i++) {
            if (cheminsResultats.get(i).getOrigine().getIdNoeud() == livraisonAvant.getId()){
                trouveChemin = true;
                break;
            }
        }
        
        if(trouveChemin){
            //Création des variables nécessaires
            LivraisonGraphVertex lgvAvant = new LivraisonGraphVertex(livraisonAvant.getId(), false);
            LivraisonGraphVertex lgvMilieu = new LivraisonGraphVertex(noeudALivrer.getId(), false);
            LivraisonGraphVertex lgvApres = cheminsResultats.get(i).getDestination();
            //Calculerplus court chemin entre noeud Avant et milieu
            Chemin cheminAvant = Dijkstra.plusCourtChemin(zone, lgvAvant, lgvMilieu);
            //Calculer plus court chemin entre noeud Milieu et noeud Après
            Chemin cheminAprès = Dijkstra.plusCourtChemin(zone, lgvMilieu, lgvApres);
            //Supprimer le chemin
            System.out.println("Taille avant : " + cheminsResultats);
            cheminsResultats.remove(i);
            //Ajouter les deux chemins crées à la place de celui supprimé
            cheminsResultats.add(i, cheminAvant);
            cheminsResultats.add(i+1, cheminAprès);
            System.out.println("Taille après : " + cheminsResultats);
            this.graph.noeuds.add(lgvMilieu);
        
            
            
            
             /**
             * TODO (Gustave ??): recalculer les horaires des livraisons. 
             * GROS PAVé pour expliquer pourquoi j'ai pas réussi, et ce qu'il faut faire
             * En fait la j'ai modifié la tournée et maintenant qu'on a ajouté la livraison,
             * il faudrait savoir à quelle heure on l'a ajoutée, et s'il le faut, recalculer
             * les heures de toutes les livraisons et dire celles qu'on va devoir virer !
             */
        }
        else {
            //Si on a pas trouvé le chemin, on renvoie une tournee nulle
            //Il faut réagir à cette tournée nulle en disant qu'on a pas trouvé le chemin mais ne pas changer l'affichage.
            return false;
        }
                
        return true;
    }

    public boolean supprimerPointLivraison(Noeud noeudASup) {
        boolean trouve = false;
        int i;
        Chemin cheminAvant = null;
        Chemin cheminApres = null;
        for (i=0 ; i<cheminsResultats.size() ; i++) {
            int idNoeudASup = noeudASup.getId();
            if ((cheminsResultats.get(i).getDestination().getIdNoeud() == idNoeudASup) &&
                    (cheminsResultats.get(i+1).getOrigine().getIdNoeud() == idNoeudASup)){
                cheminAvant = cheminsResultats.get(i);
                cheminApres = cheminsResultats.get(i+1);
                trouve = true;
                break;
            }
  
//            if (cheminsResultats.get(i).getOrigine().getIdNoeud() == noeudASup.getId()){
//                cheminApres = cheminsResultats.get(i); //TODO Const par copie???
//                trouveChemins++;
//            }
//            else if (cheminsResultats.get(i).getDestination().getIdNoeud() == noeudASup.getId()){
//                cheminAvant = cheminsResultats.get(i);//TODO Const par copie???
//                trouveChemins++;
//            }
        }
        
        this.zone.modifierLivraisonEnNoeud(noeudASup.getId(), new Noeud(noeudASup.getId(), noeudASup.getXMetre(), noeudASup.getYMetre(),noeudASup.getSortants()));
        
        if (trouve)
        {
            //Création des variables nécessaires
            LivraisonGraphVertex lgvOrigine = cheminAvant.getOrigine();
            LivraisonGraphVertex lgvDestination = cheminApres.getDestination();
            LivraisonGraphVertex lgvMilieu = cheminAvant.getDestination();
            
            
            
            Chemin chemin = Dijkstra.plusCourtChemin(zone, lgvOrigine, lgvDestination);
            
            System.out.println("Avant cheminsResultats.size()" + cheminsResultats.size());
            cheminsResultats.remove(i);
            cheminsResultats.remove(i);
            
            cheminsResultats.add(i, chemin);
            graph.noeuds.remove(lgvMilieu);
            System.out.println("Après cheminsResultats.size()" + cheminsResultats.size());
            return true;
        }
        return false;
        
    }
}
