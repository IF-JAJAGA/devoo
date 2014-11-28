package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;
import org.junit.Test;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test de {@link fr.insaif.jajagaa.control.Parseur}
 * @author gustavemonod
 */
public class ParseurTest {
    /**
     * Nombre de livraisons dans le fichier de test
     */
    protected static final int NB_LIVRAISON_1 = 8;

    @Test
    public void testLireLivraison() throws Exception {
        /*
        FileInputStream inputStream = null;
        FileInputStream inputStreamPlan = null;
        ZoneGeographique zone = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");
        try {
            inputStream = new FileInputStream("./src/test/resources/livraison10x10-1.xml");
            inputStreamPlan = new FileInputStream("./src/test/resources/plan10x10.xml");
            zone = Parseur.lirePlan(inputStreamPlan);
            List<PlageHoraire> plages = Parseur.lireLivraison(inputStream,zone);
            for (PlageHoraire plage : plages) {
                List<Livraison> livraisons = plage.getLivraisons();
                assertEquals(NB_LIVRAISON_1, livraisons.size());
                assertEquals(simpleDateFormat.parse("8:0:0"), livraisons.get(0).getHeureLivraison());
                //assertEquals(simpleDateFormat.parse("12:0:0"), livraisons.get(0).getHeureLivraison());
                for (int i = 1; i <= NB_LIVRAISON_1; ++i) {
                    assertEquals(i, livraisons.get(i - 1).getPointLivraison().getId());
                }
            }
            
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (inputStreamPlan != null) {
                inputStreamPlan.close();
            }
        }
        */
    }
}
