package fr.insaif.jajagaa.control;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.List;

import org.jdom.input.JDOMParseException;
import org.junit.Test;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Test de {@link fr.insaif.jajagaa.control.Parseur}
 */
public class ParseurTest {
    /**
     * Nombre de livraisons dans le fichier de test
     */
	
	private static final int NB_LIVRAISON_1 = 8;
	//TODO Test plan10x10, Test plan20x20, Test xml mal formé, Test xml n'existe pas
	//XML mar formé: manque d'attributs, Noeuds pas fermés
	//DONE???
    
	@Test
	public void testLirePlansCorrects(){
		testLirePlanCorrect("./src/test/resources/plan10x10.xml",100,2,3,2);
		testLirePlanCorrect("./src/test/resources/plan20x20.xml",400,2,3,1);
	}
	
	/**
	 * 
	 */
	public void testLirePlanCorrect(String path, int nombre, int i1, int i2, int i3) {
		FileInputStream inputPlan = null;
		ZoneGeographique zoneGeo = null;
		List<Noeud> listNoeuds = null;
		try {
			inputPlan = new FileInputStream(path);
			zoneGeo = Parseur.lirePlan(path);
			listNoeuds = zoneGeo.getNoeuds();
			assertEquals(nombre,listNoeuds.size());
			
			Noeud n1 = listNoeuds.get(0), n2 = listNoeuds.get(nombre/2), 
				n3 = listNoeuds.get(nombre);
			assertEquals(i1,n1.getSortants().size());
			assertEquals(i2,n2.getSortants().size());
			assertEquals(i3, n3.getSortants().size());
			
			inputPlan.close();
			
		} catch(Exception e) {
			
		}
	}
	
	@Test
	public void testLireLivraisonsCorrectes(){
		testLireLivraisonCorrecte("./src/test/resources/livraison10x10-1.xml",
				"./src/test/resources/plan10x10.xml",8);
		testLireLivraisonCorrecte("./src/test/resources/livraison10x10-2.xml",
				"./src/test/resources/plan10x10.xml",8);
		testLireLivraisonCorrecte("./src/test/resources/livraison10x10-3.xml",
				"./src/test/resources/plan10x10.xml",8);
		testLireLivraisonCorrecte("./src/test/resources/livraison20x20-1.xml",
				"./src/test/resources/plan20x20.xml",12);
		testLireLivraisonCorrecte("./src/test/resources/livraison20x20-2.xml",
				"./src/test/resources/plan20x20.xml",12);
	}
	
	/**
	 * 
	 */
	public void testLireLivraisonCorrecte(String pathLiv, String pathPlan, int nombre) {
		FileInputStream inputPlan = null;
		FileInputStream inputLivraison = null;
		ZoneGeographique zoneGeo = null;
		List<PlageHoraire> listPlages = null;
		try {
			inputPlan = new FileInputStream(pathPlan);
			inputLivraison = new FileInputStream(pathLiv);
			zoneGeo = Parseur.lirePlan(pathPlan);
			listPlages = Parseur.lireLivraison(inputLivraison, zoneGeo);
			
			int cont = 0;
			for (PlageHoraire plage : listPlages) {
				cont += plage.getLivraisons().size();
			}
			
			assertEquals(nombre, cont);
			
			inputPlan.close();
			inputLivraison.close();
		} catch(Exception e) {
			
		}
	}
	
	@Test (expected = JDOMParseException.class)
	/**
	 * 
	 */
	public void testErreurXMLMalForme(){
		try {
			FileInputStream inputPlan = new FileInputStream("./src/test/resources/plan10x10-E1.xml");
			Parseur.lirePlan("./src/test/resources/plan10x10-E1.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void testErreurAttributInvalide(){
		try {
			FileInputStream inputPlan = new FileInputStream("./src/test/resources/plan10x10-E2.xml");
			//TODO Exception
			inputPlan.close();
		} catch (Exception e) {
			//Exception ici?
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void testErreurManqueAttribut(){
		try {
			FileInputStream inputPlan = new FileInputStream("./src/test/resources/plan10x10-E3.xml");
			//TODO Exception
			inputPlan.close();
		} catch (Exception e) {
			//Exception ici?
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void testErreurAttributVide(){
		try {
			FileInputStream inputPlan = new FileInputStream("./src/test/resources/plan10x10-E4.xml");
			//TODO Exception
			inputPlan.close();
		} catch (Exception e) {
			//Exception ici?
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void testErreurXMLNonExistant(){
		try {
			FileInputStream inputPlan = new FileInputStream("./src/test/resources/nexistepas.xml");
			//TODO Exception
			inputPlan.close();
		} catch (Exception e) {
			//Exception ici?
		}
	}
}
