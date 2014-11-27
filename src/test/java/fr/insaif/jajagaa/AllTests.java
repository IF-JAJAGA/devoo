package fr.insaif.jajagaa;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.insaif.jajagaa.model.PlageHoraireTest;
import fr.insaif.jajagaa.model.TSPTest;
import fr.insaif.jajagaa.model.TourneeTest;
import fr.insaif.jajagaa.model.ZoneGeographiqueTest;
import fr.insaif.jajagaa.view.VueNoeudTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Cette classe permet uniquement de lancer les tests dont les noms sont renseignés dans la liste @Suite.SuiteClasses
 * @author jeje
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({   AppTest.class,
                        //ParseurTest.class,  //TODO : Parseur
                        PlageHoraireTest.class, TSPTest.class, TourneeTest.class, ZoneGeographiqueTest.class,
                        VueNoeudTest.class})
public class AllTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
