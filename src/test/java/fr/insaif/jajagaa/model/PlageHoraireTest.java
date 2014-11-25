/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.model;

import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aurelien
 */
public class PlageHoraireTest {
    
    public PlageHoraireTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compareTo method, of class PlageHoraire.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        PlageHoraire t = new PlageHoraire("8:0:0", "9:0:0");
        PlageHoraire instance = new PlageHoraire("9:0:0", "10:0:0");
        int result = instance.compareTo(t);
        assertEquals(1, result);
        result = t.compareTo(instance);
        assertEquals(-1, result);
        instance = new PlageHoraire("10:0:0", "11:0:0");
        result = instance.compareTo(t);
        assertEquals(1, result);
        result = t.compareTo(instance);
        assertEquals(-1, result);
    }
    
}
