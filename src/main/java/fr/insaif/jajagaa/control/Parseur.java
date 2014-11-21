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
}
