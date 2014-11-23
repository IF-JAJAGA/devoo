package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui s'occupe de parser les fichiers XML de livraisons et de plan.
 * TODO Vérifier où cette classe est censée être
 * @author gustavemonod
 */
public class Parseur {
    public static List<Livraison> lireLivraison(InputStream inputStream) {
        List<Livraison> livraisonList = new ArrayList<Livraison>();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(inputStream);
            Element journee = document.getRootElement();
            Noeud entrepot = new Noeud(0, 0, 0); // TODO Trouver quel noeud à comme id "adresse"
	    //Pourquoi ne pas simplement enregistrer l'info de l'adresse de l'entrepot sous forme d'un attribut (int) de zoneGeographique ? 
	    //Puis se servir de cette info plus tard ?
	    //int entrepot = journee.getChild("Entrepot").getAttributeValue("adresse");

            @SuppressWarnings("unchecked")
            List<Element> plages = journee.getChild("PlagesHoraires").getChildren("Plage");
            for (Element plage : plages) {
                PlageHoraire plageHoraire = new PlageHoraire(plage.getAttributeValue("heureDebut"),
                        plage.getAttributeValue("heureFin"));

                @SuppressWarnings("unchecked")
                List<Element> livraisons = plage.getChild("Livraisons").getChildren("Livraison");
                for (Element livraison : livraisons) {
                    /* TODO Prendre en compte les informations sur le client et l'adresse de la livraison
                    String idClient = livraison.getAttributeValue("client");
                    String adresse = livraison.getAttributeValue("adresse");
                    */
                    livraisonList.add(new Livraison(livraison.getAttributeValue("id"), plageHoraire));
                }
            }
        } catch (IOException io) {
            System.err.println("Impossible d'accéder au fichier correctement");
            System.exit(501);
        } catch (JDOMException jdomex) {
            System.err.println("Ficher XML mal formé: mauvaise syntaxe XML");
            System.exit(502);
        } catch (NullPointerException nullptrex) {
            System.err.println("Ficher XML mal formé: element ou attribut manquant");
            System.exit(502);
        }

        return livraisonList;
    }
	
    public static List<Noeud> lirePlan(InputStream inputStream) {
        List<Noeud> plan = new ArrayList<Noeud>();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(inputStream);
            Element reseau = document.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> noeuds = reseau.getChildren("Noeud");
	    //Création des noeuds contenus dans le fichier XML
	    //Et ajout de ceux-ci dans la liste plan
            for (Element noeudXml : noeuds) {
                Noeud noeud = new Noeud(noeudXml.getAttributeValue("id"),
                        noeudXml.getAttributeValue("x"),
			noeudXml.getAttributeValue("y"));

                @SuppressWarnings("unchecked")
		plan.add(noeud);
            }
		
	    //Ajout des tronçons sortants de chaque noeud
	    for (Element noeudXml : noeuds) {
		list Element troncons = noeudXml.getChildren("LeTronconSortant");
		for (Element tronconXml : troncons) {
			plan.get(noeudXml.getAttributeValue("id")).addSortant(plan.get(tronconXml.getAttributeValue("idNoeudDestination")),tronconXml.getAttributeValue("longueur"),tronconXml.getAttributeValue("vitesse")); 
		}
	    }	

        } catch (IOException io) {
            System.err.println("Impossible d'accéder au fichier correctement");
            System.exit(501);
        } catch (JDOMException jdomex) {
            System.err.println("Ficher XML mal formé: mauvaise syntaxe XML");
            System.exit(502);
        } catch (NullPointerException nullptrex) {
            System.err.println("Ficher XML mal formé: element ou attribut manquant");
            System.exit(502);
        }

        return plan;
    }
}
