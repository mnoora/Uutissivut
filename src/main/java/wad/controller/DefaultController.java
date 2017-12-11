/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import wad.domain.Account;
import wad.domain.Kategoria;
import wad.domain.Kirjoittaja;
import wad.domain.Uutinen;
import wad.repository.AccountRepository;
import wad.repository.KategoriaRepository;
import wad.repository.KirjoittajaRepository;
import javax.transaction.Transactional;
import wad.repository.UutisRepository;
import wad.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *
 * @author mnoora
 */
@Controller
public class DefaultController {
   
    @Autowired
    private UutisRepository uutisetRepository;
    
    @Autowired 
    private KategoriaRepository kategoriaRepository;
   
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @PostConstruct
    @Transactional
    public void init() {
        
        //luodaan oletuskäyttäjä
        Account user = new Account();
        user.setUsername("esimerkki");
        user.setPassword(this.passwordEncoder().encode("salasana"));
        user = this.accountRepository.save(user);
        
        //luodaan esimerkkiuutisia uutissivustoa varten
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Noora");
        
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList<>();
        kirjoittajat.add(kirjoittaja);
        
        
        
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("Uusi uutissivu");
        uutinen.setIngressi("Tervetuloa uudelle uutissivulle!");
        uutinen.setTeksti("Uuden uutissivuston ansiosta tulet pysymään ajan tasalla maailman tapahtumista.");
        ArrayList<Uutinen> lista = new ArrayList<>();
        ArrayList<Uutinen> lista2 = new ArrayList<>();
        
        
        uutinen.setKirjoittajat(kirjoittajat);
        
        Uutinen uutinen2 = new Uutinen();
        uutinen2.setOtsikko("Presidenttiehdokkaat paneelikeskustelussa");
        uutinen2.setIngressi("Presidenttiehdokkaat kokoontuivat keskustelemaan ajankohtaisista aiheista");
        uutinen2.setTeksti("Tänään presidenttiehdokkaat kokoontuivat samaan paikkaan keskustellakseen polttavista aiheista. Voit myös seurata keskustelua suorana.");
        
        Uutinen uutinen3 = new Uutinen();
        uutinen3.setOtsikko("Rankkaa lumisadetta luvassa");
        uutinen3.setIngressi("Lunta jopa 10 cm.");
        uutinen3.setTeksti("Ensi viikolla rankat lumisateet saavuttavat Suomen ja pääkaupunkiseuta saa jopa hyvinkin paksun lumipeitteen. Lumisateen on ennustettu leviävän koko Suomeen.");
        
        uutinen2.setKirjoittajat(kirjoittajat);
        uutinen3.setKirjoittajat(kirjoittajat);
        
        Uutinen uutinen4 = new Uutinen();
        uutinen4.setOtsikko("Lemmikin omistamisen on todettu olevan terveellistä");
        uutinen4.setIngressi("Lemmikin omistajat elävät muita pidempään");
        uutinen4.setTeksti("Lemmikin omistajien on todettu elävän pidempään kuin niiden ihmisten, jotka eivät omista lemmikkejä."
                + " Lemmikkieläinten terveysvaikutukset ovat nousseet tietoisuuteen yhä enemmän. Vaikutukset ovat suuria etenkin koiranomistajien keskuudessa.");
        uutinen4.setKirjoittajat(kirjoittajat);
        
        Uutinen uutinen5 = new Uutinen();
        uutinen5.setOtsikko("Lähestyvän joulun seuraukset");
        uutinen5.setIngressi("Ruuhkan mahdollisuus kasvaa huomattavasti");
        uutinen5.setTeksti("Lähestyvä joulu aiheuttaa laajasti ruuhkaa. Ruuhkan määrän on arvioitu kasvavan mitä lähemmäs joulua ollaan. "
                + "Suurin syy jouluruuhkaan ovat jouluostokset, joita ostetaan sitä enemmän mitä lähempänä joulu on.");
        uutinen5.setKirjoittajat(kirjoittajat);
        
        Uutinen uutinen6 = new Uutinen();
        uutinen6.setOtsikko("Laaja flunssaepidemia");
        uutinen6.setIngressi("Pääkaupunkiseudulla laaja flunssaepidemia");
        uutinen6.setTeksti("Pääkaupunkiseudulla on havaittu laaja flunssaepidemia. Ihmisiä kehotetaan kiinnittämään enemmän huomiota käsien pesuun."
                + " Mistään hyvin vakavasta ei kuitenkaan ole kyse, samankaltaiset epidemiat ovat hyvin yleisiä talviaikaan.");
        uutinen5.setKirjoittajat(kirjoittajat);
        
        Kategoria kategoria1 = new Kategoria("Politiikka",lista);
        Kategoria kategoria2 = new Kategoria("Kotimaa",lista);
        Kategoria kategoria3 = new Kategoria("Terveys",lista);
        
        lista2.add(uutinen);
        lista2.add(uutinen2);
        lista2.add(uutinen3);
        lista2.add(uutinen4);
        lista2.add(uutinen5);
        lista2.add(uutinen6);
        
        uutinen.getKategoriat().add(kategoria2);
        uutinen2.getKategoriat().add(kategoria1);
        uutinen3.getKategoriat().add(kategoria2);
        uutinen4.getKategoriat().add(kategoria3);
        uutinen5.getKategoriat().add(kategoria2);
        uutinen6.getKategoriat().add(kategoria2);
        
        this.uutisetRepository.saveAll(lista2);
        this.kirjoittajaRepository.save(kirjoittaja);
        
        kategoria1.lisaaUutinen(uutinen2);
        kategoria2.lisaaUutinen(uutinen);
        kategoria2.lisaaUutinen(uutinen3);
        kategoria3.lisaaUutinen(uutinen4);
        kategoria2.lisaaUutinen(uutinen5);
        kategoria2.lisaaUutinen(uutinen6);
        
        this.kategoriaRepository.save(kategoria1);
        this.kategoriaRepository.save(kategoria2);
        this.kategoriaRepository.save(kategoria3);
        
        
        //tallennetaan uutiset ja kategoriat repository:hin
          
    }
    
}
