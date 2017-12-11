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

/**
 *
 * @author mnoora
 */
@Controller
public class DefaultController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
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
    
    @PostConstruct
    @Transactional
    public void init() {
        
        //luodaan oletuskäyttäjä
        Account user = new Account();
        user.setUsername("esimerkki");
        user.setPassword(passwordEncoder.encode("salasana"));
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
        uutinen2.setTeksti("Tänään presidentti ehdokkaat kokoontuivat samaan paikkaan keskustellakseen polttavista aiheista. Voit myös seurata keskustelua suorana.");
        
        Uutinen uutinen3 = new Uutinen();
        uutinen3.setOtsikko("Rankkaa lumisadetta luvassa");
        uutinen3.setIngressi("Lunta jopa 10 cm.");
        uutinen3.setTeksti("Ensi viikolla rankat lumisateet saavuttavat Suomen ja pääkaupunkiseuta saa jopa hyvinkin paksun lumipeitteen. Lumisateen on ennustettu leviävän koko Suomeen.");
        
        uutinen2.setKirjoittajat(kirjoittajat);
        uutinen3.setKirjoittajat(kirjoittajat);
        
        
        
        Kategoria kategoria1 = new Kategoria("Politiikka",lista);
        Kategoria kategoria2 = new Kategoria("Kotimaa",lista);
        lista2.add(uutinen);
        lista2.add(uutinen2);
        lista2.add(uutinen3);
        
        uutinen.getKategoriat().add(kategoria2);
        uutinen2.getKategoriat().add(kategoria1);
        uutinen3.getKategoriat().add(kategoria2);
        
        this.uutisetRepository.saveAll(lista2);
        this.kirjoittajaRepository.save(kirjoittaja);
        
        kategoria1.lisaaUutinen(uutinen2);
        kategoria2.lisaaUutinen(uutinen);
        kategoria2.lisaaUutinen(uutinen3);
        
        this.kategoriaRepository.save(kategoria1);
        this.kategoriaRepository.save(kategoria2);
        
        //tallennetaan uutiset ja kategoriat repository:hin
          
    }
}
