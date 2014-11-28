package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Classe testant {@link fr.insaif.jajagaa.model.tsp.TSP#solve(int, int)} avec une ZoneGeographique
 * @author gustavemonod
 */
public class TSPTest {
    private LivraisonGraph graph;
    public static final int MAX_SEC = 2;

    @Before
    public void setUp() throws Exception{
        List<Noeud> noeuds = new ArrayList<Noeud>();

        // Exemple de noeuds et de livraisons (s'il y a une livraison, un noeud est une livraison)
        noeuds.add(new Noeud(0, 63, 100));
        noeuds.add(new Noeud(1, 88, 171));
        noeuds.add(new Noeud(2, 103, 248));
        noeuds.add(new Noeud(3, 65, 310));
        noeuds.add(new Noeud(4, 77, 350));
        noeuds.add(new Noeud(5, 83, 403));
        noeuds.add(new Noeud(6, 87, 483));
        noeuds.add(new Noeud(7, 45, 579));
        noeuds.add(new Noeud(8, 43, 619));
        noeuds.add(new Noeud(9, 59, 699));
        noeuds.add(new Noeud(10, 154, 95));

        // Exemple de tronçons
        noeuds.get(0).addSortant(noeuds.get(1), 602.1f, 3.9f, "v0");
        noeuds.get(0).addSortant(noeuds.get(10), 729f, 4.2f, "h0");
        noeuds.get(1).addSortant(noeuds.get(0), 602.1f, 4.1f, "v0");
        noeuds.get(1).addSortant(noeuds.get(2), 627.5f, 3.8f, "v0");
        noeuds.get(2).addSortant(noeuds.get(3), 323.5f, 3.43f, "v0");
        noeuds.get(2).addSortant(noeuds.get(1), 627.5f, 4.6f, "v0");
        noeuds.get(2).addSortant(noeuds.get(3), 581.7f, 4.1f, "v0");
        noeuds.get(3).addSortant(noeuds.get(2), 581.7f, 4.0f, "v0");
        noeuds.get(3).addSortant(noeuds.get(4), 334.0f, 4.5f, "v0");
        noeuds.get(4).addSortant(noeuds.get(3), 334.0f, 4.2f, "v0");
        noeuds.get(4).addSortant(noeuds.get(5), 426.7f, 4.1f, "v0");
        noeuds.get(5).addSortant(noeuds.get(4), 426.7f, 4.1f, "v0");
        noeuds.get(5).addSortant(noeuds.get(6), 640.7f, 4.0f, "v0");
        noeuds.get(6).addSortant(noeuds.get(5), 640.7f, 4.7f, "v0");
        noeuds.get(6).addSortant(noeuds.get(7), 838.2f, 4.2f, "v0");
        noeuds.get(7).addSortant(noeuds.get(6), 838.2f, 4.3f, "v0");
        noeuds.get(7).addSortant(noeuds.get(8), 320.3f, 4.1f, "v0");
        noeuds.get(8).addSortant(noeuds.get(7), 320.3f, 4.7f, "v0");
        noeuds.get(8).addSortant(noeuds.get(9), 652.6f, 4.5f, "v0");
        noeuds.get(9).addSortant(noeuds.get(8), 652.6f, 4.6f, "v0");
        noeuds.get(9).addSortant(noeuds.get(0), 652.6f, 4.6f, "v0");
        noeuds.get(10).addSortant(noeuds.get(0), 729f, 4.5f, "h0");

        ZoneGeographique zone = new ZoneGeographique(noeuds);

        List<PlageHoraire> plages = new ArrayList<PlageHoraire>();
        List<Livraison> livraisons = new ArrayList<Livraison>();
        plages.add(new PlageHoraire("8:0:0", "12:0:0"));

        livraisons.add(new Livraison(noeuds.get(3), 1, 611));
        livraisons.add(new Livraison(noeuds.get(4), 2, 276));
        livraisons.add(new Livraison(noeuds.get(8), 3, 300));
        livraisons.add(new Livraison(noeuds.get(1), 4, 591));
        livraisons.add(new Livraison(noeuds.get(7), 5, 923));
        livraisons.add(new Livraison(noeuds.get(5), 6, 68));
        livraisons.add(new Livraison(noeuds.get(6), 7, 709));

        plages.get(0).setLivraisons(livraisons);

        zone.modifierNoeudEnLivraison(3, livraisons.get(0));
        zone.modifierNoeudEnLivraison(4, livraisons.get(1));
        zone.modifierNoeudEnLivraison(8, livraisons.get(2));
        zone.modifierNoeudEnLivraison(1, livraisons.get(3));
        zone.modifierNoeudEnLivraison(7, livraisons.get(4));
        zone.modifierNoeudEnLivraison(5, livraisons.get(5));
        zone.modifierNoeudEnLivraison(6, livraisons.get(6));

        Tournee tournee = new Tournee(zone);
        tournee.setPlagesHoraire(plages);

        this.graph = tournee.getGraph();
    }

    @Test
    public void testSolve() throws Exception {
        int nbVertices = this.graph.getNbVertices();
        int maxCost = this.graph.getMaxArcCost();

        TSP tsp = new TSP(this.graph);
        tsp.solve(MAX_SEC * 1000, nbVertices * maxCost + 1);
        if (tsp.getSolutionState() == SolutionState.SOLUTION_FOUND
                || tsp.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND) {
            int[] next = tsp.getNext();
            System.out.println(Arrays.toString(next));
        }
        else {
            fail("No solution found after "+MAX_SEC+" seconds...");
        }
    }
}
