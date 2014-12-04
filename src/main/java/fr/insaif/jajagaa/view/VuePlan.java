package fr.insaif.jajagaa.view;

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
 * @author alicia
 */
public class VuePlan extends JPanel{
    protected static int border = VueNoeud.DIAMETRE;
    
    protected List<VueNoeud> vueNoeuds = new Vector<>();
    protected List<VueTroncon> vueTroncons = new Vector<>();
    protected VueTournee vueTournee;
    
    //Valeurs en mètres de la ville, s'actualise en fonction du chargement du plan.
    private int XVille = 0;
    private int YVille = 0;
    
    /**
     * Les 2 attributs ci-dessous donnent des informations sur les données dans la vue.
     */
    private VueNoeud vNSelectionne = null;
    
    private boolean isGray = true;
    
    private final List<Color> colors  =  new  ArrayList<Color>(){{
        add(Color.CYAN);
        add(Color.YELLOW);
        add(Color.PINK);
        add(Color.WHITE);
        add(Color.BLACK);
        add(Color.LIGHT_GRAY);
    }};
    private final Map<PlageHoraire,Color>  colorsPL = new HashMap<>();
    
    
    private boolean livraisonsPresentes ;
    private final List<Livraison> livraisons  = new ArrayList<>();
    
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
    
    
    
    
	@Override
    public void paintComponent(Graphics g) {
        /* methode appelee a chaque fois que le dessin doit etre redessine
         * C'est ici que l'on peint tous les composants
         */
        super.paintComponent(g);
              
        ZoneGeographique zg = Controleur.getInstance().getZone();

        //On utilise un type différent pour avoir plus de possibilités et l'accès à de nouvelles méthodes
        //Les méthodes qu'on utilisait avec g continuent à fonctionner.
        Graphics2D g2 = (Graphics2D) g;
        
        // Dessin des tronçons
        for(VueTroncon vTr : vueTroncons){
            //Règle de trois pour afficher les points.
            vTr.setOrigViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setOrigViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
            vTr.setDestViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setDestViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

            //g2.setColor(vTr.getCouleur());
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
        }


        if (vueTournee != null){
            for (VueTroncon vTr : getVueTournee().vTroncons) {

                //Règle de trois pour afficher les points.
                vTr.setOrigViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setOrigViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
                vTr.setDestViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setDestViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

                //g2.setColor(vTr.getCouleur());
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.BLUE);
                g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
                
                g2.setStroke(new BasicStroke(3));
                new Fleche(vTr).draw(g2);
            }
        }
        
        for(VueNoeud vN : vueNoeuds){
                //Règle de trois pour afficher les points.
                vN.setVueX(border + this.getX() + vN.getNoeudModele().getXMetre()*(this.getWidth() - 2*border) / XVille);
                vN.setVueY(border + this.getY() + vN.getNoeudModele().getYMetre()*(this.getHeight() - 2*border) / YVille);

                g2.setColor(vN.getCouleur());
                if (vN.getPointDeLivraison()==VueNoeud.Etat.LIVRAISON)
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
//		TODO
//    /**
//     * Constructeur de la classe VuePlan
//     */
//    public VuePlan(List<VueNoeud> desNoeuds, List<VueTroncon> desTroncons, List<VueTournee> desTournees) {
//        vueNoeuds = (Vector<VueNoeud>) desNoeuds;
//        vueTroncons = (Vector<VueTroncon>) desTroncons;
//        vueTournee = (Vector<VueTournee>) desTournees;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    /**
     * Creates new form VuePlan
     */
    public VuePlan() {
        setBackground(Color.GRAY);
 	this.paint(getGraphics());
    }
     /**
     *
     * @param noeuds
     * @param troncons
     * @param livraisons
     */
    public VuePlan (List<Noeud> noeuds, List<Troncon> troncons, List<Livraison> livraisons) {
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    /**
     * Détermine quel noeud est cliqué et met à jour en conséquence vNSelectionne.
     * @param locationOnPanel point cliqué sur le panel
     * @return le noeud qui a été cliqué, null si aucun noeud n'est cliqué.
     */
    protected VueNoeud noeudEstClique(Point locationOnPanel) {
    	VueNoeud vNSelectionne = null;
        Iterator<VueNoeud> itVN = vueNoeuds.iterator();
    	while(itVN.hasNext()){
            VueNoeud vN = itVN.next();
            if(vN.getNoeudClique(locationOnPanel)){
                vNSelectionne = vN;
                break;
            }
        }
        changerSelection(vNSelectionne);
        return vNSelectionne;
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

//    public VueTournee getVueTournee() {
//        return vueTournee;
//    }

    /**
     * Redessine tout le plan en fonction du modèle (noeuds+troncons+livraison).
     * @param zoneGeo 
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
        
        ajouterNoeuds(zoneGeo.getEntrepot(), zoneGeo.getNoeuds());
//        livraisonsPresentes = ajouterLivraisons(zoneGeo.getTournee().getPlagesHoraire());
        ajouterTournee(zoneGeo.getTournee());
        
        this.paint(getGraphics());
    }
    
    /**
     * Ajoute les noeuds à dessiner.
     * @param entrepot
     * @param listNoeuds 
     */
    private void ajouterNoeuds(Noeud entrepot, List<Noeud> listNoeuds){
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
                //TODO : redéfinir .equals 
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
     * Deprecated TODO Jérôme
     * Ajoute les troncons à dessiner.
     * @param PL 
     */
    private boolean ajouterLivraisons(List<PlageHoraire> PL) {
        if(PL == null || PL.isEmpty()) return false;
        
        System.out.println("PL.size() : " + PL.size());
        
        for(PlageHoraire pl : PL){
            List<Livraison> listNoeuds = pl.getLivraisons();
            for (Livraison liv : listNoeuds){
                if(liv.getXMetre()>XVille){
                    XVille = liv.getXMetre();
                }
                if(liv.getYMetre()>YVille)
                {
                    YVille = liv.getYMetre();
                }
                ajouterLivraisonAVueNoeuds(new VueNoeud(liv, Color.YELLOW));
                List<Troncon> listTroncon = liv.getSortants();
                for(Troncon troncon : listTroncon){
                    vueTroncons.add(new VueTroncon(troncon));
                }
            }
        }
        return true;
    }
    
    private void ajouterTournee(Tournee tournee){
        if(tournee.getCheminsResultats().isEmpty()){
            return;
        }
        
        vueTournee = new VueTournee(tournee, Color.GREEN);
    }
    
    private void ajouterLivraisonAVueNoeuds(VueNoeud vN){
        for(int i=0,len=vueNoeuds.size() ; i<len ; i++){
            if(vueNoeuds.get(i).getNoeudModele().getId() == vN.getNoeudModele().getId()){
                vueNoeuds.set(i, vN);
                i=len;
            }
        }
    }
    
    private void viderPlan(){
        vueNoeuds.clear();
        vueTroncons.clear();
        vueTournee = null;
    }

    boolean getLivraisonsPresente() {
        return livraisonsPresentes;
    }

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
