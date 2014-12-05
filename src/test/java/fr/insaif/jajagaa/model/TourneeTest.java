package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test de {@link fr.insaif.jajagaa.model.Tournee}
 * @author gustavemonod
 */
public class TourneeTest {
    protected Tournee tournee, tournee2;
    protected ZoneGeographique zoneGeo;

    public static final int MAX_TIME_SEC = 100;

    @Before
    public void setUp() throws Exception {
        this.zoneGeo = Parseur.lirePlan("./src/main/resources/plan10x10.xml");
        List<PlageHoraire> listPlages = Parseur.lireLivraison("./src/main/resources/livraison10x10-2.xml", this.zoneGeo);
        this.tournee = new Tournee(this.zoneGeo);
        this.tournee.setPlagesHoraire(listPlages);

    }

    @Test
    public void testSolve() throws ParseurException {
        SolutionState solutionState = null;
        try{
        	solutionState = tournee.solve(MAX_TIME_SEC * 1000);
        } catch (Exception e) {
        	e.printStackTrace();
        	fail();
        }

        if (solutionState == SolutionState.OPTIMAL_SOLUTION_FOUND || solutionState == SolutionState.SOLUTION_FOUND) {
            this.verifierHoraires();
        } else {
            fail("Impossible de résoudre le problème en moins de "+MAX_TIME_SEC+" secondes.");
        }
    }

    protected void verifierHoraires() {
        int plageIndex = 0;
        for (Chemin chemin : this.tournee.getCheminsResultats()) {
            Livraison currentLivraison;
            try {
                currentLivraison = ((Livraison) zoneGeo.getNoeudById(chemin.getDestination().getIdNoeud()));
            }
            catch(ClassCastException e) {
                continue;
            }
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

        try {
            this.tournee.ajouterPointDeLivraison(noeudALivrer, 1, precedent);
        } catch (HorsPlageException ex) {
            Logger.getLogger(TourneeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    @Test
    public void testTropDeLivraisons() {
    	try {
          List<PlageHoraire> listPlages2 = Parseur.lireLivraison("./src/main/resources/livraison10x10-5.xml", zoneGeo);
          this.tournee2 = new Tournee(this.zoneGeo);
          this.tournee2.setPlagesHoraire(listPlages2);
    		tournee2.solve(200000);
    		fail();
    	} catch (HorsPlageException hpe) {
    		
    	} catch (Exception e) {
    		fail();
    	}
    }
}
