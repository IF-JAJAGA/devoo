package fr.insaif.jajagaa.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test de {@link fr.insaif.jajagaa.model.ZoneGeographique}.
 * @author gustavemonod
 */
public class ZoneGeographiqueTest {
    private Noeud entrepot;
    private List<Noeud> noeuds;
    private ZoneGeographique zone;
    private int min;
    private int max;
    private int[][] costs;
    private int[][] successeurs;

    public static ZoneGeographique exampleZone() {
        ZoneGeographiqueTest t = new ZoneGeographiqueTest();
        t.makeZone();
        return t.zone;
    }

    private void makeZone() {
        this.noeuds = new ArrayList<Noeud>();

        // Exemple de vueNoeuds (et d'entrepôt)
        noeuds.add(new Noeud(0, 541, 12));
        noeuds.add(new Noeud(1, 321, 11));
        this.entrepot = new Noeud(2, 42, 144);
        noeuds.add(this.entrepot);
        noeuds.add(new Noeud(3, 42, 145));

        // Exemple de tronçons
        noeuds.get(0).addSortant(noeuds.get(1), 349f, 3.32f, "a");
        noeuds.get(0).addSortant(noeuds.get(3), 123.5f, 4.43f, "b"); // min
        noeuds.get(1).addSortant(noeuds.get(2), 312.4f, 6.831f, "c"); // max
        noeuds.get(2).addSortant(noeuds.get(3), 323.5f, 3.43f, "d");
        noeuds.get(3).addSortant(noeuds.get(0), 432.4f, 1.43f, "e");

        this.zone = new ZoneGeographique(noeuds);
    }

    @Before
    public void setUp() throws Exception {
        this.makeZone();

        List<Troncon> troncons = new ArrayList<Troncon>();
        troncons.add(noeuds.get(0).getSortants().get(0));
        troncons.add(noeuds.get(0).getSortants().get(1));
        troncons.add(noeuds.get(1).getSortants().get(0));
        troncons.add(noeuds.get(2).getSortants().get(0));
        troncons.add(noeuds.get(3).getSortants().get(0));

        // Min et max attendus
        this.min = Math.round(troncons.get(1).getCost());
        this.max = Math.round(troncons.get(4).getCost());

        // Successeurs attendus
        this.successeurs = new int[4][];
        this.successeurs[0] = new int[] {1, 3};
        this.successeurs[1] = new int[] {2};
        this.successeurs[2] = new int[] {3};
        this.successeurs[3] = new int[] {0};

        // Matrice des coûts attendue
        int PAS_TRONCON = this.max + 1;
        this.costs = new int[][]{
                {PAS_TRONCON, troncons.get(0).getCostInt(), PAS_TRONCON,                  troncons.get(1).getCostInt()},
                {PAS_TRONCON,                  PAS_TRONCON, troncons.get(2).getCostInt(), PAS_TRONCON},
                {PAS_TRONCON,                  PAS_TRONCON, PAS_TRONCON,                  troncons.get(3).getCostInt()},
                {troncons.get(4).getCostInt(), PAS_TRONCON, PAS_TRONCON,                  PAS_TRONCON}
        };
    }

    @Test
    public void testCourtChemin() {
//        Chemin c = Dijkstra.plusCourtChemin(this.zone, new LivraisonGraphVertex(this.noeuds.get(0)), new LivraisonGraphVertex(this.noeuds.get(2)));
//        List<Troncon> troncons = c.getTroncons();
//        assertEquals(2, troncons.size());
//        assertEquals(this.noeuds.get(0).getSortants().get(0), troncons.get(0));
//        assertEquals(this.noeuds.get(1).getSortants().get(0), troncons.get(1));
    }

    @Test
    public void testConstruction() {
        //TODO
        /*
        assertEquals(this.noeuds.size(), this.zone.getNbVertices());

        this.zone.setEntrepot(0); // 2 = Indice de l'entrepôt dans la liste
        assertNotEquals(this.entrepot, zone.getEntrepot());

        this.zone.setEntrepot(this.entrepot); // Mise à jour par la référence
        assertEquals(this.entrepot, zone.getEntrepot());

        assertEquals(this.min, this.zone.getMinArcCost());
        assertEquals(this.max, this.zone.getMaxArcCost());

        for (int i = 0; i < this.noeuds.size(); ++i) {
            assertArrayEquals(this.successeurs[i], this.zone.getSucc(i));
        }

        int actual[][] = this.zone.getCost();
        for (int i = 0; i < this.costs.length; ++i) {
            assertArrayEquals(this.costs[i], actual[i]);
        }
        */
    }
}
