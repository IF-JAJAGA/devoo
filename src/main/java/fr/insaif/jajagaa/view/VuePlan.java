package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.EtatNoeud;
import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * La classe représente la Vue du plan et est composée de tous les tronçons, les vueNoeuds et les tournées du modèle.
 * @author H4201
 */
public class VuePlan extends JPanel{
    private static VuePlan vuePlan = null;
    public static VuePlan getInstance(){
        if(vuePlan == null)     vuePlan = new VuePlan();
        return vuePlan;
    }
    /**
     * Bordure du plan
     */
    protected static int border = VueNoeud.DIAMETRE;
    
    /**
     * Noeuds à afficher
     */
    protected List<VueNoeud> vueNoeuds = new Vector<>();
    /**
     * Troncons à afficher.
     */
    protected List<VueTroncon> vueTroncons = new Vector<>();
    /**
     * Tournée à afficher. Null si aucune n'est calculée.
     */
    protected VueTournee vueTournee;
    
    //Valeurs en mètres de la ville, s'
    /**
     * Largeur du plan selon la taille de la ville. S'actualise en fonction du chargement du plan.
     */
    private int XVille = 0;
    /**
     * Hauteur du plan selon la taille de la ville. S'actualise en fonction du chargement du plan.
     */
    private int YVille = 0;
    
    /**
     * Noeud sélectionné actuellement. Null si aucun ne l'est.
     */
    private VueNoeud vNSelectionne = null;
    
    /**
     * Vrai si le fond d'écran est gris
     */
    private boolean isGray = true;
    
    /**
     * Liste des couleurs à attribuer aux plages horaires.
     */
    private final List<Color> colors  =  new  ArrayList<Color>(){{
        add(Color.CYAN);
        add(Color.YELLOW);
        add(Color.PINK);
        add(Color.WHITE);
        add(Color.BLACK);
        add(Color.LIGHT_GRAY);
    }};
    /**
     * Permet d'associer les pages avec les couleurs de la liste colors.
     */
    private final Map<PlageHoraire,Color>  colorsPL = new HashMap<>();
    
    /**
     * Vrai si des livraisons sont présentes dans le plan.
     */
    private boolean livraisonsPresentes ;
    
    public void setTournee(Tournee tournee){
        vueTournee.setTourneeModel(tournee);
    }
    
    public List<VueNoeud> getVueNoeuds() {
        return vueNoeuds;
    }
    public VueTournee getVueTournee() {
        return vueTournee;
    }

    public VueNoeud getvNSelectionne() {
        return vNSelectionne;
    }
    
    public boolean getLivraisonsPresente() {
        return livraisonsPresentes;
    }

    public Map<PlageHoraire, Color> getColorsPL() {
        return colorsPL;
    }
    
    
    @Override
    /**
     * Méthode appelée a chaque fois que le dessin doit etre redessine
     * C'est ici que l'on peint tous les composants
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
              
        ZoneGeographique zg = Controleur.getInstance().getZone();

        Graphics2D g2 = (Graphics2D) g;
        
        for(VueTroncon vTr : vueTroncons){
            vTr.setOrigViewX(border + this.getX() + zg.getNoeudById(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setOrigViewY(border + this.getY() + zg.getNoeudById(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
            vTr.setDestViewX(border + this.getX() + zg.getNoeudById(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setDestViewY(border + this.getY() + zg.getNoeudById(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

            g2.setStroke(new BasicStroke(3));
            g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
        }


        if (vueTournee != null){
            for (VueTroncon vTr : getVueTournee().vTroncons) {

                vTr.setOrigViewX(border + this.getX() + zg.getNoeudById(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setOrigViewY(border + this.getY() + zg.getNoeudById(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
                vTr.setDestViewX(border + this.getX() + zg.getNoeudById(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setDestViewY(border + this.getY() + zg.getNoeudById(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.BLUE);
                g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
                
                g2.setStroke(new BasicStroke(3));
                new Fleche(vTr).draw(g2);
            }
        }
        
        for(VueNoeud vN : vueNoeuds){
                vN.setVueX(border + this.getX() + vN.getNoeudModele().getXMetre()*(this.getWidth() - 2*border) / XVille);
                vN.setVueY(border + this.getY() + vN.getNoeudModele().getYMetre()*(this.getHeight() - 2*border) / YVille);

                g2.setColor(vN.getCouleur());
                if (vN.getEtatLivraison()==EtatNoeud.LIVRAISON)
                {
                    g2.fillOval(vN.getVueX()-VueNoeud.DIAMETRE_LIVRAISON/2, vN.getVueY()-VueNoeud.DIAMETRE_LIVRAISON/2, VueNoeud.DIAMETRE_LIVRAISON, VueNoeud.DIAMETRE_LIVRAISON);
                }
                else if (vN.getCouleur() == Color.ORANGE){
                    g2.draw(new Rectangle2D.Double((vN.getVueX()-7.5),(vN.getVueY()-7.5),15,15));
                }
                else {
                    g2.fillOval(vN.getVueX()-VueNoeud.DIAMETRE/2, vN.getVueY()-VueNoeud.DIAMETRE/2, VueNoeud.DIAMETRE, VueNoeud.DIAMETRE);
                }
        }
    }

    
    private VuePlan() {
        setBackground(Color.GRAY);
 	this.paint(getGraphics());
    }
        
    /**
     * Détermine quel noeud est cliqué et met à jour en conséquence vNSelectionne.
     * @param locationOnPanel point cliqué sur le panel.
     * @return le noeud qui a été cliqué, null si aucun noeud n'est cliqué.
     */
    protected VueNoeud noeudEstClique(Point locationOnPanel) {
    	VueNoeud noeudSelectionne = null;
        Iterator<VueNoeud> itVN = vueNoeuds.iterator();
    	while(itVN.hasNext()){
            VueNoeud vN = itVN.next();
            if(vN.getNoeudClique(locationOnPanel)){
                noeudSelectionne = vN;
                break;
            }
        }
        changerSelection(noeudSelectionne);
        return noeudSelectionne;
    }
    
    /**
     * Change les flags des VueNoeud de la Vue pour que la sélection se mette à jour.
     * Appelée à la fois par le clic sur le plan et la liste de droite.
     * @param vueNoeud 
     */
    protected void changerSelection(VueNoeud vueNoeud){
        vNSelectionne = null;
        Iterator<VueNoeud> itVN = vueNoeuds.iterator();
        while(itVN.hasNext()){
            VueNoeud vN = itVN.next();
            if(vN.equals(vueNoeud)){
                vN.setEstSelectionne(true);
                vNSelectionne = vN;
            }
            else{
                if(vN.isEstSelectionne())   vN.setEstSelectionne(false);
            }
        }
    }

    /**
     * Redessine tout le plan en fonction du modèle (noeuds+troncons+livraison).
     * @param zoneGeo le modèle à afficher.
     */
    protected void actualiserPlan(ZoneGeographique zoneGeo) {
        viderPlan();
        if(zoneGeo == null){
            this.paint(getGraphics());
            return;
        }
        
        List<PlageHoraire> listPl = Controleur.getInstance().getPlagesHoraire();
        int i =0;
        for(PlageHoraire PL : listPl){
            colorsPL.put(PL,colors.get(i));
            i++;
        }
        
        ajouterNoeuds(zoneGeo);
        ajouterTournee(zoneGeo.getTournee());
        
        this.paint(getGraphics());
    }
    
    /**
     * Ajoute les noeuds et les livraisons à dessiner.
     * @param entrepot
     * @param listNoeuds 
     */
    private void ajouterNoeuds(ZoneGeographique zoneGeo){
        Noeud entrepot = zoneGeo.getEntrepot();
        List<Noeud> listNoeuds = zoneGeo.getNoeuds();
        Color c;
        for(Noeud noeud : listNoeuds) {
            if(noeud instanceof Livraison){
                if(!livraisonsPresentes){
                    livraisonsPresentes = true;
                }
                Livraison liv = (Livraison)noeud;
                if(liv.getXMetre()>XVille){
                    XVille = liv.getXMetre();
                }
                if(liv.getYMetre()>YVille)
                {
                    YVille = liv.getYMetre();
                }
                c = colorsPL.get(liv.getPlage());
                vueNoeuds.add(new VueNoeud(liv, c));
                List<Troncon> listTroncon = liv.getSortants();
                for(Troncon troncon : listTroncon){
                    vueTroncons.add(new VueTroncon(troncon));
                }
            }
            else{
                if(noeud.getXMetre()>XVille){
                    XVille = noeud.getXMetre();
                }
                if(noeud.getYMetre()>YVille){
                    YVille = noeud.getYMetre();
                }
                if ((entrepot != null) && (noeud.equals(entrepot))){
                    vueNoeuds.add(new VueNoeud(noeud, Color. ORANGE));
                }
                else{
                    vueNoeuds.add(new VueNoeud(noeud, Color.GREEN));
                }
                List<Troncon> listTroncon = noeud.getSortants();
                if (listTroncon != null) {
                    for(Troncon troncon : listTroncon){
                        vueTroncons.add(new VueTroncon(troncon));
                    }
                }
            }
        }
    }
    
    /**
     * Ajoute la tournée à afficher.
     * @param tournee 
     */
    private void ajouterTournee(Tournee tournee){
        if(tournee.getCheminsResultats().isEmpty()){
            return;
        }
        
        vueTournee = new VueTournee(tournee, Color.GREEN);
    }
    
    /**
     * Remet le plan à zéro.
     */
    private void viderPlan(){
        colorsPL.clear();
        vueNoeuds.clear();
        vueTroncons.clear();
        vueTournee = null;
    }
    
    /**
     * Change la couleur de fond d'écran.
     */
    void changeBackGround() {
        if(isGray){
            setBackground(Color.WHITE);
        }
        else{
            setBackground(Color.GRAY);
        }
        isGray = !isGray;
    }
}
