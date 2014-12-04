/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.view.VueNoeud;
import java.awt.Color;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;


/**
 * Contient les éléments à droite du plan.
 * @author jeje
 */
public class ConteneurDroite extends JPanel{
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    
    private final String strBtnAddNoeud0 = "Ajouter un noeud livraison";
    private final String strBtnAddNoeud1 = "Choisir la livraison précédente";
    private final String strBtnSupNoeud  = "Supprimer une livraison";
    
    private JLabel titre;
    private ListNoeuds listeNoeuds;
    private JTextArea textFiDescriptionLivraison;
    private JTextArea legendeCouleurs;
    private JButton btnAddNoeud;
    private JButton btnSupNoeud;
    private JButton btnCalculLivraison;
    
    private SpringLayout layout;
    
    public void majListe(List<VueNoeud> vueNoeuds) {
        listeNoeuds.maj(vueNoeuds);
    }

    public JButton getBtnAddNoeud() {
        return btnAddNoeud;
    }
    
     public JButton getBtnSupNoeud() {
        return btnSupNoeud;
    }    

    public ListNoeuds getListeNoeuds() {
        return listeNoeuds;
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
    
    public void setEtatBtnSupNoeud(int e){
        //TODO !!
    }
    
    public void setTextFieldText(Livraison l){
        String s = "";
        s += "Id Commande : ";
        s += l.getId();
        s += "\nId Client : ";
        s += l.getIdClient();
        s += "\nHoraire prévu d'arrivée : ";
        s += sdf.format(l.getHeureLivraison()).toString();
        s += "\nHoraire prévu de fin : ";
        s += sdf.format(l.getHeureFin()).toString();
        
        textFiDescriptionLivraison.setText(s);
    }
    
    public void resetTextFieldText(){
        textFiDescriptionLivraison.setText("");
    }

    public ConteneurDroite() {
        initComponents();
        placeComponents();
        addListeners();
    }
    
    private void initComponents(){
        titre = new JLabel("Liste des Noeuds");
        
        listeNoeuds = new ListNoeuds();

        textFiDescriptionLivraison = new JTextArea();
        textFiDescriptionLivraison.setEditable(false);
        
        legendeCouleurs = new JTextArea();
        legendeCouleurs.setEditable(false);
        
        btnAddNoeud = new JButton(strBtnAddNoeud0);
        btnSupNoeud = new JButton(strBtnSupNoeud);
        btnCalculLivraison = new JButton("Calculer la tournée");
        
        btnAddNoeud.setEnabled(false);
        btnSupNoeud.setEnabled(false);
        btnCalculLivraison.setEnabled(false);
    }
    
    private void placeComponents(){
        layout = new SpringLayout();
        setLayout(layout);
        
        add(titre);
        add(listeNoeuds);
        JScrollPane scrollListe = new JScrollPane(listeNoeuds);
        add(scrollListe);  
        add(legendeCouleurs);
        add(textFiDescriptionLivraison);
        add(btnAddNoeud);
        add(btnSupNoeud);
        
        add(btnCalculLivraison);
        
        final int espace = 5;
        final int hauteurTextes = 70;
        layout.putConstraint(SpringLayout.NORTH, titre, espace, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, titre, espace, SpringLayout.WEST, this);
        
        layout.putConstraint(SpringLayout.NORTH, scrollListe, espace, SpringLayout.SOUTH, titre);
        layout.putConstraint(SpringLayout.WEST, scrollListe, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollListe, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollListe, -espace, SpringLayout.NORTH, legendeCouleurs);
        
        layout.putConstraint(SpringLayout.NORTH, legendeCouleurs, -hauteurTextes, SpringLayout.SOUTH, legendeCouleurs);
        layout.putConstraint(SpringLayout.WEST, legendeCouleurs, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, legendeCouleurs, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, legendeCouleurs, -espace, SpringLayout.NORTH, textFiDescriptionLivraison);
        
        layout.putConstraint(SpringLayout.NORTH, textFiDescriptionLivraison, -hauteurTextes, SpringLayout.SOUTH, textFiDescriptionLivraison);
        layout.putConstraint(SpringLayout.WEST, textFiDescriptionLivraison, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, textFiDescriptionLivraison, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, textFiDescriptionLivraison, -espace, SpringLayout.NORTH, btnCalculLivraison);
        
        
        layout.putConstraint(SpringLayout.SOUTH, btnCalculLivraison, -espace, SpringLayout.NORTH, btnSupNoeud);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnCalculLivraison, 0, SpringLayout.HORIZONTAL_CENTER, this);
        
        layout.putConstraint(SpringLayout.SOUTH, btnSupNoeud, -espace, SpringLayout.NORTH, btnAddNoeud);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSupNoeud, 0, SpringLayout.HORIZONTAL_CENTER, this);
        
        layout.putConstraint(SpringLayout.SOUTH, btnAddNoeud, -espace, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAddNoeud, 0, SpringLayout.HORIZONTAL_CENTER, this);


    }
    
    private void addListeners(){
    }

    public void majLegendeCouleurs(Map<PlageHoraire, Color> colorsPL) {
        legendeCouleurs.setText("Pas de plages horaires chargées");
        
        String text = "";
        for (Map.Entry<PlageHoraire,Color> entry : colorsPL.entrySet()){
            PlageHoraire pl = entry.getKey();
            Color couleur = entry.getValue();
            if(couleur == Color.CYAN){
                text += "Cyan : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
            else if(couleur == Color.YELLOW){
                text += "Jaune : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
            else if(couleur == Color.PINK){
                text += "Rose : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
            else if(couleur == Color.WHITE){
                text += "Blanc : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
            else if(couleur == Color.BLACK){
                text += "Noir : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
            else if(couleur == Color.LIGHT_GRAY){
                text += "Gris clair : " + sdf.format(pl.getHeureDebut()) + " -> " + sdf.format(pl.getHeureFin()) + "\n";
            }
        }
        
        if(!text.equals(""))     legendeCouleurs.setText(text);
        
    }
}
