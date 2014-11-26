package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Classe qui s'occupe de parser les fichiers XML de livraisons et de plan.
 * TODO Vérifier où cette classe est censée être
 * @author gustavemonod
 */
public class Parseur {
    /** TODO tester (test actuel incomplet/vieux)
     * Lit toutes les livraisons contenues dans un fichier de demande de livraisons et en renvoie une liste
     * @param inputStream Flux à partir duquel lire les données XML
     * @return Liste des plages horaires contenant chacune leur livraisons à effectuer
     */
    public static List<PlageHoraire> lireLivraison(InputStream inputStream, ZoneGeographique zone) {
        SAXBuilder builder = new SAXBuilder();
        List<PlageHoraire> plages = null;
        try {
            Document document = builder.build(inputStream);
            Element journee = document.getRootElement();

            // L'identifiant de l'entrepôt permet de savoir quel nœud est l'entrepôt
            zone.setEntrepot(Integer.parseInt(journee.getChild("Entrepot").getAttributeValue("adresse")));

            // Liste des plages représentées dans le document XML
            @SuppressWarnings("unchecked")
            List<Element> plagesXml = journee.getChild("PlagesHoraires").getChildren("Plage");
            plages = new Vector<PlageHoraire>();
            for (Element plageXml : plagesXml) {
                PlageHoraire plageCourante = new PlageHoraire(plageXml.getAttributeValue("heureDebut"),
                        plageXml.getAttributeValue("heureFin"));
                plages.add(plageCourante);

                @SuppressWarnings("unchecked")
                List<Element> livraisonsXml = plageXml.getChild("Livraisons").getChildren("Livraison");
                for (Element livraisonXml : livraisonsXml) {
                    int idNoeud = Integer.parseInt(livraisonXml.getAttributeValue("adresse"));

                    // TODO À quoi sert l'information sur le client?
                    plageCourante.getLivraisons().add(new Livraison(zone.getNoeudId(idNoeud)));
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

        return plages;
    }

    // TODO tester cette methode dans {@link fr.insaif.jajagaa.control.ParseurTest}
    /**
     * TODO
     * @param inputStream TODO
     * @return TODO
     */
    public static ZoneGeographique lirePlan(InputStream inputStream) {
        List<Noeud> plan = new ArrayList<Noeud>();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(inputStream);
            Element reseau = document.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> noeuds = reseau.getChildren("Noeud");
            //Création des vueNoeuds contenus dans le fichier XML
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

//        return plan;
        return null; // TODO
    }
}
