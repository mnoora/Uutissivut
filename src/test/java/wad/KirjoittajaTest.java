/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import wad.domain.*;

/**
 *
 * @author mnoora
 */
public class KirjoittajaTest {
    
    Kirjoittaja kirjoittaja;
    
    public KirjoittajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("nimi");
        ArrayList lista = new ArrayList<>();
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("esimerkki");
        lista.add(uutinen);
        kirjoittaja.setUutiset(lista);
    }
    
    @Test
    public void setNimiAsettaaNimenOikein() {
        assertEquals("nimi",kirjoittaja.getNimi());
    }
    
    @Test
    public void setUutisetToimiiOikeinYhdelläUutisella(){
        assertEquals("esimerkki",kirjoittaja.getUutiset().get(0).getOtsikko());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
