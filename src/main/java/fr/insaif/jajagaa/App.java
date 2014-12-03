package fr.insaif.jajagaa;

import java.util.List;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.*;

/**
 * Point d'entrée de l'application
 * @author gustavemonod
 */
public class App {
	public static void main(String ... args) throws Exception {
		ZoneGeographique zoneGeo = Parseur.lirePlan("./src/main/resources/plan10x10.xml");

		List<PlageHoraire> listePlages = Parseur.lireLivraison("./src/main/resources/livraison10x10-2.xml",zoneGeo);

        Tournee tournee = new Tournee(zoneGeo);
        tournee.setPlagesHoraire(listePlages);

        System.out.println("Debut calcul");
        tournee.solve(10000);
        System.out.println("Fin calcul");
	}
}
