package fr.insaif.jajagaa.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 * Classe qui s'occupe de parser les fichiers XML de livraisons et de plan.
 * TODO Vérifier où cette classe est censée être
 * @author gustavemonod
 */
public class Parseur{
	
	/** TODO tester (test actuel incomplet/vieux)
     * Lit toutes les livraisons contenues dans un fichier de demande de livraisons et en renvoie une liste
     * @param inputStream Flux à partir duquel lire les données XML
     * @param zone ZoneGeographique la liste des noeuds dans laquelle les livraisons sont contenues
     * @return Liste des plages horaires contenant chacune leur livraisons à effectuer

     */
    public static List<PlageHoraire> lireLivraison(String fichierEntree, ZoneGeographique zone) throws ParseurException{
        List<PlageHoraire> plageList = new ArrayList<PlageHoraire>();
        SAXBuilder builder = new SAXBuilder();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fichierEntree);            
            Document document = builder.build(inputStream);
            Element journee = document.getRootElement();

            // L'identifiant de l'entrepôt permet de savoir quel nœud est l'entrepôt
            zone.setEntrepot(Integer.parseInt(journee.getChild("Entrepot").getAttributeValue("adresse")));

            // Liste des plages représentées dans le document XML
            @SuppressWarnings("unchecked")
            List<Element> plages = journee.getChild("PlagesHoraires").getChildren("Plage");
            for (Element plage : plages) {
                PlageHoraire plageHoraire = new PlageHoraire(plage.getAttributeValue("heureDebut"),
                		plage.getAttributeValue("heureFin"));
                List<Livraison> livraisonList = new ArrayList<Livraison>();

                @SuppressWarnings("unchecked")
                List<Element> livraisons = plage.getChild("Livraisons").getChildren("Livraison");
                for (Element livraison : livraisons) {
                	int idNoeud = Integer.parseInt(livraison.getAttributeValue("adresse"));
                	int idLiv = Integer.parseInt(livraison.getAttributeValue("id"));
                	int idClient = Integer.parseInt(livraison.getAttributeValue("client"));

                	Livraison l = new Livraison(zone.getNoeudId(idNoeud),idLiv, idClient);
                	livraisonList.add(l);
                	zone.modifierNoeudEnLivraison(idNoeud, l);
                }
                plageHoraire.setLivraisons(livraisonList);
                plageList.add(plageHoraire);
            }
        } catch (FileNotFoundException fnfe) {
            throw new ParseurException("Fichier inexistant");
        } catch (IOException io) {
            throw new ParseurException("Impossible d'accéder au fichier correctement");
        } catch (JDOMException jdomex) {
            throw new ParseurException("Ficher XML mal formé: mauvaise syntaxe XML");
        } catch (NullPointerException nullptrex) {
            throw new ParseurException("Ficher XML mal formé: element ou attribut manquant");
        } catch (IndexOutOfBoundsException ioobe) {
            throw new ParseurException("Fichier XML erroné: valeurs de certains attributs inexistants");
        } catch (NumberFormatException nfe) {
            throw new ParseurException("Données du fichier XML non conforme");
        }

        return plageList;
    }

    // TODO tester cette methode dans {@link fr.insaif.jajagaa.control.ParseurTest}
    /**
     * Méthode permettant de déterminer la zone géographique contenant les noeuds et les tronçons associés
     * @param inputStream Fichier XML contenant les noeuds et les tronçons composants la zone géographique
     * @return La zone géographique contenant les noeuds passés en paramètre
     */
    public static ZoneGeographique lirePlan(String fichierEntree) throws ParseurException{
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fichierEntree);
        } catch (FileNotFoundException ex) {
        	//TODO: À quoi ça sert?
//            Logger.getLogger(Parseur.class.getName()).log(Level.SEVERE, null, ex);
            throw new ParseurException("Fichier inexistant");
        }
        
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
                int noeudId = Integer.parseInt(noeudXml.getAttributeValue("id"));
                Noeud noeud = new Noeud(noeudId,
                        Integer.parseInt(noeudXml.getAttributeValue("x")),
                        Integer.parseInt(noeudXml.getAttributeValue("y")));

                plan.add(noeudId, noeud);
            }

            //Ajout des tronçons sortants de chaque noeud
            for (Element noeudXml : noeuds) {
                @SuppressWarnings("unchecked")
                List<Element> troncons = noeudXml.getChildren("LeTronconSortant");
                for (Element tronconXml : troncons) {
                    int id = Integer.parseInt(noeudXml.getAttributeValue("id"));
                    int idNoeudDestination = Integer.parseInt(tronconXml.getAttributeValue("idNoeudDestination"));
                    float longeur = Float.parseFloat((tronconXml.getAttributeValue("longueur")).replace(",", "."));
                    float vitesse = Float.parseFloat((tronconXml.getAttributeValue("vitesse")).replace(",", "."));
                    String rue = tronconXml.getAttributeValue("nomRue");
                    plan.get(id).addSortant(plan.get(idNoeudDestination), longeur, vitesse, rue);
                }
            }
        } catch (FileNotFoundException fnfe) { //TODO: il sert là maintenant? Car il se déclanche haut.
            throw new ParseurException("Fichier inexistant");
        } catch (IOException io) {
            throw new ParseurException("Impossible d'accéder au fichier correctement");
        } catch (JDOMException jdomex) {
            throw new ParseurException("Ficher XML mal formé: mauvaise syntaxe XML");
        } catch (NullPointerException nullptrex) {
            throw new ParseurException("Ficher XML mal formé: element ou attribut manquant");
        } catch (IndexOutOfBoundsException ioobe) {
            throw new ParseurException("Fichier XML erroné: valeurs de certains attributs inexistants");
        } catch (NumberFormatException nfe) {
            throw new ParseurException("Données du fichier XML non conforme");
        } 
        
        return (new ZoneGeographique(plan));
    }
}
