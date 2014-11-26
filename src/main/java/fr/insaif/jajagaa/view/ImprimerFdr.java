package fr.insaif.jajagaa.view;

import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Tournee;


/**
 * AIDE________________________
 * la liste des livraisons qui appartiennent a cette tournée, l’heure de départ 
 * et d’arrivée pour chaque livraison, itinéraire entre chaque livraison et les 
 * informations sur la personne à livrer
 *
 */
public class ImprimerFdr {

	public boolean faireImpresion(Tournee tournee){
		List<Livraison> livraisons = Tournee.
		for(Livraison liv : livraisons) {
			Date heuDeb = liv.getHeureLivraison();
			Date heuFin = liv.getFinLivraison();
		}
		return false;
	}
}
