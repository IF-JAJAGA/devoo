/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import fr.insaif.jajagaa.model.EtatNoeud;
import fr.insaif.jajagaa.view.VueNoeud;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author H4201
 */
public class ListNoeuds extends JList {
    private DefaultListModel dlm = new DefaultListModel<VueNoeud>();
    
    private Vector<ListSelectionListener> listeners = new Vector<ListSelectionListener>();

    public ListNoeuds() {
        setModel(dlm);
        setCellRenderer(new ListNoeudsRenderer());
        setSelectionBackground(Color.RED);
    }
    
    /**
     * <b>Précaution : </b> Désactivation des listeners pour travailler.
     * @param vNASelectionner noeud à sélectionner.
     */
    public void SelectionnerNoeud(VueNoeud vNASelectionner){
        disableListeners();
        if(vNASelectionner==null){
            clearSelection();
        }
        else {
            setSelectedValue(vNASelectionner, true);
        }
        enableListeners();
    }

    /**
     * Met à jour la liste des noeuds selon l'état des éléments dans le plan
     * On enlève au préalable les noeuds déjà présents dans la liste.
     * <b>Précaution : </b> Désactivation des listeners pour travailler.
     * @param vueNoeuds la liste des noeuds qui seront dans la liste.
     */
    void maj(List<VueNoeud> vueNoeuds) {
        disableListeners();
        removeElements();
        addElements(vueNoeuds);
        enableListeners();
    }

    /**
     * 
     * @param vueNoeuds liste des noeuds à ajouter à la liste
     */
    private void addElements(List<VueNoeud> vueNoeuds) {
        for(VueNoeud vN : vueNoeuds){
            dlm.addElement(vN);
        }
    }
    
    /**
     * Vide la JList
     */
    private void removeElements(){
        dlm.removeAllElements();
    }

    @Override
    public void addListSelectionListener(ListSelectionListener listener) {
        listeners.add(listener);
        super.addListSelectionListener(listener);
    }
    
    /**
     * Désactive les listeners sur la liste pour la manipuler tranquilement.
     */
    private void disableListeners(){
        for(ListSelectionListener lst : listeners){
            removeListSelectionListener(lst);
        }
    }
    
    /**
     * Réactive les listeners que l'on avait désactivés avec disableListeners.
     */
    private void enableListeners(){
        for(ListSelectionListener lst : listeners){
            super.addListSelectionListener(lst);
        }
    }
    
    
}

class ListNoeudsRenderer extends JLabel implements ListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList jlist, Object e, int index, boolean isSelected, boolean cellHasFocus) {
        if(e instanceof VueNoeud){
            VueNoeud vN = (VueNoeud)e;
            if(vN.getEtatLivraison() == EtatNoeud.LIVRAISON){
                setText("Livraison " + vN.getNoeudModele().getId() + " (" + vN.getNoeudModele().getXMetre()+ " ; " + vN.getNoeudModele().getYMetre()+ ")");
            }
            else if(vN.getEtatLivraison() == EtatNoeud.RETARD){
                setText("Livraison retard" + vN.getNoeudModele().getId() + " (" + vN.getNoeudModele().getXMetre()+ " ; " + vN.getNoeudModele().getYMetre()+ ")");
            }
            else if(vN.getCouleur() == Color.ORANGE){
                setText("Entrepôt " + vN.getNoeudModele().getId() + " (" + vN.getNoeudModele().getXMetre()+ " ; " + vN.getNoeudModele().getYMetre()+ ")");
            }
            else{
                setText("Noeud " + vN.getNoeudModele().getId() + " (" + vN.getNoeudModele().getXMetre()+ " ; " + vN.getNoeudModele().getYMetre()+ ")");
            }
            
            if (isSelected) {
                setBackground(Color.CYAN);
            } else {
                setBackground(Color.WHITE);
            }
        }
        else if(e instanceof String){
            setText((String)e);
        }
        else{
            setText("Type non trouvé");
        }
        
        setOpaque(true);
        return this;
    }
}
