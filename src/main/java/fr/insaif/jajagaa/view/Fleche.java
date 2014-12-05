/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Path2D;
import static java.lang.Math.abs;

/**
 *
 * @author H4201
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
        
        float dp = 20.0f;   //Longueur de la fleche projetée.
        
        float y = (yg * dp) / dg;
        
        double alpha = Math.atan(xg / yg);
        float x = (float) (y * Math.tan((Math.PI/6) - alpha));
        
        
        Graphics2D g2 = (Graphics2D) g;
        
        Polygon polygon = new Polygon();
        if(xArrivee >= xDepart){
            if(yArrivee >= yDepart){
                
                //Flèche vers le bas à droite
                polygon.addPoint((int) (xArrivee+x), (int) (yArrivee-y));
                polygon.addPoint((int) (xArrivee), (int) (yArrivee));
                polygon.addPoint((int) (xArrivee-y), (int) (yArrivee-x));
                
            }
            else{
                //Fleche vers le haut à droite
                polygon.addPoint((int) (xArrivee+x), (int) (yArrivee+y));
                polygon.addPoint((int) (xArrivee), (int) (yArrivee));
                polygon.addPoint((int) (xArrivee-y), (int) (yArrivee+x));
            }
        }
        else {
            if(yArrivee >= yDepart){
                //Flèche vers le bas à gauche
                polygon.addPoint((int) (xArrivee-x), (int) (yArrivee-y));
                polygon.addPoint((int) (xArrivee), (int) (yArrivee));
                polygon.addPoint((int) (xArrivee+y), (int) (yArrivee-x));
            }
            else{
                //Fleche vers le haut à gauche
                polygon.addPoint((int) (xArrivee-x), (int) (yArrivee+y));
                polygon.addPoint((int) (xArrivee), (int) (yArrivee));
                polygon.addPoint((int) (xArrivee+y), (int) (yArrivee+x));
            }
        }
        
         g2.setColor ( Color.MAGENTA );
         g2.fillPolygon(polygon);
        
        
        
    }
}
