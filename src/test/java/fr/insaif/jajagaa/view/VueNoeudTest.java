package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.Noeud;
import java.awt.Color;
import java.awt.Point;
import static java.lang.Math.sqrt;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jeje
 */
public class VueNoeudTest {
    private VueNoeud vN1;
    private VueNoeud vN2;
    private final int rayon = VueNoeud.DIAMETRE/2;
    
    public VueNoeudTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vN1 = new VueNoeud(new Noeud(1, 0, 0), Color.GREEN);
    	vN2 = new VueNoeud(new Noeud(2, 1000, 700), Color.ORANGE);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testClicSurCentreNoeud(){
        assertTrue( vN1.getNoeudClique(new Point(vN1.getVueX(),vN1.getVueY())) == vN1);
    }
    
    @Test
    public void testClicSurExtremiteNoeud(){
        int diagonale = (int) (rayon/sqrt(2));
        Point p = new Point(vN1.getVueX()+diagonale, vN1.getVueY()+diagonale);
        assertEquals(vN1,vN1.getNoeudClique(p));
    }
}
