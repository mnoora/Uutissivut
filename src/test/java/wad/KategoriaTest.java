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
import wad.domain.*;
import java.util.*;

/**
 *
 * @author mnoora
 */
public class KategoriaTest {
    
    Kategoria kategoria;
    
    public KategoriaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kategoria = new Kategoria();
        kategoria.setNimi("nimi");
        List<Uutinen> lista = new ArrayList<>();
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("esimerkki");
        lista.add(uutinen);
        kategoria.setUutiset(lista);
        kategoria.setUutistenMaara(1);
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void setNimiAsettaaNimenOikein() {
        assertEquals("nimi",kategoria.getNimi());
    }
    
    @Test
    public void setUutisetToimiiOikeinYhdelläUutisella() {
        
        assertEquals("esimerkki",kategoria.getUutiset().get(0).getOtsikko());
    }
    
    @Test
    public void getUutistenMaaraPalauttaaOikeanMaaran(){
        assertEquals(1,kategoria.getUutistenMaara());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
