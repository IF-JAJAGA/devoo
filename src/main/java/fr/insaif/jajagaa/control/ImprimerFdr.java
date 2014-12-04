package fr.insaif.jajagaa.control;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;


/**
 * Classe permettant la réalisation d'un fichier imprimable correspondant à la 
 * feuille de route destiné aux livreurs en cas de panne du système
 */
public class ImprimerFdr {

	/**
         * Méthode permettant de générer un fichier .txt qui va servir lors de 
         * l'impression de la feuille de route
         * @param zone
         * @return un booléen indiquant si le fichier a bien été créé 
         *      correctement ou pas
         */
	public static boolean ecrireFichier(ZoneGeographique zone){
        Tournee tournee = zone.getTournee();
        String idFichier = getHeure(Calendar.getInstance().getTime()).replace(":", "");
		String nomFichier = "./src/test/resources/feuilleDeRoute_"+idFichier+".txt";
		BufferedWriter fichier = null;
		try{
			fichier = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "utf-8"));
			List<Chemin> chemins = tournee.getCheminsResultats();
			//TODO: ID fdr
			for (int i=0; i < chemins.size(); i++) {
				Chemin chemin = chemins.get(i);
				Livraison destination = (Livraison) zone.getNoeudById(chemin.getDestination().getIdNoeud());
				List<Troncon> parcours = chemin.getTroncons();
                                
				fichier.write( (i+1) + ") Livraison numero : " + destination.getId() + "\n");
				fichier.write("\tHeure d'arrivee prevue : " + getHeure(destination.getHeureLivraison()) + "\n");
				fichier.write("\tHeure de depart prevue : " + getHeure(destination.getHeureFin()) + "\n");
				fichier.write("\tIdentifiant client : " + destination.getIdClient() + "\n");
				fichier.write("\tItineraire : \n");
                                
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
				fichier.write("-----------------------------\n");
			}
		} catch ( IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		finally {
                    try {
			fichier.flush();
                        fichier.close();
                        return true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
		}
	}

	/**
         * Fonction permettant de mettre l'heure au format sous lequel nous 
         * voulons qu'il soit affiché dans la feuille de route 
         * @param date que nous voulons mettre au format souhaité
         * @return 
         */
	@SuppressWarnings("deprecation")
	public static String getHeure(Date date) {
		return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	}

}
