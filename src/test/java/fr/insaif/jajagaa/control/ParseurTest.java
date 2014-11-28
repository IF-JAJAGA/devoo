package fr.insaif.jajagaa.control;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Test de {@link fr.insaif.jajagaa.control.Parseur}
 * @author gustavemonod
 */
public class ParseurTest {
    /**
     * Nombre de livraisons dans le fichier de test
     */
	
	private static final int NB_LIVRAISON_1 = 8;
	private static final int NB_NOEUDS_10x10 = 100;
	//TODO Test plan10x10, Test plan20x20, Test xml mal formé, Test xml n'existe pas
	//XML mar formé: manque d'attributs, Noeuds pas fermés
    
	@Test
	/**
	 * 
	 */
	public void testLirePlan() {
		FileInputStream inputPlan = null;
		ZoneGeographique zoneGeo = null;
		List<Noeud> listNoeuds = null;
		try {
			inputPlan = new FileInputStream("./src/test/resources/plan10x10.xml");
			zoneGeo = Parseur.lirePlan(inputPlan);
			listNoeuds = zoneGeo.getNoeuds();
			assertEquals(NB_NOEUDS_10x10,listNoeuds.size());
			
			Noeud n1 = listNoeuds.get(0), n2 = listNoeuds.get(50), 
					n3 = listNoeuds.get(100);
			assertEquals(2,n1.getSortants().size());
			assertEquals(3,n2.getSortants().size());
			assertEquals(20, n3.getSortants().size());
			
			inputPlan.close();
			
		} catch(Exception e) {
			
		}
	}
	
	/**@Test
    
    public void testLireLivraison() throws Exception {
        
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
                for (int i = 1; i <= NB_LIVRAISON_1; ++i) {
                    //assertEquals(i, livraisons.get(i - 1).getPointLivraison().getId());
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
    }
    */

}
