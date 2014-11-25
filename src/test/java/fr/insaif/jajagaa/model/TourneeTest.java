package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.control.Parseur;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;

/**
 * Test de {@link fr.insaif.jajagaa.model.Tournee}
 * @author gustavemonod
 */
public class TourneeTest {
    protected Tournee tournee;
    protected ZoneGeographique zone;

    @Before
    public void setUp() throws Exception {
        this.tournee = new Tournee();
        tournee.livraisons = Parseur.lireLivraison(new FileInputStream("./src/test/resources/livraison10x10-1.xml"));
        tournee.zone = ZoneGeographiqueTest.exampleZone();
    }

    @Test
    public void testConvert() {

    }
}
