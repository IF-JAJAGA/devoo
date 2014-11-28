package fr.insaif.jajagaa;

import java.io.FileInputStream;
import java.util.List;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Point d'entrée de l'application
 * @author gustavemonod
 */
public class App {
    public static void main(String ... args) throws Exception {
        Thread.sleep(6000);
        FileInputStream zoneInputStream = null,livInputStream = null;
        try {
            zoneInputStream = new FileInputStream("./src/test/resources/plan10x10.xml");
//        	livInputStream = new FileInputStream("./src/test/resources/livraison10x10-1.xml");
        	ZoneGeographique zoneGeo = Parseur.lirePlan(zoneInputStream);
        	for(Noeud no : zoneGeo.getNoeuds()) {
        		List<Troncon> lt = no.getSortants();
        		for(Troncon tronco : lt) {
        			System.out.println(tronco.getIdOrigine()+": "+tronco.getLongueurMetre()+"m.   "+tronco.getVitesse()+"m/s");
        		}
        		
        	}
//          Parseur.lireLivraison(livInputStream,zoneGeo);
        } finally {
//            if (livInputStream != null) {
//                livInputStream.close();
//            }
            if (zoneInputStream != null) {
            	zoneInputStream.close();
            }
        }
    }
}
