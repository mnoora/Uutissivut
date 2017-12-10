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
public class UutinenTest {
    
    Uutinen uutinen;
    
    public UutinenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        uutinen = new Uutinen();
        uutinen.setOtsikko("esimerkkiOtsikko");
        uutinen.setIngressi("esimerkkiIngressi");
        uutinen.setTeksti("esimerkkiTeksti");
        ArrayList<Kategoria> kategoriat = new ArrayList<>();
        Kategoria kategoria = new Kategoria();
        kategoria.setNimi("kategoria");
        kategoriat.add(kategoria);
        uutinen.setKategoriat(kategoriat);
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList();
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("kirjoittaja");
        kirjoittajat.add(kirjoittaja);
        uutinen.setKirjoittajat(kirjoittajat); 
    }
    
    @Test
    public void konstruktoriAsettaaOtsikonOikein(){
        assertEquals("esimerkkiOtsikko",uutinen.getOtsikko());
    }
    @Test
    public void konstruktoriAsettaaIngressinOikein() {
        assertEquals("esimerkkiIngressi",uutinen.getIngressi());
    }
    
    @Test
    public void konstruktoriAsettaaTekstinOikein() {
        assertEquals("esimerkkiTeksti", uutinen.getTeksti());
    }
    
    @Test
    public void konstruktoriAsettaaKategoriatOikeinYhdelläKategorialla() {
        assertEquals("kategoria",uutinen.getKategoriat().get(0).getNimi());
    }
    
    @Test
    public void konstruktoriAsettaaKirjoittajatOikeinYhdelläKirjoittajalla() {
        assertEquals("kirjoittaja",uutinen.getKirjoittajat().get(0).getNimi());
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
