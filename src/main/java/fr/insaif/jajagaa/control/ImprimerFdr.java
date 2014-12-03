package fr.insaif.jajagaa.control;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
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
	public static boolean ecrireFichier(ZoneGeographique zone){
                Tournee tournee = zone.getTournee();
		//TODO nom fichier
		String nomFichier = "./src/test/resources/feuilleDeRoute_.txt";
		BufferedWriter fichier = null;
		try{
			fichier = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "utf-8"));
			List<Chemin> chemins = tournee.getCheminsResultats();
			//TODO: Numero fdr
//			fichier.write("Tournee du " + id + "\n");
//			fichier.write("---------------------------------------------\n");
			for (int i=0; i < chemins.size(); i++) {
				Chemin chemin = chemins.get(i);
				Livraison destination = (Livraison) zone.getNoeudById(chemin.getDestination().getIdNoeud());
				List<Troncon> parcours = chemin.getTroncons();
				fichier.write("Livraison numero : " + destination.getId() + "\n");
				fichier.write("\tHeure d'arrivee prevue : " + getHeure(destination.getHeureLivraison()) + "\n");
				fichier.write("\tHeure de depart prevue : " + getHeure(destination.getHeureFin()) + "\n");
				fichier.write("\tIdentifiant client : " + destination.getIdClient() + "\n");
				fichier.write("\tItineraire : \n");
//TODO: Choisir boucle
				Troncon avant = null;
				for(Troncon troncon : parcours) {
					if (avant != null){
						if(troncon.getIdOrigine() == avant.getIdDestination()) {

							fichier.write("\t\t- "+troncon.getNomRue() + "\n");
						}
						else {
							System.err.println("Erreur lors de la lecture des troncons de chemins");
						}
					}
					else {
						fichier.write("\t\t- "+troncon.getNomRue() + "\n");
					}
					avant=troncon;
				}

				
//				for (int j=0 ; j < parcours.size() ; j++) {
//					if (j>0) {
//						if (parcours.get(j).getIdOrigine() == parcours.get(j-1).getIdDestination()) {
//							fichier.write("\t\t- "+parcours.get(j).getNomRue() + "\n");
//						} else {
//							System.err.println("Erreur lors de la lecture des troncons de chemins");
//						}
//					} else {
//						fichier.write("\t\t- "+parcours.get(j).getNomRue() + "\n");
//					}
//				}
				fichier.write("-----------------------------\n");
			}
			fichier.flush();
			fichier.close();
			return true;
		} catch ( IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		finally {
			try {
				fichier.close();

			}catch (Exception ex) {}
		}
	}


//	/**
//	 * 
//	 * @param args
//     */
//	public static void main(String[] args) throws ParseurException{
//		ZoneGeographique zone = Parseur.lirePlan("./src/main/resources/plan10x10.xml");
//		List<PlageHoraire> plage = Parseur.lireLivraison("./src/main/resources/livraison10x10-1.xml", zone);
//		Tournee tournee = new Tournee(zone);
//		tournee.setPlagesHoraire(plage);
//		tournee.solve(1000);
//		ecrireFichier(zone);
//	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static String getHeure(Date date) {
		return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}

}
