package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;
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
        FileInputStream inputStream = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");
        try {
            inputStream = new FileInputStream("./src/test/resources/livraison10x10-1.xml");
            List<Livraison> livraisons = Parseur.lireLivraison(inputStream);
            assertEquals(NB_LIVRAISON_1, livraisons.size());
            assertEquals(simpleDateFormat.parse("8:0:0"), livraisons.get(0).getHoraireDeroulement().getHeureDebut());
            assertEquals(simpleDateFormat.parse("12:0:0"), livraisons.get(0).getHoraireDeroulement().getHeureFin());
            for (int i = 1; i <= NB_LIVRAISON_1; ++i) {
                assertEquals(""+i, livraisons.get(i - 1).getId());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
