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
    /**
     * Lit toutes les livraisons contenues dans un fichier de demande de livraisons et en renvoie une liste
     * @param inputStream Flux à partir duquel lire les données XML
     * @return Liste des livraisons à effectuer
     */
    public static List<Livraison> lireLivraison(InputStream inputStream) {
        List<Livraison> livraisonList = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(inputStream);
            Element journee = document.getRootElement();
            int entrepotIndice = Integer.parseInt(journee.getChild("Entrepot").getAttributeValue("adresse"));

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

    // TODO tester cette methode dans {@link fr.insaif.jajagaa.control.ParseurTest}
    /**
     * TODO
     * @param inputStream TODO
     * @return TODO
     */
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
                    Noeud noeud = new Noeud(Integer.parseInt(noeudXml.getAttributeValue("id")),
                            Integer.parseInt(noeudXml.getAttributeValue("x")),
                            Integer.parseInt(noeudXml.getAttributeValue("y")));

                    plan.add(noeud);
                }

            //Ajout des tronçons sortants de chaque noeud
            for (Element noeudXml : noeuds) {
                @SuppressWarnings("unchecked")
                List<Element> troncons = noeudXml.getChildren("LeTronconSortant");
                for (Element tronconXml : troncons) {
                    int id = Integer.parseInt(noeudXml.getAttributeValue("id"));
                    int idNoeudDestination = Integer.parseInt(tronconXml.getAttributeValue("idNoeudDestination"));
                    float longeur = Float.parseFloat(tronconXml.getAttributeValue("longueur"));
                    float vitesse = Float.parseFloat(tronconXml.getAttributeValue("vitesse"));
                    plan.get(id).addSortant(plan.get(idNoeudDestination), longeur, vitesse);
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
