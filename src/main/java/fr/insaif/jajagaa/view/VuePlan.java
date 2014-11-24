package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.Chemin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Troncon;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.Iterator;

/**
 * La classe représente la Vue du plan et est composée de tous les tronçons, les noeuds et les tournées du modèle.
 * @author alicia
 */
public class VuePlan extends JPanel{
	protected static int border = VueNoeud.DIAMETRE;
	
    protected Vector<VueNoeud> noeuds = new Vector<VueNoeud>();
    protected Vector<VueTroncon> troncons = new Vector<VueTroncon>();
    protected Vector<VueTournee> tournees =  new Vector<VueTournee>();
    
    //Valeurs en mètres avant que le chargement soit implémenté.
    protected final int XVille = 1000;
    protected final int YVille = 700;
    
	@Override
    public void paintComponent(Graphics g) {
        /* methode appelee a chaque fois que le dessin doit etre redessine
         * C'est ici que l'on peint tous les composants
         */
        super.paintComponent(g);
        
        Iterator<VueTroncon> itTroncon = troncons.iterator();
        Iterator<VueTournee> itTournee = tournees.iterator();
        Iterator<VueNoeud> itNoeud = noeuds.iterator();
        
        //On utilise un type différent pour avoir plus de possibilités et l'accès à de nouvelles méthodes
        //Les méthodes qu'on utilisait avec g continuent à fonctionner.
        Graphics2D g2 = (Graphics2D) g;
        
        // Dessin des tronçons
        while(itTroncon.hasNext()){
            VueTroncon vTr = itTroncon.next();                    
            //Règle de trois pour afficher les points.
            vTr.setOrigViewX(border + this.getX() + vTr.getTronconModel().getOrigine().getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setOrigViewY(border + this.getY() + vTr.getTronconModel().getOrigine().getYMetre()*(this.getHeight() -2*border) / YVille);
            vTr.setDestViewX(border + this.getX() + vTr.getTronconModel().getDestination().getXMetre()*(this.getWidth() - 2*border) / XVille);
            vTr.setDestViewY(border + this.getY() + vTr.getTronconModel().getDestination().getYMetre()*(this.getHeight() -2*border) / YVille);

            //g2.setColor(vTr.getCouleur());
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
        }
        // Dessin des noeuds
        while(itNoeud.hasNext()){
                VueNoeud vN = itNoeud.next();
                //Règle de trois pour afficher les points.
                vN.setVueX(border + this.getX() + vN.getNoeudModele().getXMetre()*(this.getWidth() - 2*border) / XVille);
                vN.setVueY(border + this.getY() + vN.getNoeudModele().getYMetre()*(this.getHeight() - 2*border) / YVille);

                g2.setColor(vN.getCouleur());
                g2.fillOval(vN.getVueX()-VueNoeud.DIAMETRE/2, vN.getVueY()-VueNoeud.DIAMETRE/2, VueNoeud.DIAMETRE, VueNoeud.DIAMETRE);
        }
        
        // GROSSE GALERE : différence de granularité entre troncon < chemin < tournee et vueTroncon < VueTournee
//        while(itTournee.hasNext()){
//            VueTournee vTo = itTournee.next();
//            
//            Iterator itToCh = vTo.tourneeModel.getChemins().iterator();
//            while(itToCh.hasNext()){
//                
//                Chemin chem = (Chemin) itToCh.next();
//                Iterator itChTr = chem.getTroncons().iterator();
//                while(itChTr.hasNext())
//                    
//                    VueTournee vTri = 
//
//                    //Règle de trois pour afficher les points.
//                    vTr.setOrigViewX(border + this.getX() + vTr.getTronconModel().getOrigine().getXMetre()*(this.getWidth() - 2*border) / XVille);
//                    vTr.setOrigViewY(border + this.getY() + vTr.getTronconModel().getOrigine().getYMetre()*(this.getHeight() -2*border) / YVille);
//                    vTr.setDestViewX(border + this.getX() + vTr.getTronconModel().getDestination().getXMetre()*(this.getWidth() - 2*border) / XVille);
//                    vTr.setDestViewY(border + this.getY() + vTr.getTronconModel().getDestination().getYMetre()*(this.getHeight() -2*border) / YVille);
//
//                    //g2.setColor(vTr.getCouleur());
//                    g2.setStroke(new BasicStroke(3));
//                    g2.drawLine(vTr.origViewX, vTr.origViewY, vTr.destViewX, vTr.destViewY);
//            }
//        }
        

    }
//		TODO
//    /**
//     * Constructeur de la classe VuePlan
//     */
//    public VuePlan(List<VueNoeud> desNoeuds, List<VueTroncon> desTroncons, List<VueTournee> desTournees) {
//        noeuds = (Vector<VueNoeud>) desNoeuds;
//        troncons = (Vector<VueTroncon>) desTroncons;
//        tournees = (Vector<VueTournee>) desTournees;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    
    /**
     * Creates new form VuePlan
     */
    public VuePlan() {
		setBackground(Color.GRAY);
        initComponents();
    	
    	//Pour l'instant ici
    	noeuds.add(new VueNoeud(new Noeud(0, 200, 200), Color.BLUE));
    	noeuds.add(new VueNoeud(new Noeud(1, 0, 0), Color.GREEN));
    	noeuds.add(new VueNoeud(new Noeud(2, 1000, 700), Color.ORANGE));
    	noeuds.add(new VueNoeud(new Noeud(3, 1000, 0), Color.YELLOW));
    	noeuds.add(new VueNoeud(new Noeud(4, 0, 700), Color.BLACK));
        troncons.add(new VueTroncon(new Troncon(new Noeud(0,200,200), new Noeud(2,1000,700), 400,4)));
        troncons.add(new VueTroncon(new Troncon(new Noeud(1,0,0), new Noeud(4,0,700), 400,4)));
        troncons.add(new VueTroncon(new Troncon(new Noeud(0,200,200), new Noeud(3,1000,0), 400,4)));
        troncons.add(new VueTroncon(new Troncon(new Noeud(4,0,700), new Noeud(2,1000,700), 400,4)));
        troncons.add(new VueTroncon(new Troncon(new Noeud(1,0,0), new Noeud(3,1000,0), 400,4)));
        troncons.add(new VueTroncon(new Troncon(new Noeud(2,1000,700), new Noeud(3,1000,0), 400,4)));    

    	
    	this.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			super.mouseClicked(e);
    			quelquUnEstClique(e.getPoint());
    		}
		});
    	this.paint(getGraphics());
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
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    private void quelquUnEstClique(Point locationOnPanel) {
    	boolean rePaint = false;
    	for(VueNoeud vN : noeuds){
    		if(vN.changementSelection(locationOnPanel)) 	rePaint = true;
    	}
    	if(rePaint)		repaint();
	}
}
