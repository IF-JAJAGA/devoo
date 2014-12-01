package fr.insaif.jajagaa.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.ZoneGeographique;


/**
 * AIDE________________________
 * la liste des livraisons qui appartiennent a cette tourn�e, l�heure de d�part 
 * et d�arriv�e pour chaque livraison, itin�raire entre chaque livraison et les 
 * informations sur la personne � livrer
 *
 */
public class ImprimerFdr {
	
	/**
	 * 
	 * @param zoneGeo
	 * @param plage
	 * @param id
	 */
	public static void ecrireFichierTri(Tournee tournee){
		String id = Calendar.getInstance().toString();
		String NomFichier = "./src/test/resources/feuilleDeRoute_"+id+".txt";
		try{
			PrintWriter fichier  = new PrintWriter(new BufferedWriter(new FileWriter(NomFichier)));
			List<Chemin> chemins = tournee.getCheminsResultats();
//			List<Livraison> livraisons = null;
//			List<Chemin> chemins = tournee.getCheminsResultats();
//			for(PlageHoraire plage : plages) {
//				livraisons = plage.getLivraisons();
//				for(Livraison livraison : livraisons) {
//					fichier.write("Livraison "+livraison.getId()+"\n");
//					fichier.write("\tHeure début: "+getHeure(livraison.getHeureLivraison())+"\n");
//					fichier.write("\tHeure fin: "+getHeure(livraison.getHeureFin())+"\n");
//					fichier.write("\tId client: "+livraison.getIdClient()+"\n");
//					
//					fichier.write("\n");
//				}
//			}
			
			fichier.flush();
			fichier.close();
		} catch(Exception e){
		} 
	}

	/**
	 * 
	 * @param args
     */
    public static void main(String[] args) {
    	ZoneGeographique zone = Parseur.lirePlan("./src/main/resources/plan10x10.xml");
    	List<PlageHoraire> plage = Parseur.lireLivraison("./src/main/resources/livraison10x10-1.xml", zone);
    	Tournee tournee = new Tournee(zone);
    	//TODO construire la tournee
    	ecrireFichierTri(tournee);
    }
    
    
	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static String getHeure(Date date) {
		return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}

}
