package fr.insaif.jajagaa.model;

<<<<<<< HEAD:src/test/java/fr/insaif/jajagaa/model/TspTest.java
=======
import fr.insaif.jajagaa.model.LivraisonGraph;
import fr.insaif.jajagaa.model.Noeud;
>>>>>>> 20fec5bd88e8b712e17b6d96d9419098bc276eda:src/test/java/fr/insaif/jajagaa/model/TSPTest.java
import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;
import org.junit.Before;
import org.junit.Test;

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
        noeuds.add(new Noeud(0, 541, 12));
        noeuds.add(new Noeud(1, 321, 11));
        Noeud entrepot = new Noeud(2, 42, 144);
        noeuds.add(entrepot);
        noeuds.add(new Noeud(3, 42, 145));

        // Exemple de tronçons
        noeuds.get(0).addSortant(noeuds.get(1), 349f, 3.32f);
        noeuds.get(0).addSortant(noeuds.get(3), 123.5f, 4.43f); // min
        noeuds.get(1).addSortant(noeuds.get(2), 312.4f, 6.831f); // max
        noeuds.get(2).addSortant(noeuds.get(3), 323.5f, 3.43f);
        noeuds.get(3).addSortant(noeuds.get(0), 432.4f, 1.43f);

        this.graph = new LivraisonGraph(noeuds);
    }

    @Test
    public void testSolve() throws Exception {
<<<<<<< HEAD:src/test/java/fr/insaif/jajagaa/model/TspTest.java
        int nbVertices = this.zone.getNbVertices();
        int maxCost = this.zone.getMaxArcCost();
=======
        int nbVertices = this.graph.getNbVertices();
        int maxCost = this.graph.getMaxArcCost();
        int totalCost = 0;
>>>>>>> 20fec5bd88e8b712e17b6d96d9419098bc276eda:src/test/java/fr/insaif/jajagaa/model/TSPTest.java

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
