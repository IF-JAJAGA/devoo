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
    public static final int MAX_SEC = 200;

    @Before
    public void setUp() throws Exception{
        List<Noeud> noeuds = new ArrayList<Noeud>();

        // Exemple de vueNoeuds (et d'entrepôt)
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
        //TODO mettre le bon nom de rue
        noeuds.get(0).addSortant(noeuds.get(1), 602.1f, 3.9f, "v0");
        noeuds.get(0).addSortant(noeuds.get(10), 729f, 4.2f, "h0");
        noeuds.get(1).addSortant(noeuds.get(0), 602.1f, 4.1f, "v0");
        noeuds.get(1).addSortant(noeuds.get(2), 627.5f, 3.8f, "v0");
        noeuds.get(2).addSortant(noeuds.get(3), 323.5f, 3.43f, "v0");
        noeuds.get(2).addSortant(noeuds.get(1), 627.500000f, 4.600000f, "v0");
        noeuds.get(2).addSortant(noeuds.get(3), 581.700000f, 4.100000f, "v0");
        noeuds.get(3).addSortant(noeuds.get(2), 581.700000f, 4.000000f, "v0");
        noeuds.get(3).addSortant(noeuds.get(4), 334.000000f, 4.500000f, "v0");
        noeuds.get(4).addSortant(noeuds.get(3), 334.000000f, 4.200000f, "v0");
        noeuds.get(4).addSortant(noeuds.get(5), 426.700000f, 4.100000f, "v0");
        noeuds.get(5).addSortant(noeuds.get(4), 426.700000f, 4.100000f, "v0");
        noeuds.get(5).addSortant(noeuds.get(6), 640.700000f, 4.000000f, "v0");
        noeuds.get(6).addSortant(noeuds.get(5), 640.700000f, 4.700000f, "v0");
        noeuds.get(6).addSortant(noeuds.get(7), 838.200000f, 4.200000f, "v0");
        noeuds.get(7).addSortant(noeuds.get(6), 838.200000f, 4.300000f, "v0");
        noeuds.get(7).addSortant(noeuds.get(8), 320.300000f, 4.100000f, "v0");
        noeuds.get(8).addSortant(noeuds.get(7), 320.300000f, 4.700000f, "v0");
        noeuds.get(8).addSortant(noeuds.get(9), 652.600000f, 4.500000f, "v0");
        noeuds.get(9).addSortant(noeuds.get(8), 652.600000f, 4.600000f, "v0");
        noeuds.get(10).addSortant(noeuds.get(0), 729.000000f, 4.500000f, "h0");

        /*
        ZoneGeographique zone = Parseur.lirePlan(new FileInputStream("./src/test/resources/plan-test.xml"));
        List<PlageHoraire> plages = Parseur.lireLivraison(new FileInputStream("./src/test/resources/livraison-test.xml"), zone);
        */
        ZoneGeographique zone = new ZoneGeographique(noeuds);
        List<PlageHoraire> plages = new ArrayList<PlageHoraire>();

        plages.add(new PlageHoraire("8:0:0", "12:0:0"));

        Tournee tournee = new Tournee(zone);
        tournee.setPlagesHoraire(plages);
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
