package fr.insaif.jajagaa.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Tournee;


/**
 * AIDE________________________
 * la liste des livraisons qui appartiennent a cette tourn�e, l�heure de d�part 
 * et d�arriv�e pour chaque livraison, itin�raire entre chaque livraison et les 
 * informations sur la personne � livrer
 *
 */
public class ImprimerFdr {

	public boolean faireImpresion(Tournee tournee){
		List<Chemin> chemins = tournee.getChemins();
		Livraison liv = null;
		for(Chemin chem : chemins) {
			liv = chem.getLivraison();
			Date heuDeb = liv.getHeureLivraison();
			Date heuFin = liv.getHeureFin();
			//TODO Infos personnelles
			//TODO Itineraire
			//TODO ID
			//TODO formater Strings (tableau??)
		}
		return false;
	}
	
    public void ecrireFichierTri(String ecriture[], String id){
        String NomFichier = "./feuilleDeRoute_"+id;
        try(PrintWriter out  = new PrintWriter(new BufferedWriter(new FileWriter(NomFichier)))){
          
          for (int i = 0; i < ecriture.length; i++) {
            out.println(ecriture);
          }
          out.close();
        }
        catch(Exception e){
          e.printStackTrace();
        }
      }


}
