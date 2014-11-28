/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import fr.insaif.jajagaa.view.VueNoeud;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;

/**
 *
 * @author jeje
 */
public class ListNoeuds extends JList {
    private DefaultListModel dlm = new DefaultListModel<VueNoeud>();

    public ListNoeuds() {
        setModel(dlm);
        setCellRenderer(new ListNoeudsRenderer());
        setSelectionBackground(Color.RED);
    }

    /**
     * Met à jour la liste selon l'état des éléments dans le plan
     * @param vueNoeuds 
     */
    void maj(List<VueNoeud> vueNoeuds) {
        addElements(vueNoeuds);
    }

    private void addElements(List<VueNoeud> vueNoeuds) {
        dlm.addElement("8h");
        for(VueNoeud vN : vueNoeuds){
            dlm.addElement(vN);
        }
        dlm.addElement("9h");
    }
}

class ListNoeudsRenderer extends JLabel implements ListCellRenderer{
    

    @Override
    public Component getListCellRendererComponent(JList jlist, Object e, int index, boolean isSelected, boolean cellHasFocus) {
        if(e instanceof VueNoeud){
            VueNoeud vN = (VueNoeud)e;
            setText("Noeud " + vN.getNoeudModele().getX() + " ; " + vN.getNoeudModele().getY());
            if (isSelected) {
                vN.setEstSelectionne(true);
                setBackground(Color.CYAN);
            } else {
                if(vN.isEstSelectionne()){
                    vN.setEstSelectionne(false);
                }
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
