/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import static java.lang.Math.abs;

/**
 *
 * @author jeje
 */
public class Fleche {
    private VueTroncon vT;
    public Fleche(VueTroncon vT){
        this.vT = vT;
    }
    private Fleche(){}
    public void draw ( Graphics2D g )
    {
        float xArrivee = vT.getDestViewX();
        float xDepart =  vT.getOrigViewX();
        float yArrivee = vT.getDestViewY();
        float yDepart = vT.getOrigViewY();
        
        
        float yg = abs(yDepart - yArrivee);
        float xg = abs(xDepart - xArrivee);
        
        float dg = (float) Math.sqrt(Math.pow(xg, 2) + Math.pow(yg, 2));
        
        float dp = 25.0f;   //Longueur de la fleche projetée.
        
        float y = (yg * dp) / dg;
        
        double alpha = Math.atan(xg / yg);
        float x = (float) (y * Math.tan((Math.PI/4) - alpha));
        
        
        Path2D.Float path = new Path2D.Float();
        
        if(xArrivee >= xDepart){
            if(yArrivee >= yDepart){
                
                //Flèche vers le bas à droite
                path.moveTo ( xArrivee+x, yArrivee-y );
                path.lineTo ( xArrivee, yArrivee );
                path.lineTo ( xArrivee - y, yArrivee-x );
                
            }
            else{
                //Fleche vers le haut à droite
                path.moveTo ( xArrivee+x, yArrivee+y );
                path.lineTo ( xArrivee, yArrivee );
                path.lineTo ( xArrivee - y, yArrivee+x );
            }
        }
        else {
            if(yArrivee >= yDepart){
                //Flèche vers le bas à gauche
                path.moveTo ( xArrivee-x, yArrivee-y );
                path.lineTo ( xArrivee, yArrivee );
                path.lineTo ( xArrivee + y, yArrivee-x );
            }
            else{
                //Fleche vers le haut à gauche
                path.moveTo ( xArrivee+x, yArrivee+y );
                path.lineTo ( xArrivee, yArrivee );
                path.lineTo ( xArrivee - y, yArrivee+x );
            }
        }
        
        g.setColor ( Color.BLUE );
        g.draw ( path );
        
        
        
    }
}
