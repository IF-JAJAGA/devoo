package fr.insaif.jajagaa.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

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
		ZoneGeographique zoneGeo = null;
		List<PlageHoraire> listPlages = null;
		try {
			zoneGeo = Parseur.lirePlan(pathPlan);
			listPlages = Parseur.lireLivraison(pathLiv, zoneGeo);
			
			int cont = 0;
			for (PlageHoraire plage : listPlages) {
				cont += plage.getLivraisons().size();
			}
			
			assertEquals(nombre, cont);
			
		} catch(Exception e) {
			
		}
	}
	
	
	@Test
	/**
	 * 
	 */
	public void testPlages() {
		try{
			ZoneGeographique zoneGeo = Parseur.lirePlan("./src/test/resources/plan10x10.xml");
			//livraison10x10-1
			List<PlageHoraire> listePlages = Parseur.lireLivraison("./src/test/resources/livraison10x10-1.xml",zoneGeo);
			assertEquals("8:0:0",getHeure(listePlages.get(0).getHeureDebut())); 
			assertEquals("12:0:0",getHeure(listePlages.get(0).getHeureFin()));

			//livraison10x10-2
			listePlages = Parseur.lireLivraison("./src/test/resources/livraison10x10-2.xml", zoneGeo);
			assertEquals("8:0:0",getHeure(listePlages.get(0).getHeureDebut())); 
			assertEquals("9:30:0",getHeure(listePlages.get(0).getHeureFin()));
			assertEquals("9:30:0",getHeure(listePlages.get(1).getHeureDebut())); 
			assertEquals("11:0:0",getHeure(listePlages.get(1).getHeureFin()));
			assertEquals("11:0:0",getHeure(listePlages.get(2).getHeureDebut())); 
			assertEquals("12:30:0",getHeure(listePlages.get(2).getHeureFin()));

			//livraison10x10-3 mêmes plages qu'à livraison10x10-2

			ZoneGeographique zoneGeo2 = Parseur.lirePlan("./src/test/resources/plan20x20.xml");
			//livraison20x20-1
			listePlages = Parseur.lireLivraison("./src/test/resources/livraison20x20-1.xml", zoneGeo2);
			assertEquals("8:30:0",getHeure(listePlages.get(0).getHeureDebut())); 
			assertEquals("10:0:0",getHeure(listePlages.get(0).getHeureFin()));
			assertEquals("10:0:0",getHeure(listePlages.get(1).getHeureDebut())); 
			assertEquals("11:30:0",getHeure(listePlages.get(1).getHeureFin()));
			assertEquals("14:0:0",getHeure(listePlages.get(2).getHeureDebut()));
			assertEquals("16:0:0",getHeure(listePlages.get(2).getHeureFin()));

			//livraison20x20-2 mêmes plages qu'à livraison20x20-1
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public String getHeure(Date date) {
		return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}
	

	@Test
	/**
	 * 
	 */
	public void testErreurXMLMalForme(){
		try {
			Parseur.lirePlan("./src/test/resources/plan10x10-E1.xml");
		} catch (ParseurException pe) {
			assertEquals("Ficher XML mal formé: mauvaise syntaxe XML",pe.getMessage());
			assertSame(ParseurException.class, pe.getClass());
			////TODO sont necessaires les deux asserts? Le premier contient le deuxieme?
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test 
	/**
	 * 
	 */
	public void testErreurAttributInvalide(){
		try {
			Parseur.lirePlan("./src/test/resources/plan10x10-E2.xml");
		} catch (ParseurException pe) {
			assertEquals("Ficher XML mal formé: mauvaise syntaxe XML",pe.getMessage());
			assertSame(ParseurException.class, pe.getClass());
			////TODO sont necessaires les deux asserts? Le premier contient le deuxieme?
		}
	}

	@Test 
	/**
	 * 
	 */
	public void testErreurManqueAttribut(){
		try {
			Parseur.lirePlan("./src/test/resources/plan10x10-E3.xml");
		} catch (ParseurException pe) {
			assertEquals("Ficher XML mal formé: mauvaise syntaxe XML",pe.getMessage());
			assertSame(ParseurException.class, pe.getClass());
			//TODO sont necessaires les deux asserts? Le premier contient le deuxieme?
		}
	}

	@Test 
	/**
	 * 
	 */
	public void testErreurAttributVide(){
		try {
			Parseur.lirePlan("./src/test/resources/plan10x10-E4.xml");
		} catch (ParseurException pe) {
			assertEquals("Ficher XML mal formé: mauvaise syntaxe XML",pe.getMessage());
			assertSame(ParseurException.class, pe.getClass());
			//TODO sont necessaires les deux asserts? Le premier contient le deuxieme?
		}
	}

	@Test 
	/**
	 * 
	 */
	public void testErreurXMLNonExistant(){
		try {
			Parseur.lirePlan("./src/test/resources/nexistepas.xml");
		} catch (ParseurException pe){
			assertEquals("Fichier inexistant",pe.getMessage());
		}
	}
}
