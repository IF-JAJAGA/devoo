package fr.insaif.jajagaa;

import java.util.List;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Point d'entrée de l'application
 * @author gustavemonod
 */
public class App {
	public static void main(String ... args) throws Exception {
		Thread.sleep(6000); 
		ZoneGeographique zoneGeo = Parseur.lirePlan("/home/jeje/4If/devoo/src/main/resources/plan10x10.xml");
//		for(Noeud no : zoneGeo.getNoeuds()) {
//			List<Troncon> lt = no.getSortants();
//			for(Troncon tronco : lt) {
//				System.out.println("Noeud " + tronco.getIdOrigine()+": Longeur "+tronco.getLongueurMetre()+" m.   Vitesse: "+tronco.getVitesse()+" m/s");
//			}
//
//		}
		List<PlageHoraire> listePlages = Parseur.lireLivraison("/home/jeje/4If/devoo/src/main/resources/livraison10x10-2.xml",zoneGeo);
		for(PlageHoraire plage : listePlages) {
			System.out.println("Plage" +plage.getHeureDebut() +" - "+ plage.getHeureFin());
			List<Livraison> listLiv = plage.getLivraisons();
//			for(Livraison liv : listLiv) {
//				System.out.println("Livraison: "+ liv.getId() +". Livrer à "+liv.getHeureLivraison()+
//						"heures. Finir à " + liv.getHeureFin() + "heures.");
//			}
			
		}
                
                Tournee tournee = new Tournee(zoneGeo);
                tournee.setPlagesHoraire(listePlages);
                System.out.println("Debut calcul");
                tournee.solve(10000);
                System.out.println("Fin calcul");
	}
}
