/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import fr.insaif.jajagaa.view.VueNoeud;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


/**
 * Contient les éléments à droite du plan.
 * @author jeje
 */
public class ConteneurDroite extends JPanel{
    private JLabel titre;
    private ListNoeuds listeNoeuds;
    private JButton btnAddNoeud;
    
    private SpringLayout layout;
    
    public void majListe(List<VueNoeud> vueNoeuds) {
        listeNoeuds.maj(vueNoeuds);
    }

    public JButton getBtnAddNoeud() {
        return btnAddNoeud;
    }

    public ListNoeuds getListeNoeuds() {
        return listeNoeuds;
    }

    

    public ConteneurDroite() {
        initComponents();
        placeComponents();
        addListeners();
    }
    
    private void initComponents(){
        titre = new JLabel("Liste des vueNoeuds ici");
        
        listeNoeuds = new ListNoeuds();

        btnAddNoeud = new JButton("Ajouter un noeud");
    }
    
    private void placeComponents(){
        layout = new SpringLayout();
        setLayout(layout);
        
        add(titre);
        add(listeNoeuds);
        add(btnAddNoeud);
        
        final int espace = 5;
        layout.putConstraint(SpringLayout.NORTH, titre, espace, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, titre, espace, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.NORTH, listeNoeuds, espace, SpringLayout.SOUTH, titre);
        layout.putConstraint(SpringLayout.WEST, listeNoeuds, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, listeNoeuds, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, listeNoeuds, -espace, SpringLayout.NORTH, btnAddNoeud);
        
        layout.putConstraint(SpringLayout.SOUTH, btnAddNoeud, -espace, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, btnAddNoeud, 0, SpringLayout.WEST, titre);


    }
    
    private void addListeners(){
    }
}
