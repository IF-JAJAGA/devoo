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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 * Contient les éléments à droite du plan.
 * @author jeje
 */
public class ConteneurDroite extends JPanel{
    private final String strBtnAddNoeud0 = "Ajouter une livraison";
    private final String strBtnAddNoeud1 = "Choisir le noeud avant";
    
    private JLabel titre;
    private ListNoeuds listeNoeuds;
    private JButton btnAddNoeud;
    private JTextField saisieTemps;
    private final JLabel lblTemps = new JLabel("secondes");
    private JButton btnCalculLivraison;
    
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

    public JTextField getSaisieTemps() {
        return saisieTemps;
    }

    public JButton getBtnCalculLivraison() {
        return btnCalculLivraison;
    }

    public void setEtatBtnAddNoeud(int e){
        if(e==0){
            btnAddNoeud.setEnabled(true);
            btnAddNoeud.setText(strBtnAddNoeud0);
        }
        else if(e==1){
            btnAddNoeud.setText(strBtnAddNoeud1);
        }
    }
    

    public ConteneurDroite() {
        initComponents();
        placeComponents();
        addListeners();
    }
    
    private void initComponents(){
        titre = new JLabel("Liste des Noeuds");
        
        listeNoeuds = new ListNoeuds();

        btnAddNoeud = new JButton(strBtnAddNoeud0);
        
        btnCalculLivraison = new JButton("Calculer la livraison");
        saisieTemps = new JTextField("10");
    }
    
    private void placeComponents(){
        layout = new SpringLayout();
        setLayout(layout);
        
        add(titre);
        add(listeNoeuds);
        JScrollPane scrollListe = new JScrollPane(listeNoeuds);
        add(scrollListe);  
        add(btnAddNoeud);
        
        add(saisieTemps);
        add(lblTemps);
        add(btnCalculLivraison);
        
        final int espace = 5;
        layout.putConstraint(SpringLayout.NORTH, titre, espace, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, titre, espace, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.NORTH, scrollListe, espace, SpringLayout.SOUTH, titre);
        layout.putConstraint(SpringLayout.WEST, scrollListe, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollListe, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollListe, -espace, SpringLayout.NORTH, saisieTemps);
        
        layout.putConstraint(SpringLayout.SOUTH, saisieTemps, -espace, SpringLayout.NORTH, btnCalculLivraison);
        layout.putConstraint(SpringLayout.WEST, saisieTemps, 0, SpringLayout.WEST, titre);
        layout.putConstraint(SpringLayout.EAST, saisieTemps, -espace, SpringLayout.WEST, lblTemps);
        
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblTemps, 0, SpringLayout.VERTICAL_CENTER, saisieTemps);
        layout.putConstraint(SpringLayout.EAST, lblTemps, 0, SpringLayout.EAST, btnCalculLivraison);
        
        layout.putConstraint(SpringLayout.SOUTH, btnCalculLivraison, -espace, SpringLayout.NORTH, btnAddNoeud);
        layout.putConstraint(SpringLayout.WEST, btnCalculLivraison, espace, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.SOUTH, btnAddNoeud, -espace, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAddNoeud, 0, SpringLayout.HORIZONTAL_CENTER, this);


    }
    
    private void addListeners(){
    }
}
