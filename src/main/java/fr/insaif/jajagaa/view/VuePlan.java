package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Livraison;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La classe représente la Vue du plan et est composée de tous les tronçons, les vueNoeuds et les tournées du modèle.
 * @author alicia
 */
public class VuePlan extends JPanel{
    protected static int border = VueNoeud.DIAMETRE;
    
    protected List<VueNoeud> vueNoeuds = new Vector<VueNoeud>();
    protected List<VueTroncon> vueTroncons = new Vector<VueTroncon>();
    protected VueTournee vueTournee;
    
    //Valeurs en mètres avant que le chargement soit implémenté.
    protected final int XVille = 1000;
    protected final int YVille = 700;

    public List<VueNoeud> getVueNoeuds() {
        return vueNoeuds;
    }
    
    
	@Override
    public void paintComponent(Graphics g) {
        /* methode appelee a chaque fois que le dessin doit etre redessine
         * C'est ici que l'on peint tous les composants
         */
        super.paintComponent(g);
        
        Iterator<VueTroncon> itTroncon = vueTroncons.iterator();
        Iterator<VueNoeud> itNoeud = vueNoeuds.iterator();
        
        ZoneGeographique zg = Controleur.getInstance().getZone();

        //On utilise un type différent pour avoir plus de possibilités et l'accès à de nouvelles méthodes
        //Les méthodes qu'on utilisait avec g continuent à fonctionner.
        Graphics2D g2 = (Graphics2D) g;
        
        // Dessin des tronçons
        while(itTroncon.hasNext()){
            VueTroncon vTr = itTroncon.next();                    
            //Règle de trois pour afficher les points.
            vTr.setOrigViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setOrigViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
            vTr.setDestViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setDestViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

            //g2.setColor(vTr.getCouleur());
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
        }


        Iterator itTr = getVueTournee().vTroncons.iterator();
        while (itTr.hasNext()) {
            VueTroncon vTr = (VueTroncon) itTr.next();

                //Règle de trois pour afficher les points.
                vTr.setOrigViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setOrigViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdOrigine()).getYMetre()*(this.getHeight() -2*border) / YVille);
                vTr.setDestViewX(border + this.getX() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getXMetre()*(this.getWidth() - 2*border) / XVille);
                vTr.setDestViewY(border + this.getY() + zg.getNoeudId(vTr.getTronconModel().getIdDestination()).getYMetre()*(this.getHeight() -2*border) / YVille);

                //g2.setColor(vTr.getCouleur());
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.BLUE);
                g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
        }
        
        
        while(itNoeud.hasNext()){
                VueNoeud vN = itNoeud.next();
                //Règle de trois pour afficher les points.
                vN.setVueX(border + this.getX() + vN.getNoeudModele().getXMetre()*(this.getWidth() - 2*border) / XVille);
                vN.setVueY(border + this.getY() + vN.getNoeudModele().getYMetre()*(this.getHeight() - 2*border) / YVille);

                g2.setColor(vN.getCouleur());
                if (vN.getPointDeLivraison()==VueNoeud.Etat.LIVRAISON)
                {
                    g2.fillOval(vN.getVueX()-VueNoeud.DIAMETRE_LIVRAISON/2, vN.getVueY()-VueNoeud.DIAMETRE_LIVRAISON/2, VueNoeud.DIAMETRE_LIVRAISON, VueNoeud.DIAMETRE_LIVRAISON);
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
        initComponents();
    	
    	//Pour l'instant ici
        VueNoeud vn1 = new VueNoeud(new Noeud(0, 200, 200), Color.BLUE);
        vn1.setEstPointDeLivraison(VueNoeud.Etat.LIVRAISON);
    	vueNoeuds.add(vn1);
    	vueNoeuds.add(new VueNoeud(new Noeud(1, 0, 0), Color.GREEN));
    	vueNoeuds.add(new VueNoeud(new Noeud(2, 1000, 700), Color.ORANGE));
    	vueNoeuds.add(new VueNoeud(new Noeud(3, 1000, 0), Color.YELLOW));
    	vueNoeuds.add(new VueNoeud(new Noeud(4, 0, 700), Color.BLACK));
        //Troncon t1 = new Troncon(new Noeud(0,200,200), new Noeud(2,1000,700), 400,4,"rue1");
        Troncon t1 = new Troncon(0, 2, 400,4,"rue1");
        vueTroncons.add(new VueTroncon(t1));
        //Troncon t2 = new Troncon(new Noeud(1,0,0), new Noeud(4,0,700), 400,4,"rue2");
        Troncon t2 = new Troncon(1, 4, 400,4,"rue2");
        vueTroncons.add(new VueTroncon(t2));
        //Troncon t3 = new Troncon(new Noeud(0,200,200), new Noeud(3,1000,0), 400,4,"rue3");
        Troncon t3 = new Troncon(0, 3, 400,4,"rue3");
        vueTroncons.add(new VueTroncon(t3));
        //Troncon t4 = new Troncon(new Noeud(4,0,700), new Noeud(2,1000,700), 400,4,"rue4");
        Troncon t4 = new Troncon(4, 2, 400,4,"rue4");
        vueTroncons.add(new VueTroncon(t4));
        //Troncon t5 = new Troncon(new Noeud(1,0,0), new Noeud(3,1000,0), 400,4,"rue5");
        Troncon t5 = new Troncon(1, 3, 400,4,"rue5");
        vueTroncons.add(new VueTroncon(t5));
        //Troncon t6 = new Troncon(new Noeud(2,1000,700), new Noeud(3,1000,0), 400,4,"rue6");
        Troncon t6 = new Troncon(2, 3, 400,4,"rue6");
        vueTroncons.add(new VueTroncon(t6));
        
        List <Troncon> tronconsTournee = new ArrayList<Troncon>();
        tronconsTournee.add(t1);
        tronconsTournee.add(t6);
//        Chemin chemin = new Chemin(tronconsTournee);
//        Tournee tourneeModele = new Tournee();
//        tourneeModele.addCheminResultat(chemin);
//        vueTournee = new VueTournee(tourneeModele, Color.BLUE);
        //getVueTournee().add(vT);


    	
    	this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changerSelection(noeudEstClique(e.getPoint()));
                repaint();
            }
	});
    	this.paint(getGraphics());
    }
     
    public VuePlan (List<Noeud> noeuds, List<Troncon> troncons, List<Livraison> livraisons) {
        //TODO 
    }
    
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    /**
     * 
     * @param locationOnPanel point cliqué sur le panel
     * @return le noeud qui a été cliqué, null aucun noeud n'est cliqué.
     */
    private VueNoeud noeudEstClique(Point locationOnPanel) {
    	boolean rePaint = false;
        Iterator<VueNoeud> itVN = vueNoeuds.iterator();
        VueNoeud noeudSelectionne = null;
    	while(itVN.hasNext()){
            VueNoeud vN = itVN.next();
            if(vN.getNoeudClique(locationOnPanel)){
               return noeudSelectionne;
            }
        }
        
        //TODO : Envoi au contrôleur pour demander activer le bouton "ajouter un noeud"
    	
        return null;
    }
    /**
     * Change les flags des VueNoeud de la Vue pour que la sélection se mette à jour.
     * Appelée à la fois par 
     * @param vueNoeud 
     */
    private void changerSelection(VueNoeud vueNoeud){
        Iterator<VueNoeud> itVN = vueNoeuds.iterator();
    }

    public VueTournee getVueTournee() {
        return vueTournee;
    }
}
