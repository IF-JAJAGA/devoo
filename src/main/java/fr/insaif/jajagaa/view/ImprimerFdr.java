package fr.insaif.jajagaa.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;
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
	 * Méthode permettant de générer un fichier .txt qui va servir lors de l'impression de la feuille de route
	 * @param tournee
	 */
	public static void ecrireFichierTri(Tournee tournee){
		String id = Calendar.getInstance().toString();
		String NomFichier = "./src/test/resources/feuilleDeRoute_"+id+".txt";
		try{
			PrintWriter fichier  = new PrintWriter(new BufferedWriter(new FileWriter(NomFichier)));
			List<Chemin> chemins = tournee.getCheminsResultats();
                        fichier.write("Tournee du " + id + "\n");
                        fichier.write("---------------------------------------------\n");
                        for (int i=0; i < chemins.size(); i++) {
                            Chemin chemin = chemins.get(i);
                            Livraison destination = (Livraison) chemin.getDestination().getNoeud();
                            List<Troncon> parcours = chemin.getTroncons();
                            fichier.write("Livraison numero : " + destination.getId() + "\n");
                            fichier.write("Heure d'arrivee prevue : " + destination.getHeureLivraison() + "\n");
                            fichier.write("Heure de depart prevue : " + destination.getHeureFin() + "\n");
                            fichier.write("Identifiant client : " + destination.getIdClient() + "\n");
                            fichier.write("Itineraire :" + "\n");
                            for (Troncon rue : parcours) {
                                System.out.println("rue numero " + rue.getNomRue());
                                fichier.write(rue.getNomRue() + "\n");
                            }
                            fichier.write("-----------------------------\n");
                        }
			fichier.flush();
			fichier.close();
		} catch(Exception e){
		} 
	}

	/**
	 * 
	 * @param args
     */
    public static void main(String[] args) throws ParseurException{
    	ZoneGeographique zone = Parseur.lirePlan("./src/main/resources/plan10x10.xml");
    	List<PlageHoraire> plage = Parseur.lireLivraison("./src/main/resources/livraison10x10-1.xml", zone);
    	Tournee tournee = new Tournee(zone);
        System.out.println("test feuille de route");
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
