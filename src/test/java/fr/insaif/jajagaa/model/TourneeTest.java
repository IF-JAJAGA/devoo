package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test de {@link fr.insaif.jajagaa.model.Tournee}
 * @author gustavemonod
 */
public class TourneeTest {
    protected Tournee tournee;
    protected ZoneGeographique zoneGeo;

    public static final int MAX_TIME_SEC = 10;

    @Before
    public void setUp() throws Exception {
        this.zoneGeo = Parseur.lirePlan("./src/main/resources/plan10x10.xml");
        List<PlageHoraire> listPlages = Parseur.lireLivraison("./src/main/resources/livraison10x10-2.xml", this.zoneGeo);
        this.tournee = new Tournee(this.zoneGeo);
        this.tournee.setPlagesHoraire(listPlages);
    }

    @Test
    public void testSolve() throws ParseurException {
        System.out.println("Debut calcul");
        SolutionState solutionState = tournee.solve(MAX_TIME_SEC * 1000);
        System.out.println("Fin calcul");

        if (solutionState == SolutionState.OPTIMAL_SOLUTION_FOUND || solutionState == SolutionState.SOLUTION_FOUND) {
            this.verifierHoraires();
        } else {
            fail("Impossible de résoudre le problème en moins de "+MAX_TIME_SEC+" secondes.");
        }
    }

    protected void verifierHoraires() {
        int plageIndex = 0;
        for (Chemin chemin : this.tournee.getCheminsResultats()) {
            Livraison currentLivraison = ((Livraison) zoneGeo.getNoeudById(chemin.getDestination().getIdNoeud()));
            PlageHoraire currentPlage = null;

            // Recherche de la plage horaire d'une livraison
            for (PlageHoraire plage : this.tournee.getPlagesHoraire()) {
                if (plage.getLivraisons().contains(currentLivraison)) {
                    currentPlage = plage;
                    break;
                }
            }

            assertTrue("L'horaire de livraison doit être supérieur ou égal à celui de début de la plage actuelle",
                    currentLivraison.getHeureLivraison().compareTo(currentPlage.getHeureDebut()) >= 0);

            assertTrue("L'horaire de livraison doit être inférieur ou égal à celui de fin de la plage actuelle",
                    currentLivraison.getHeureLivraison().compareTo(currentPlage.getHeureFin()) <= 0);
        }
    }

    @Test
    public void testAjout() {
        Noeud noeudALivrer = this.zoneGeo.getNoeudId(20); // le noeud 20 n'est pas une livraison
        Livraison precedent = this.tournee.getPlagesHoraire().get(1).getLivraisons().get(0);

        this.tournee.ajouterPointDeLivraison(noeudALivrer, 1, precedent);
        // TODO ajouter ici des tests
     }
}
